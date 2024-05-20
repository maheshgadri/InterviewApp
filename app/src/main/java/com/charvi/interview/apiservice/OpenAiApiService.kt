package com.charvi.interview.apiservice

import com.charvi.interview.model.OpenAiRequest
import com.charvi.interview.model.OpenAiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApiService {
    @Headers("Authorization: Bearer ")
    @POST("v1/chat/completions")
    suspend fun generateInterviewQuestions(@Body request: OpenAiRequest): Response<OpenAiResponse>
}