package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val errors: Map<String, ErrorDetails> = emptyMap()
)

@Serializable
data class ErrorDetails(
    val rawValue: String?,
    val attemptedValue: String?,
    val errors: List<Error>,
    val validationState: Int,
    val isContainerNode: Boolean,
    val children: String?
)

@Serializable
data class Error(
    val exception: String?,
    val errorMessage: String
)