package com.anton.movie_catalog_kotlin.models

data class Spouse(
    val personId: Int,
    val name: String,
    val divorced: Boolean,
    val divorcedReason: String,
    val sex: String,
    val children: Int,
    val webUrl: String,
    val relation: String
)