package com.fadymarty.rak_gpt.domain.manager

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun deleteAccessToken()
}