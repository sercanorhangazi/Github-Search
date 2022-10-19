package com.sercanorhangazi.githubsearch.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.sercanorhangazi.githubsearch.data.UserDetail
import com.sercanorhangazi.githubsearch.api.GithubApi
import com.sercanorhangazi.githubsearch.data.SearchRepository
import com.sercanorhangazi.githubsearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsVM @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {

    private var userDetailsResult = MutableLiveData<Resource<UserDetail>>()
    var userDetails: LiveData<Resource<UserDetail>> = userDetailsResult

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            repository.getUserDetails(username).collect { response ->
                Log.d("DEBUG", "UserDetailsVM: ${response.data?.username}")
                userDetailsResult.value = response
            }
        }
    }
}