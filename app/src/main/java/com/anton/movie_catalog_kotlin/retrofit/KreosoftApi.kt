package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models.FavoritesMoviesListModel
import com.anton.movie_catalog_kotlin.models.LoginResponse
import com.anton.movie_catalog_kotlin.models.MovieDetailsModel
import com.anton.movie_catalog_kotlin.models.MoviesPagedListModel
import com.anton.movie_catalog_kotlin.models.ProfileModel
import com.anton.movie_catalog_kotlin.models.ReviewShortModel
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.models.SignUpResponse
import models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KreosoftApi {

    //Auth

    @POST("api/account/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/account/register")
    suspend fun register(@Body request: SignUpRequest): SignUpResponse

    @POST("/api/account/logout")
    suspend fun logout(): Result<Unit>

    //FavoriteMovies

    @GET("/api/favorites")
    suspend fun getFavoritesMovies(): FavoritesMoviesListModel

    @POST("/api/favorites/{movieId}/add")
    suspend fun addFavoritesMovie(@Path("movieId") movieId: String): Result<Unit>

    @DELETE("/api/favorites/{movieId}/delete")
    suspend fun deleteFavoriteMovie(@Path("movieId") movieId: String): Result<Unit>

    //Movie
    @GET("/api/movies/{page}")
    suspend fun getMoviesPage(@Path("page") page: Int): Response<MoviesPagedListModel>

    @GET("/api/movies/detail/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): Response<MovieDetailsModel>

    //Review
    @POST("/api/movie/{movieId}/review/add")
    suspend fun addReview(@Path("movieId") movieId: Int, @Body request: ReviewShortModel): Result<Unit>

    @PUT("/api/movie/{movieId}/review/{reviewId}/edit")
    suspend fun editReview(@Path("movieId") movieId: Int,
                           @Path ("reviewId") reviewId: Int,
                           @Body request: ReviewShortModel): Result<Unit>

    @DELETE("/api/movie/{movieId}/review/{reviewId}/delete")
    suspend fun deleteReview(@Path("movieId") movieId: Int, @Path("reviewId") reviewId: Int): Result<Unit>

    //User
    @GET("/api/account/profile")
    suspend fun getUserProfile(): Response<ProfileModel>

    @PUT("/api/account/profile")
    suspend fun editUserProfile(@Body request: ProfileModel): Response<ProfileModel>
}