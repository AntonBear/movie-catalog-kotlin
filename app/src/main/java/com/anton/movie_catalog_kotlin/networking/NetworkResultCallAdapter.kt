package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.ErrorResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<NetworkResult<Type, ErrorResponse>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type, ErrorResponse>> {
        return NetworkResultCall(call)
    }
}