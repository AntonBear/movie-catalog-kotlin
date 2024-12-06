package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.ErrorResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T, ErrorResponse>> {

    override fun enqueue(callback: Callback<NetworkResult<T, ErrorResponse>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult: NetworkResult<T, ErrorResponse> = with(response) {
                    val body = body()
                    if (isSuccessful && body != null) {
                        NetworkResult.Success(body)
                    } else {
                        try {
                            errorBody()
                                ?.string()
                                ?.let { Json.decodeFromString<ErrorResponse>(it) }
                                ?.let { NetworkResult.Error(it) }
                                ?: NetworkResult.Exception(SerializationException())
                        } catch (e: Exception) {
                            NetworkResult.Exception(e)
                        }
                    }
                }
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = NetworkResult.Exception<T, ErrorResponse>(t)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<NetworkResult<T, ErrorResponse>> = throw NotImplementedError()
    override fun clone(): Call<NetworkResult<T, ErrorResponse>> = NetworkResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() {
        proxy.cancel()
    }
}