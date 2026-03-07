package com.fadymarty.rak_gpt.data.repository

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.fadymarty.rak_gpt.domain.repository.AudioPlayer
import com.linc.amplituda.Amplituda
import java.io.File

class AndroidAudioPlayer(
    private val context: Context,
    private val amplituda: Amplituda,
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File, onCompletion: () -> Unit) {
        stopAndRelease()
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
            setOnCompletionListener {
                stopAndRelease()
                onCompletion()
            }
        }
    }

    override fun pause() {
        player?.pause()
    }

    override fun resume() {
        player?.start()
    }

    override fun seekForward() {
        player?.let {
            it.seekTo(it.currentPosition + 10000)
        }
    }

    override fun getAmplitudes(file: File): List<Int> {
        return amplituda.processAudio(file)
            .get()
            .amplitudesAsList()
    }

    private fun stopAndRelease() {
        player?.stop()
        player?.release()
        player = null
    }
}