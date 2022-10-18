package com.sercanorhangazi.githubsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercanorhangazi.githubsearch.model.UserDetail
import com.sercanorhangazi.githubsearch.api.GithubApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsVM @Inject constructor(
    private val api: GithubApi
): ViewModel() {

    private var userDetailsResult = MutableLiveData<UserDetail>()
    var userDetails: LiveData<UserDetail> = userDetailsResult

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            val result = api.getUserDetails(username)
            userDetailsResult.value = result
        }
    }


}