package com.anton.movie_catalog_kotlin.feedScreen

import com.anton.movie_catalog_kotlin.repository.MovieRepository
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.models.ImageSource
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.models.MovieElementModel
import com.anton.movie_catalog_kotlin.models.MoviesPagedListModel
import com.anton.movie_catalog_kotlin.models.PageInfoModel

class MovieRepositoryStub : MovieRepository {

    override suspend fun fetchMovies(page: Int): Result<MoviesPagedListModel> {
        val movies = MoviesPagedListModel(
            listOf(
                MovieElementModel()
            ),
            PageInfoModel(
                pageSize = 10,
                pageCount = 1,
                currentPage = 1
            )
        )
        return Result.success(movies)
    }

    override suspend fun getRandomMoviePosterWithDetails(): MovieDetails {
        return MovieDetails(
            id = "",
            name = "NameStub",
            poster = ImageSource.Local(R.drawable.background),
            year = 2024,
            country = "N/A",
            genres = listOf("Action", "Comedy")
        )
    }

    override suspend fun getMovies(page: Int): List<MovieElementModel> {
        return listOf(
            MovieElementModel("b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2")
        )
    }
}