package com.example.goetheb1worte.audio

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import java.util.UUID

enum class TtsTarget {
    WORD,
    EXAMPLE
}

open class TtsManager(
    context: Context? = null
) : DefaultLifecycleObserver {

    private val tag = "TtsManager"
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false
    private var pendingWord: String? = null

    private val _currentlySpeaking = MutableStateFlow<TtsTarget?>(null)
    open val currentlySpeaking: StateFlow<TtsTarget?> = _currentlySpeaking.asStateFlow()

    open val isSpeaking: StateFlow<Boolean>
        get() = MutableStateFlow(_currentlySpeaking.value != null).asStateFlow()

    init {
        context?.let { ctx ->
            val appContext = ctx.applicationContext
            textToSpeech = TextToSpeech(appContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val tts = textToSpeech
                    if (tts != null) {
                        val result = tts.setLanguage(Locale.GERMAN)
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.w(tag, "German language is not supported or missing data on this device.")
                        } else {
                            isInitialized = true
                            setupProgressListener(tts)
                            Log.i(tag, "TextToSpeech successfully initialized for Locale.GERMAN.")
                            pendingWord?.let { wordToSpeak ->
                                pendingWord = null
                                speakWord(wordToSpeak)
                            }
                        }
                    }
                } else {
                    Log.e(tag, "Failed to initialize TextToSpeech, status=$status")
                }
            }
        }
    }

    private fun setupProgressListener(tts: TextToSpeech) {
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                when {
                    utteranceId?.endsWith("_word") == true -> _currentlySpeaking.value = TtsTarget.WORD
                    utteranceId?.endsWith("_example") == true -> _currentlySpeaking.value = TtsTarget.EXAMPLE
                    else -> _currentlySpeaking.value = null
                }
            }

            override fun onDone(utteranceId: String?) {
                _currentlySpeaking.value = null
            }

            @Deprecated("Deprecated in Java", ReplaceWith("onError(utteranceId, -1)"))
            override fun onError(utteranceId: String?) {
                _currentlySpeaking.value = null
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                _currentlySpeaking.value = null
                Log.w(tag, "TTS utterance error: $utteranceId, code: $errorCode")
            }
        })
    }

    open fun speakWord(word: String) {
        val tts = textToSpeech
        if (!isInitialized || tts == null) {
            Log.w(tag, "TextToSpeech is not ready yet. Queuing word: $word")
            pendingWord = word
            return
        }

        stop()

        val cleanWord = word.replace("/", " oder ").replace("¨", "").trim()
        val utteranceId = "${UUID.randomUUID()}_word"
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }
        _currentlySpeaking.value = TtsTarget.WORD
        tts.speak(cleanWord, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }

    open fun speakExample(example: String) {
        val tts = textToSpeech
        if (!isInitialized || tts == null) {
            Log.w(tag, "TextToSpeech is not ready yet.")
            return
        }

        stop()

        val cleanExample = example
            .replace("1.", "")
            .replace("2.", "")
            .replace("3.", "")
            .replace("4.", "")
            .replace("–", "")
            .trim()

        if (cleanExample.isBlank()) return

        val utteranceId = "${UUID.randomUUID()}_example"
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }
        _currentlySpeaking.value = TtsTarget.EXAMPLE
        tts.speak(cleanExample, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }

    open fun stop() {
        pendingWord = null
        textToSpeech?.stop()
        _currentlySpeaking.value = null
    }

    open fun shutdown() {
        stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isInitialized = false
    }

    override fun onStop(owner: LifecycleOwner) {
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        shutdown()
    }
}
