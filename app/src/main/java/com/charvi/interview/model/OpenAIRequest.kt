package com.charvi.interview.model

data class OpenAiRequest(
    val model: String,
    val messages: List<OpenAiMessage>,
    val max_tokens: Int,
    val temperature: Double,
    val top_p: Double,
    val frequency_penalty: Double,
    val presence_penalty: Double
)

data class OpenAiMessage(
    val role: String,
    val content: String
)

data class OpenAiResponse(
    val id: String,
    val objectType: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>
) {
    data class Choice(
        val text: String,
        val index: Int,
        val logprobs: Any?,
        val finish_reason: String
    )
}
