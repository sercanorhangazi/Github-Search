package com.sercanorhangazi.mvvmpractise.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sercanorhangazi.mvvmpractise.model.User
import com.sercanorhangazi.mvvmpractise.model.UserDetail
import com.sercanorhangazi.mvvmpractise.retrofit.GithubApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserDetailsVM @Inject constructor(
    private val api: GithubApi
): ViewModel() {

    private var userDetails = MutableLiveData<UserDetail>()

    fun getUserDetails(username: String) {
        api.getUserDetails(username).enqueue(object: Callback<UserDetail>{
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                Log.d("Request url", call.request().url().toString())
                response.body()?.let { userDetail ->
                    userDetails.value = userDetail
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.d("DEBUG", "Couldn't fetch user details : ${t.message.toString()}")
            }

        })
    }

    fun observeUserDetailsLiveData(): LiveData<UserDetail> {
        return userDetails
    }

}