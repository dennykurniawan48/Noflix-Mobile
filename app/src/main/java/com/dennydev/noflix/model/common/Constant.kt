package com.dennydev.noflix.model.common

object Constant {
    const val BASE_URL = "http://freenoflix.000webhostapp.com"
    const val BASE_API = "$BASE_URL/api/"
    const val URL_LOGIN = "${BASE_API}login"
    const val URL_REGISTER = "${BASE_API}register"
    const val URL_MOVIES = "${BASE_API}movies"
    const val URL_MOVIE = "$URL_MOVIES/{id}"
}