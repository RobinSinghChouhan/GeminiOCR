package com.example.geminiocr

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GeminiInterface {
        @GET("api/gemini/{question}")
        fun getResponse(@Path("question") question: String?): Call<JsonObject>
}
