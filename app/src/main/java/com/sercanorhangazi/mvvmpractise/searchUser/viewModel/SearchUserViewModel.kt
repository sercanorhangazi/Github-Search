package com.sercanorhangazi.mvvmpractise.searchUser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sercanorhangazi.mvvmpractise.retrofit.RetrofitInstance
import com.sercanorhangazi.mvvmpractise.searchUser.models.UserSearchResultModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel: ViewModel() {

    private var userSearchLiveData = MutableLiveData<UserSearchResultModel>()

    fun getUserSearchResult(query: String = "sercan", page: Int = 1) {
        RetrofitInstance.api.getUserSearchResults(query,page).enqueue(object:
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