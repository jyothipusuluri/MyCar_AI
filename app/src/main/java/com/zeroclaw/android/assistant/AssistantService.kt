package com.zeroclaw.android.assistant

/**
 * Minimal interface for asking the assistant for a short response.
 * Implementation should call the local server on the phone (edge-based).
 */
interface AssistantService {
    suspend fun ask(query: String): String
}
