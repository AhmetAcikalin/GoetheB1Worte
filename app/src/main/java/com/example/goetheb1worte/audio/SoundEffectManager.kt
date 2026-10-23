package com.example.goetheb1worte.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.goetheb1worte.R

interface SoundEffectManager {
    fun playCorrect()
    fun playWrong()
    fun release()
}

class NoOpSoundEffectManager : SoundEffectManager {
    override fun playCorrect() {}
    override fun playWrong() {}
    override fun release() {}
}

class AndroidSoundEffectManager(
    context: Context
) : SoundEffectManager, DefaultLifecycleObserver {

    private val tag = "SoundEffectManager"
    private val soundPool: SoundPool
    private var correctSoundId: Int = 0
    private var wrongSoundId: Int = 0
    private var isReleased = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        try {
            correctSoundId = soundPool.load(context.applicationContext, R.raw.duolingo_correct, 1)
            wrongSoundId = soundPool.load(context.applicationContext, R.raw.duolingo_wrong, 1)
        } catch (e: Exception) {
            Log.e(tag, "Error loading sound effects", e)
        }
    }

    override fun playCorrect() {
        if (isReleased) return
        try {
            if (correctSoundId != 0) {
                soundPool.play(correctSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
            }
        } catch (e: Exception) {
            Log.w(tag, "Failed to play correct sound", e)
        }
    }

    override fun playWrong() {
        if (isReleased) return
        try {
            if (wrongSoundId != 0) {
                soundPool.play(wrongSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
            }
        } catch (e: Exception) {
            Log.w(tag, "Failed to play wrong sound", e)
        }
    }

    override fun release() {
        if (!isReleased) {
            isReleased = true
            try {
                soundPool.release()
            } catch (e: Exception) {
                Log.w(tag, "Error releasing SoundPool", e)
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        release()
    }
}
