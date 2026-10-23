package com.example.goetheb1worte.audio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

class SpeechRecognizerHelper(
    private val context: Context,
    private val onResultsReceived: (String) -> Unit,
    private val onErrorReceived: (Int) -> Unit,
    private val onListeningStateChanged: (Boolean) -> Unit
) {

    private val tag = "SpeechHelper"
    private var speechRecognizer: SpeechRecognizer? = null

    init {
        initializeRecognizer()
    }

    private fun initializeRecognizer() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Log.w(tag, "Speech recognition is not available on this device.")
            return
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    Log.d(tag, "onReadyForSpeech params=$params")
                    onListeningStateChanged(true)
                }

                override fun onBeginningOfSpeech() {
                    Log.d(tag, "onBeginningOfSpeech")
                }

                override fun onRmsChanged(rmsdB: Float) {
                    // Log.d(tag, "onRmsChanged: $rmsdB")
                }

                override fun onBufferReceived(buffer: ByteArray?) {
                    Log.d(tag, "onBufferReceived: size=${buffer?.size}")
                }

                override fun onEndOfSpeech() {
                    Log.d(tag, "onEndOfSpeech")
                    onListeningStateChanged(false)
                }

                override fun onError(error: Int) {
                    onListeningStateChanged(false)
                    Log.w(tag, "SpeechRecognizer error: $error")
                    onErrorReceived(error)
                }

                override fun onResults(results: Bundle?) {
                    onListeningStateChanged(false)
                    val keys = results?.keySet()?.joinToString(", ")
                    Log.d(tag, "onResults: keys=[$keys]")
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val bestMatch = matches?.firstOrNull() ?: ""
                    Log.d(tag, "onResults bestMatch: '$bestMatch'")
                    onResultsReceived(bestMatch)
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }
    }

    fun startListening() {
        if (speechRecognizer == null) {
            initializeRecognizer()
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-DE")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "de-DE")
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "de-DE")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        }

        try {
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            Log.e(tag, "Error starting speech listening: ${e.message}", e)
            onErrorReceived(SpeechRecognizer.ERROR_CLIENT)
        }
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
            Log.e(tag, "Error stopping speech listening: ${e.message}", e)
        }
        onListeningStateChanged(false)
    }

    fun destroy() {
        try {
            speechRecognizer?.destroy()
        } catch (e: Exception) {
            Log.e(tag, "Error destroying speech recognizer: ${e.message}", e)
        }
        speechRecognizer = null
    }
}
