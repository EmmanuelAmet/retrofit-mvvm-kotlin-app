package com.emmanuelamet.retrofit_mvvm.api

import com.emmanuelamet.retrofit_mvvm.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>
}