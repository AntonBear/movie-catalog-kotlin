package com.anton.movie_catalog_kotlin.models

data class Staff(
    val personId: Int,
    val webUrl: String,
    val nameRu: String,
    val nameEn: String,
    val sex: String,
    val posterUrl: String,
    val growth: Int?,
    val birthday: String?,
    val death: String?,
    val age: Int,
    val birthplace: String?,
    val deathplace: String?,
    val spouses: List<Spouse>,
    val hasAwards: Int,
    val profession: String?,
    val facts: List<String>,
    val films: List<Film>
)




//{
//    "userName": "Anton",
//    "name": "AntonBer",
//    "password": "greedisgood",
//    "email": "holzed15@gmail.com",
//    "birthDate": "2024-11-06T10:26:03.128Z",
//    "gender": 1
//}

//
//{
//    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IkFudG9uIiwiZW1haWwiOiJob2x6ZWQxNUBnbWFpbC5jb20iLCJuYmYiOjE3MzA5ODQyMTQsImV4cCI6MTczMDk4NzgxNCwiaWF0IjoxNzMwOTg0MjE0LCJpc3MiOiJodHRwczovL3JlYWN0LW1pZHRlcm0ua3Jlb3NvZnQuc3BhY2UvIiwiYXVkIjoiaHR0cHM6Ly9yZWFjdC1taWR0ZXJtLmtyZW9zb2Z0LnNwYWNlLyJ9.aRNiqfZ-0n7CYAviHlrepUh4pejq-3f4H75r3rotjvU"
//}