package com.anton.movie_catalog_kotlin.repository

import android.util.Log
import com.anton.movie_catalog_kotlin.models_old.ReviewModifyModel
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi


interface ReviewRepository {
    suspend fun postReview(movieId: String, postReviewRequest: ReviewModifyModel)
    suspend fun putReview(movieId: String, id: String, postReviewRequest: ReviewModifyModel)
    suspend fun deleteReview(id: String, postReviewRequest: ReviewModifyModel)
}

class ReviewRepositoryImpl(private val movieCatalogApi: MovieCatalogApi) : ReviewRepository {
    override suspend fun postReview(movieId: String, postReviewBody: ReviewModifyModel) {
        try {
            movieCatalogApi.postReview(movieId, postReviewBody)
        } catch (e: Exception) {
            Log.e("postReview", e.message.toString())
        }
    }

    override suspend fun putReview(
        movieId: String,
        id: String,
        postReviewRequest: ReviewModifyModel
    ) {
        try {
            movieCatalogApi.putReview(id, postReviewRequest)
        } catch (e: Exception) {
            Log.e("putReview", e.message.toString())
        }
    }

    override suspend fun deleteReview(id: String, postReviewRequest: ReviewModifyModel) {
        try {
            movieCatalogApi.putReview(id, postReviewRequest)
        } catch (e: Exception) {
            Log.e("deleteReview", e.message.toString())
        }

    }
}