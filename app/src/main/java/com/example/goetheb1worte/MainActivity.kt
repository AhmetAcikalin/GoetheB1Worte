package com.example.goetheb1worte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.goetheb1worte.audio.AndroidSoundEffectManager
import com.example.goetheb1worte.audio.AndroidUserVoiceRecorder
import com.example.goetheb1worte.audio.SoundEffectManager
import com.example.goetheb1worte.audio.TtsManager
import com.example.goetheb1worte.audio.UserVoiceRecorder
import com.example.goetheb1worte.data.repository.UserProgressRepositoryImpl
import com.example.goetheb1worte.data.repository.VocabularyRepositoryImpl
import com.example.goetheb1worte.localization.LanguageManager
import com.example.goetheb1worte.presentation.quiz.QuizScreen
import com.example.goetheb1worte.presentation.quiz.QuizViewModel
import com.example.goetheb1worte.ui.theme.GoetheB1WorteTheme

class MainActivity : ComponentActivity() {

    private lateinit var ttsManager: TtsManager
    private lateinit var soundEffectManager: SoundEffectManager
    private lateinit var userVoiceRecorder: UserVoiceRecorder

    private val quizViewModel: QuizViewModel by viewModels {
        val repository = VocabularyRepositoryImpl(applicationContext)
        val progressRepository = UserProgressRepositoryImpl(applicationContext)
        val languageManager = LanguageManager(applicationContext)
        QuizViewModel.provideFactory(
            repository = repository,
            progressRepository = progressRepository,
            ttsManager = ttsManager,
            soundEffectManager = soundEffectManager,
            languageManager = languageManager,
            userVoiceRecorder = userVoiceRecorder
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ttsManager = TtsManager(this)
        lifecycle.addObserver(ttsManager)

        val soundManager = AndroidSoundEffectManager(this)
        soundEffectManager = soundManager
        lifecycle.addObserver(soundManager)

        val voiceRecorder = AndroidUserVoiceRecorder(this)
        userVoiceRecorder = voiceRecorder
        lifecycle.addObserver(voiceRecorder)

        setContent {
            GoetheB1WorteTheme {
                QuizScreen(viewModel = quizViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::ttsManager.isInitialized) {
            ttsManager.shutdown()
        }
        if (::soundEffectManager.isInitialized) {
            soundEffectManager.release()
        }
        if (::userVoiceRecorder.isInitialized) {
            userVoiceRecorder.deleteRecording()
        }
    }
}
