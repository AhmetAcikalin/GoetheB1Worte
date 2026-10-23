package com.example.goetheb1worte.audio

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class SoundEffectManagerTest {

    @Test
    fun testNoOpSoundEffectManagerDoesNotThrow() {
        val noOp = NoOpSoundEffectManager()
        noOp.playCorrect()
        noOp.playWrong()
        noOp.release()
    }

    @Test
    fun testCustomSoundEffectManagerTracksInvocations() {
        var correctCount = 0
        var wrongCount = 0
        var releaseCount = 0

        val soundManager = object : SoundEffectManager {
            override fun playCorrect() { correctCount++ }
            override fun playWrong() { wrongCount++ }
            override fun release() { releaseCount++ }
        }

        soundManager.playCorrect()
        soundManager.playCorrect()
        soundManager.playWrong()
        soundManager.release()

        assertEquals(2, correctCount)
        assertEquals(1, wrongCount)
        assertEquals(1, releaseCount)
    }

    @Test
    fun testDuolingoCorrectMp3AssetIsValid() {
        val file = File("src/main/res/raw/duolingo_correct.mp3")
        assertTrue("duolingo_correct.mp3 must exist in src/main/res/raw", file.exists())
        assertTrue("duolingo_correct.mp3 must not be empty", file.length() > 5000)
    }

    @Test
    fun testDuolingoWrongMp3AssetIsValid() {
        val file = File("src/main/res/raw/duolingo_wrong.mp3")
        assertTrue("duolingo_wrong.mp3 must exist in src/main/res/raw", file.exists())
        assertTrue("duolingo_wrong.mp3 must not be empty", file.length() > 5000)
    }
}
