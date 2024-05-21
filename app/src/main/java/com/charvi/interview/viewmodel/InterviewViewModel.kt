package com.charvi.interview.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charvi.interview.api.ApiClient
import com.charvi.interview.model.OpenAiMessage
import com.charvi.interview.model.OpenAiRequest

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InterviewViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _designation = MutableStateFlow("")
    val designation: StateFlow<String> = _designation.asStateFlow()

    private val _industry = MutableStateFlow("")
    val industry: StateFlow<String> = _industry.asStateFlow()

    private val _yearsOfExperience = MutableStateFlow("")
    val yearsOfExperience: StateFlow<String> = _yearsOfExperience.asStateFlow()

    private val _interviewQuestions = MutableStateFlow<List<String>>(emptyList())
    val interviewQuestions: StateFlow<List<String>> = _interviewQuestions.asStateFlow()

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onLastNameChange(newLastName: String) {
        _lastName.value = newLastName
    }

    fun onDesignationChange(newDesignation: String) {
        _designation.value = newDesignation
    }

    fun onIndustryChange(newIndustry: String) {
        _industry.value = newIndustry
    }

    fun onYearsOfExperienceChange(newYearsOfExperience: String) {
        _yearsOfExperience.value = newYearsOfExperience
    }

    fun generateInterviewQuestions() {
        viewModelScope.launch {
            val prompt = "Generate basic interview questions in MCQ pattern for a software engineering industry. The designation is Android Developer with 2 years of experience."
            val message = OpenAiMessage(role = "system", content = prompt)
            val request = OpenAiRequest(
                model = "gpt-3.5-turbo-0301",
                messages = listOf(message),
                max_tokens = 150,
                temperature = 0.7,
                top_p = 1.0,
                frequency_penalty = 0.0,
                presence_penalty = 0.0
            )

            try {
                val response = ApiClient.apiService.generateInterviewQuestions(request)
                if (response.isSuccessful) {
                    response.body()?.let { openAiResponse ->
                        Log.d("InterviewViewModel", "API Response: $openAiResponse")
                        val questions = openAiResponse.choices.mapNotNull { it.message.content?.trim() }
                        _interviewQuestions.value = questions
                        Log.d("InterviewViewModel", "Generated Questions: $questions")
                    }
                } else {
                    Log.e("InterviewViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("InterviewViewModel", "Exception: ${e.message}", e)
            }
        }
    }
}