package com.anton.movie_catalog_kotlin.room

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val id: String,
    val name: String,
    var isFavorite: Boolean = false
)


@Dao
interface GenreDao {
    @Query("SELECT * FROM genres WHERE id = :genreId")
    suspend fun getGenreById(genreId: String): Genre?

    @Query("UPDATE genres SET isFavorite = :isFavorite WHERE id = :genreId")
    suspend fun updateGenre(genreId: String, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<Genre>)

}


//class GenreRepository(private val genreDao: GenreDao) {
//    suspend fun getGenreById(genreId: String): Genre? = genreDao.getGenreById(genreId)
//    suspend fun updateGenre(genreId: String, isFavorite: Boolean) = genreDao.updateGenre(genreId, isFavorite)
//    suspend fun insertGenre(genre: Genre) = genreDao.insertGenre(genre)
//    suspend fun getAllGenres(): List<Genre> = genreDao.getAllGenres()
//    suspend fun insertGenres(genres: List<Genre>) = genreDao.insertGenres(genres)
//}

interface GenreRepository {
    suspend fun getGenreById(genreId: String): Genre?
    suspend fun updateGenre(genreId: String, isFavorite: Boolean)
    suspend fun insertGenre(genre: Genre)
    suspend fun getAllGenres(): List<Genre>
    suspend fun insertGenres(genres: List<Genre>)
}

class GenreRepositoryImpl(private val genreDao: GenreDao) : GenreRepository {
    override suspend fun getGenreById(genreId: String): Genre? = genreDao.getGenreById(genreId)
    override suspend fun updateGenre(genreId: String, isFavorite: Boolean) = genreDao.updateGenre(genreId, isFavorite)
    override suspend fun insertGenre(genre: Genre) = genreDao.insertGenre(genre)
    override suspend fun getAllGenres(): List<Genre> = genreDao.getAllGenres()
    override suspend fun insertGenres(genres: List<Genre>) = genreDao.insertGenres(genres)

}