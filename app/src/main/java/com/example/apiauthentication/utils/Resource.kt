package com.example.apiauthentication.utils

sealed class Resource<T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Failure<T>(val errorMessage: String): Resource<T>()
    data class Loading<T>(val loader: Boolean): Resource<T>()
}