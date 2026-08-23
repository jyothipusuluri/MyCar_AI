package com.zeroclaw.android.assistant

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AssistantManager(
    private val baseUrl: String = "http://127.0.0.1:5000"
) : AssistantService {

    private val client = OkHttpClient()

    override suspend fun ask(query: String): String = withContext(Dispatchers.IO) {
        val payload = "{\"query\": ${escapeJson(query)} }"
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = payload.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("$baseUrl/assistant")
            .post(body)
            .build()

        client.newCall(request).execute().use { resp ->
            if (!resp.isSuccessful) {
                throw Exception("Assistant request failed: ${'$'}{resp.code}")
            }
            resp.body?.string() ?: ""
        }
    }

    private fun escapeJson(s: String) = '"' + s.replace("\\", "\\\\").replace('"', "\\\"") + '"'
}
