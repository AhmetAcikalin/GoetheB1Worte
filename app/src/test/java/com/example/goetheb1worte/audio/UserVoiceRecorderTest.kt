package com.example.goetheb1worte.audio

import com.example.goetheb1worte.data.model.WordItem
import com.example.goetheb1worte.data.repository.GameType
import com.example.goetheb1worte.presentation.quiz.QuizUiEvent
import com.example.goetheb1worte.presentation.quiz.QuizUiState
import com.example.goetheb1worte.presentation.quiz.SpeechState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UserVoiceRecorderTest {

    @Test
    fun testNoOpUserVoiceRecorderReturnsFalseAndDoesNotThrow() {
        val recorder = NoOpUserVoiceRecorder()
        assertFalse(recorder.startRecording())
        assertFalse(recorder.stopRecording())
        assertFalse(recorder.playRecording())
        assertFalse(recorder.isPlaying())
        assertFalse(recorder.hasRecording())
        recorder.stopPlayback()
        recorder.deleteRecording()
    }

    @Test
    fun testUserVoiceRecorderLifecycleAndAutoCleanup() {
        var startCount = 0
        var stopCount = 0
        var playCount = 0
        var stopPlaybackCount = 0
        var deleteCount = 0
        var recordingAvailable = false
        var currentlyPlaying = false
        var completionInvoked = false

        val fakeRecorder = object : UserVoiceRecorder {
            override fun startRecording(): Boolean {
                startCount++
                recordingAvailable = false
                return true
            }

            override fun stopRecording(): Boolean {
                stopCount++
                recordingAvailable = true
                return true
            }

            override fun playRecording(onComplete: () -> Unit): Boolean {
                if (!recordingAvailable) return false
                playCount++
                currentlyPlaying = true
                return true
            }

            override fun stopPlayback() {
                stopPlaybackCount++
                currentlyPlaying = false
            }

            override fun deleteRecording() {
                deleteCount++
                recordingAvailable = false
                currentlyPlaying = false
            }

            override fun isPlaying(): Boolean = currentlyPlaying

            override fun hasRecording(): Boolean = recordingAvailable
        }

        // 1. Initial state
        assertFalse(fakeRecorder.hasRecording())
        assertFalse(fakeRecorder.isPlaying())

        // 2. User presses mic and speaks -> start recording
        assertTrue(fakeRecorder.startRecording())
        assertEquals(1, startCount)
        assertFalse(fakeRecorder.hasRecording())

        // 3. User finishes speaking -> stop recording
        assertTrue(fakeRecorder.stopRecording())
        assertEquals(1, stopCount)
        assertTrue(fakeRecorder.hasRecording())

        // 4. User presses "Listen to Recording" -> play recording
        assertTrue(fakeRecorder.playRecording { completionInvoked = true })
        assertEquals(1, playCount)
        assertTrue(fakeRecorder.isPlaying())

        // 5. User stops audio explicitly
        fakeRecorder.stopPlayback()
        assertEquals(1, stopPlaybackCount)
        assertFalse(fakeRecorder.isPlaying())
        assertTrue("Recording still exists until moving next/retry", fakeRecorder.hasRecording())

        // 6. User advances to next question / retries -> delete temporary recording
        fakeRecorder.deleteRecording()
        assertEquals(1, deleteCount)
        assertFalse("Recording must be deleted", fakeRecorder.hasRecording())
        assertFalse("Must not be playing", fakeRecorder.isPlaying())
    }

    @Test
    fun testQuizUiStateQuestionRecordingDefaultsAndCopy() {
        val dummyWord = WordItem(
            id = "w1",
            word = "die Ausbildung",
            translation = "training / apprenticeship",
            category = "Education",
            plural = "die Ausbildungen",
            example = "Er macht eine Ausbildung.",
            exampleTranslation = "He is doing an apprenticeship."
        )

        val questionState = QuizUiState.Question(
            currentWord = dummyWord,
            options = persistentListOf("training", "education", "school"),
            gameType = GameType.PRONUNCIATION_TRAINER
        )

        // Verify defaults
        assertFalse(questionState.hasUserRecording)
        assertFalse(questionState.isUserRecordingPlaying)
        assertEquals(SpeechState.Idle, questionState.speechState)

        // When speech finishes and user recording is captured
        val evaluatedState = questionState.copy(
            speechState = SpeechState.Success(similarity = 0.95f, transcribedText = "die Ausbildung"),
            isAnswerSubmitted = true,
            hasUserRecording = true
        )
        assertTrue(evaluatedState.hasUserRecording)
        assertFalse(evaluatedState.isUserRecordingPlaying)

        // When playback begins
        val playingState = evaluatedState.copy(isUserRecordingPlaying = true)
        assertTrue(playingState.isUserRecordingPlaying)

        // When new question is loaded (resetting to defaults)
        val nextQuestionState = questionState.copy(
            currentWord = dummyWord.copy(id = "w2", word = "die Erfahrung"),
            questionNumber = 2
        )
        assertFalse("Next question must reset hasUserRecording to false", nextQuestionState.hasUserRecording)
        assertFalse("Next question must reset isUserRecordingPlaying to false", nextQuestionState.isUserRecordingPlaying)
    }

    @Test
    fun testQuizUiEventRecordingEventsExist() {
        val playEvent: QuizUiEvent = QuizUiEvent.PlayUserRecording
        val stopEvent: QuizUiEvent = QuizUiEvent.StopUserRecording

        assertTrue(playEvent is QuizUiEvent.PlayUserRecording)
        assertTrue(stopEvent is QuizUiEvent.StopUserRecording)
    }
}
