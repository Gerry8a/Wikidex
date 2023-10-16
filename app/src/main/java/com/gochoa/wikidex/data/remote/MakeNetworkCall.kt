package com.gochoa.wikidex.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponseStatus.Error("501")
    } catch (e: HttpException) {
        ApiResponseStatus.Error(e.message!!)
    } catch (e: IOException){
        ApiResponseStatus.Error(e.message!!)
    } catch (e: Exception){
        ApiResponseStatus.Error(e.message!!)
    } catch (e: SocketTimeoutException){
        ApiResponseStatus.Error(e.message!!)
    }
}