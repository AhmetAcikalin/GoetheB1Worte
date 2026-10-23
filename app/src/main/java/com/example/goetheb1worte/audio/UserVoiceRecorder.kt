package com.example.goetheb1worte.audio

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.util.concurrent.atomic.AtomicBoolean

interface UserVoiceRecorder {
    fun startRecording(): Boolean
    fun stopRecording(): Boolean
    fun playRecording(onComplete: () -> Unit = {}): Boolean
    fun stopPlayback()
    fun deleteRecording()
    fun isPlaying(): Boolean
    fun hasRecording(): Boolean
}

class NoOpUserVoiceRecorder : UserVoiceRecorder {
    override fun startRecording(): Boolean = false
    override fun stopRecording(): Boolean = false
    override fun playRecording(onComplete: () -> Unit): Boolean = false
    override fun stopPlayback() {}
    override fun deleteRecording() {}
    override fun isPlaying(): Boolean = false
    override fun hasRecording(): Boolean = false
}

class AndroidUserVoiceRecorder(
    private val context: Context
) : UserVoiceRecorder, DefaultLifecycleObserver {

    private val tag = "UserVoiceRecorder"
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private val isRecording = AtomicBoolean(false)
    private var mediaPlayer: MediaPlayer? = null
    private val tempFile: File = File(context.cacheDir, "temp_user_pronunciation.wav")

    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioEncoding = AudioFormat.ENCODING_PCM_16BIT

    override fun startRecording(): Boolean {
        deleteRecording()
        return try {
            val minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioEncoding)
            if (minBufSize <= 0) {
                Log.e(tag, "Invalid minBufferSize: $minBufSize")
                return false
            }

            val bufferSize = (minBufSize * 2).coerceAtLeast(4096)
            val record = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioEncoding,
                bufferSize
            )

            if (record.state != AudioRecord.STATE_INITIALIZED) {
                Log.e(tag, "AudioRecord failed to initialize")
                record.release()
                return false
            }

            audioRecord = record
            isRecording.set(true)
            record.startRecording()

            recordingThread = Thread({
                writeAudioDataToWav(record, bufferSize)
            }, "UserVoiceRecorderThread").apply { start() }

            Log.d(tag, "AudioRecord started recording to ${tempFile.absolutePath}")
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to start AudioRecord: ${e.message}", e)
            safelyReleaseRecorder()
            false
        }
    }

    private fun writeAudioDataToWav(record: AudioRecord, bufferSize: Int) {
        val data = ByteArray(bufferSize)
        try {
            FileOutputStream(tempFile).use { out ->
                // Write placeholder 44-byte WAV header
                out.write(ByteArray(44))

                var totalBytesRead = 0L
                while (isRecording.get()) {
                    val read = record.read(data, 0, data.size)
                    if (read > 0) {
                        out.write(data, 0, read)
                        totalBytesRead += read
                    } else if (read < 0) {
                        Log.w(tag, "AudioRecord read error: $read")
                        break
                    }
                }
                out.flush()

                // Update WAV header with final sizes
                updateWavHeader(tempFile, totalBytesRead, sampleRate, 1, 16)
            }
        } catch (e: Exception) {
            Log.e(tag, "Error writing WAV data: ${e.message}", e)
        }
    }

    private fun updateWavHeader(file: File, totalAudioLen: Long, sampleRate: Int, channels: Int, bitsPerSample: Int) {
        val totalDataLen = totalAudioLen + 36
        val byteRate = (sampleRate * channels * bitsPerSample / 8).toLong()
        val header = ByteArray(44)

        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()
        header[4] = (totalDataLen and 0xff).toByte()
        header[5] = ((totalDataLen shr 8) and 0xff).toByte()
        header[6] = ((totalDataLen shr 16) and 0xff).toByte()
        header[7] = ((totalDataLen shr 24) and 0xff).toByte()
        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()
        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()
        header[16] = 16 // Subchunk1Size for PCM
        header[17] = 0
        header[18] = 0
        header[19] = 0
        header[20] = 1 // AudioFormat: 1 for PCM
        header[21] = 0
        header[22] = channels.toByte()
        header[23] = 0
        header[24] = (sampleRate and 0xff).toByte()
        header[25] = ((sampleRate shr 8) and 0xff).toByte()
        header[26] = ((sampleRate shr 16) and 0xff).toByte()
        header[27] = ((sampleRate shr 24) and 0xff).toByte()
        header[28] = (byteRate and 0xff).toByte()
        header[29] = ((byteRate shr 8) and 0xff).toByte()
        header[30] = ((byteRate shr 16) and 0xff).toByte()
        header[31] = ((byteRate shr 24) and 0xff).toByte()
        header[32] = (channels * bitsPerSample / 8).toByte() // block align
        header[33] = 0
        header[34] = bitsPerSample.toByte() // bits per sample
        header[35] = 0
        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()
        header[40] = (totalAudioLen and 0xff).toByte()
        header[41] = ((totalAudioLen shr 8) and 0xff).toByte()
        header[42] = ((totalAudioLen shr 16) and 0xff).toByte()
        header[43] = ((totalAudioLen shr 24) and 0xff).toByte()

        RandomAccessFile(file, "rw").use { raf ->
            raf.seek(0)
            raf.write(header)
        }
    }

    override fun stopRecording(): Boolean {
        isRecording.set(false)
        try {
            audioRecord?.stop()
        } catch (e: Exception) {
            Log.w(tag, "Error stopping AudioRecord: ${e.message}")
        }
        try {
            recordingThread?.join(500)
        } catch (e: Exception) {
            Log.w(tag, "Error waiting for recording thread: ${e.message}")
        }
        safelyReleaseRecorder()
        val exists = hasRecording()
        Log.d(tag, "AudioRecord stopped. File exists: $exists, size: ${tempFile.length()} bytes")
        return exists
    }

    override fun playRecording(onComplete: () -> Unit): Boolean {
        stopPlayback()
        if (!hasRecording()) {
            Log.w(tag, "Cannot play recording: file does not exist or is empty")
            return false
        }

        return try {
            val player = MediaPlayer().apply {
                setDataSource(tempFile.absolutePath)
                setOnCompletionListener {
                    safelyReleasePlayer()
                    onComplete()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e(tag, "MediaPlayer error: what=$what, extra=$extra")
                    safelyReleasePlayer()
                    onComplete()
                    true
                }
                prepare()
                start()
            }
            mediaPlayer = player
            Log.d(tag, "Playing recording from ${tempFile.absolutePath}")
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to play user recording: ${e.message}", e)
            safelyReleasePlayer()
            false
        }
    }

    override fun stopPlayback() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
        } catch (e: Exception) {
            Log.w(tag, "Error stopping playback: ${e.message}")
        } finally {
            safelyReleasePlayer()
        }
    }

    override fun deleteRecording() {
        stopPlayback()
        safelyReleaseRecorder()
        try {
            if (tempFile.exists()) {
                val deleted = tempFile.delete()
                Log.d(tag, "Temp recording deleted: $deleted")
            }
        } catch (e: Exception) {
            Log.w(tag, "Failed to delete temp recording: ${e.message}")
        }
    }

    override fun isPlaying(): Boolean = mediaPlayer?.isPlaying == true

    override fun hasRecording(): Boolean = tempFile.exists() && tempFile.length() > 44

    private fun safelyReleaseRecorder() {
        try {
            audioRecord?.release()
        } catch (e: Exception) {
            Log.w(tag, "Error releasing AudioRecord: ${e.message}")
        } finally {
            audioRecord = null
            recordingThread = null
        }
    }

    private fun safelyReleasePlayer() {
        try {
            mediaPlayer?.reset()
            mediaPlayer?.release()
        } catch (e: Exception) {
            Log.w(tag, "Error releasing MediaPlayer: ${e.message}")
        } finally {
            mediaPlayer = null
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        stopPlayback()
        if (isRecording.get()) {
            stopRecording()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        deleteRecording()
    }
}
