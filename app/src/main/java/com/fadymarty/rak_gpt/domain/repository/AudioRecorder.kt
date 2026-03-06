package com.fadymarty.rak_gpt.domain.repository

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
    fun getMaxAmplitude(): Int
}