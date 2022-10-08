package com.sercanorhangazi.githubsearch.util

sealed class Resource() {
    class Success<T>(val data: T) : Resource()
    class Error(val message: String) : Resource()
    object Loading : Resource()
    object Empty : Resource()
}