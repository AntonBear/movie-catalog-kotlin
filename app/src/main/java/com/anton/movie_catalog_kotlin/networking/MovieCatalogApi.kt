package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models_old.LoginResponse
import com.anton.movie_catalog_kotlin.models_old.MovieDetailsModel
import com.anton.movie_catalog_kotlin.models_old.MovieListModel
import com.anton.movie_catalog_kotlin.models_old.MoviesPagedListModel
import com.anton.movie_catalog_kotlin.models_old.ProfileModel
import com.anton.movie_catalog_kotlin.models_old.ReviewModifyModel
import com.anton.movie_catalog_kotlin.models_old.SignUpRequest
import models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovieCatalogApi {
    @POST("api/account/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<LoginResponse>

    @POST("api/account/login")
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/movies/{page}")
    suspend fun getMovies(@Path("page") page: Int): Response<MoviesPagedListModel>

    @GET("/api/movies/details/{id}")
    suspend fun getDetails(@Path("id") id: String): Response<MovieDetailsModel>

    @POST("/api/movie/{movieId}/review/add")
    suspend fun postReview(
        @Path("movieId") movieId: String,
        @Body postReviewRequest: ReviewModifyModel
    ): Response<Unit>

    @PUT("api/movie/{movieId}/review/{id}/edit")
    suspend fun putReview(
        @Path("movieId") id: String,
        @Body postReviewRequest: ReviewModifyModel
    ): Response<Unit>

    @DELETE("api/movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(
        @Path("movieId") movieId: String,
        @Path("id") id: String
    ): Response<Unit>

    @GET("api/account/profile")
    suspend fun getProfile(): ProfileModel

    @PUT("api/account/profile")
    suspend fun putProfile(@Body body: ProfileModel)

    @GET("api/favorites")
    suspend fun getFavoriteMovies(): Response<MovieListModel>

    @POST("api/favorites/{id}/add")
    suspend fun postFavoriteMovies(@Path("id") movieId: String): Response<Unit>

    @DELETE("api/favorites/{id}/delete")
    suspend fun deleteFavoriteMovies(@Path("id") movieId: String): Response<Unit>


}



