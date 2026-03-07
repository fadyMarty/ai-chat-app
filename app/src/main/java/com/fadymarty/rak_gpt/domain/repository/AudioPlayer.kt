package com.fadymarty.rak_gpt.domain.repository

import java.io.File

interface AudioPlayer {
    fun playFile(file: File, onCompletion: () -> Unit)
    fun pause()
    fun resume()
    fun seekForward()
    fun getAmplitudes(file: File): List<Int>
}