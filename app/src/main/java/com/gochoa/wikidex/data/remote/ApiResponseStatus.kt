package com.gochoa.wikidex.data.remote

sealed class ApiResponseStatus<T> {
    class Success<T>(val data: T): ApiResponseStatus<T>()
    class Loading<T>: ApiResponseStatus<T>()
    class Error<T>(val messageID: String): ApiResponseStatus<T>()
}