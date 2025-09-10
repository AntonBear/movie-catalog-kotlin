package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models_old.FavoritesMoviesListModel
import com.anton.movie_catalog_kotlin.models_old.LoginResponse
import com.anton.movie_catalog_kotlin.models_old.MovieDetailsModel
import com.anton.movie_catalog_kotlin.models_old.MoviesPagedListModel
import com.anton.movie_catalog_kotlin.models_old.ProfileModel
import com.anton.movie_catalog_kotlin.models_old.ReviewShortModel
import com.anton.movie_catalog_kotlin.models_old.SignUpRequest
import com.anton.movie_catalog_kotlin.models_old.SignUpResponse
import models.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KreosoftApi {

    //Auth

    @POST("api/account/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/account/register")
    suspend fun register(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/api/account/logout")
    suspend fun logout(): Response<Unit>

    //FavoriteMovies

    @GET("/api/favorites")
    suspend fun getFavoritesMovies(): Response<FavoritesMoviesListModel>

    @POST("/api/favorites/{movieId}/add")
    suspend fun addFavoritesMovie(@Path("movieId") movieId: String): Response<Unit>

    @DELETE("/api/favorites/{movieId}/delete")
    suspend fun deleteFavoriteMovie(@Path("movieId") movieId: String): Response<Unit>

    //Movie
    @GET("/api/movies/{page}")
    suspend fun getMoviesPage(@Path("page") page: Int): Response<MoviesPagedListModel>

    @GET("/api/movies/details/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: String): Response<MovieDetailsModel>

    @GET("/api/movies/details/{movieId}")
    suspend fun getMovieDetailRaw(@Path("movieId") movieId: String): Response<ResponseBody>

    //Review
    @POST("/api/movie/{movieId}/review/add")
    suspend fun addReview(@Path("movieId") movieId: String, @Body request: ReviewShortModel): Response<Unit>

    @PUT("/api/movie/{movieId}/review/{reviewId}/edit")
    suspend fun editReview(@Path("movieId") movieId: String,
                           @Path ("reviewId") reviewId: String,
                           @Body request: ReviewShortModel): Response<Unit>

    @DELETE("/api/movie/{movieId}/review/{reviewId}/delete")
    suspend fun deleteReview(@Path("movieId") movieId: String, @Path("reviewId") reviewId: String): Response<Unit>

    //User
    @GET("/api/account/profile")
    suspend fun getUserProfile(): Response<ProfileModel>

    @PUT("/api/account/profile")
    suspend fun editUserProfile(@Body request: ProfileModel): Response<ProfileModel>
}