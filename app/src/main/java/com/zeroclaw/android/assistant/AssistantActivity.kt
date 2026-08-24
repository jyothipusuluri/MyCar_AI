package com.zeroclaw.android.assistant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Very small activity that accepts the deep link and calls the AssistantService.
 * Shows a short response in a TextView. Keep UI minimal and quick.
 */
class AssistantActivity : AppCompatActivity() {

    private val assistant: AssistantService by lazy { AssistantManager("http://127.0.0.1:5000") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tv = TextView(this).apply {
            text = "Contacting assistant..."
            setPadding(32, 32, 32, 32)
        }
        setContentView(tv)

        val query = intent?.getStringExtra("q") ?: "Start assistant"

        lifecycleScope.launch {
            try {
                val resp = assistant.ask(query)
                tv.text = resp
            } catch (e: Exception) {
                tv.text = "Assistant error: ${'$'}{e.message}"
            }
        }
    }
}
