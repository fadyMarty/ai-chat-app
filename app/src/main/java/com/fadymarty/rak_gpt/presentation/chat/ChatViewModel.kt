package com.fadymarty.rak_gpt.presentation.chat

import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatApi
import com.fadymarty.rak_gpt.domain.model.Message
import com.fadymarty.rak_gpt.domain.model.Role
import com.fadymarty.rak_gpt.domain.repository.AudioRecorder
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import com.linc.amplituda.Amplituda
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val audioRecorder: AudioRecorder,
    val player: ExoPlayer,
    val amplituda: Amplituda,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private var streamingJob: Job? = null
    private var recordingJob: Job? = null


    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.EditPrompt -> {
                _state.value.messageState.setTextAndPlaceCursorAtEnd(event.prompt)
            }

            is ChatEvent.SendMessage -> onSendMessage(event.prompt)

            ChatEvent.StopGeneration -> {
                streamingJob?.cancel()
                _state.update {
                    it.copy(isLoading = false)
                }
            }

            is ChatEvent.StartRecording -> onStartRecording(event.outputFile)

            ChatEvent.StopRecording -> onStopRecording()

            is ChatEvent.PlayPause -> onPlayPause(event.message)

            ChatEvent.SeekForward -> {
                player.seekForward()
            }
        }
    }

    private fun onSendMessage(prompt: String? = null) {
        val userMessage = Message(
            role = Role.USER,
            content = prompt ?: _state.value.messageState.text.toString()
        )
        val assistantMessage = Message(
            role = Role.ASSISTANT,
            content = ""
        )
        _state.update {
            it.copy(
                isLoading = true,
                messages = listOf(assistantMessage, userMessage) + it.messages
            )
        }
        _state.value.messageState.clearText()

        streamingJob?.cancel()
        streamingJob = chatRepository.sendMessage(
            messages = _state.value.messages
                .filter { it.id != assistantMessage.id }
                .reversed()
        ).onEach { result ->
            result.onSuccess { content ->
                _state.update {
                    it.copy(
                        messages = it.messages.map { message ->
                            if (message.id == assistantMessage.id) {
                                message.copy(content = message.content + content)
                            } else message
                        }
                    )
                }
            }
        }.onCompletion {
            _state.update {
                it.copy(isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    private fun onStartRecording(outputFile: File) {
        _state.update {
            it.copy(
                isRecording = true,
                audioFile = outputFile,
                amplitudes = emptyList(),
                recordingSeconds = 0
            )
        }
        audioRecorder.start(outputFile)

        recordingJob?.cancel()
        recordingJob = viewModelScope.launch {
            var tickCount = 0
            while (true) {
                delay(100L)
                val amplitude = audioRecorder.getMaxAmplitude()
                tickCount++
                _state.update {
                    it.copy(
                        amplitudes = (it.amplitudes + amplitude).takeLast(100),
                        recordingSeconds = tickCount / 10
                    )
                }
            }
        }
    }

    private fun onStopRecording() {
        _state.update {
            it.copy(isRecording = false)
        }
        recordingJob?.cancel()
        audioRecorder.stop()
        val userMessage = Message(
            role = Role.USER,
            attachments = listOf("96a73e92-6343-4e2f-b5ef-62fdba5805fd"),
            fileUrl = "${GigaChatApi.BASE_URL}files/96a73e92-6343-4e2f-b5ef-62fdba5805fd/content"
        )
        val assistantMessage = Message(
            role = Role.ASSISTANT,
            content = ""
        )
        _state.update {
            it.copy(
                isLoading = true,
                audioFile = null,
                messages = listOf(assistantMessage, userMessage) + it.messages
            )
        }
//        _state.value.audioFile?.let { audioFile ->
//            viewModelScope.launch {
//                chatRepository.uploadFile(audioFile)
//                    .onSuccess { fileResponse ->
//                        val userMessage = Message(
//                            role = Role.USER,
//                            attachments = listOf(fileResponse.id),
//                            fileUrl = "${GigaChatApi.BASE_URL}/files/${fileResponse.id}/content"
//                        )
//                        val assistantMessage = Message(
//                            role = Role.ASSISTANT,
//                            content = ""
//                        )
//                        _state.update {
//                            it.copy(
//                                isLoading = true,
//                                audioFile = null,
//                                messages = listOf(assistantMessage, userMessage) + it.messages
//                            )
//                        }
//
//                        streamingJob?.cancel()
//                        streamingJob = chatRepository.sendMessage(
//                            messages = _state.value.messages
//                                .filter { it.id != assistantMessage.id }
//                                .reversed()
//                        ).onEach { result ->
//                            result.onSuccess { content ->
//                                _state.update {
//                                    it.copy(
//                                        messages = it.messages.map { message ->
//                                            if (message.id == assistantMessage.id) {
//                                                message.copy(
//                                                    content = message.content + content
//                                                )
//                                            } else message
//                                        }
//                                    )
//                                }
//                            }
//                        }.onCompletion {
//                            _state.update {
//                                it.copy(isLoading = false)
//                            }
//                        }.launchIn(viewModelScope)
//                    }
//            }
//        }
    }

    private fun onPlayPause(message: Message) {
        message.fileUrl?.let { fileUrl ->
            if (message.id != _state.value.playingMessageId) {
                val amplitudes = amplituda
                    .processAudio(fileUrl)
                    .get()
                    .amplitudesAsList()
                _state.update {
                    it.copy(
                        messages = it.messages.map { newMessage ->
                            if (newMessage.id == message.id) {
                                newMessage.copy(amplitudes = amplitudes)
                            } else newMessage
                        }
                    )
                }

                player.setMediaItem(MediaItem.fromUri(fileUrl))
                player.prepare()
                player.play()

                _state.update {
                    it.copy(playingMessageId = message.id)
                }
            } else {
                player.pause()
            }
        }
    }
}