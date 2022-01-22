package com.saveo.utils

object Constants {
    const val BASE_URl = "https://api.themoviedb.org/3/"
    const val IMGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "2835bacff4ddcef6e509e17c75776c6a"
    const val READ_ACCESS_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODM1YmFjZmY0ZGRjZWY2ZTUwOWUxN2M3NTc3NmM2YSIsInN1YiI6IjYxZWE1YjUwNTM0NjYxMDA0M2MxYzVkMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.6SULwy0Z6uUOwLp5zpsb1dsIqmvwka9j2X-B8AOY7D8"

    object HttpCode {
        const val SUCCESS = 200
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER = 500
    }
}