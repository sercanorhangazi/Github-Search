package com.sercanorhangazi.mvvmpractise.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sercanorhangazi.mvvmpractise.retrofit.GithubApi
import com.sercanorhangazi.mvvmpractise.model.UserSearchResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val api: GithubApi
): ViewModel() {

    private var userSearchLiveData = MutableLiveData<UserSearchResultModel>()

    fun getUserSearchResult(query: String = "sercan", page: Int = 1) {
        api.getUserSearchResults(query,page).enqueue(object:
            Callback<UserSearchResultModel> {
            override fun onResponse(
                call: Call<UserSearchResultModel>,
                response: Response<UserSearchResultModel>
            ) {
                Log.d("Request url", call.request().url().toString())
                response.body()?.let { result ->
                    userSearchLiveData.value = result
                }
            }

            override fun onFailure(call: Call<UserSearchResultModel>, t: Throwable) {
                println("Couldn't fetch user search result : ${t.message.toString()}")
            }
        })
    }

    fun observeUserSearchLiveData(): LiveData<UserSearchResultModel> {
        return userSearchLiveData
    }
}