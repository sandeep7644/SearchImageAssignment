package com.example.assignment.models

sealed class Resource<T>(val data: T?, val message: String = "") {
    class Success<T>(data: T) : Resource<T>(data)
    class Failed<T>(message: String) : Resource<T>(null, message)
    class Loading<T> : Resource<T>(null, "loading")
}