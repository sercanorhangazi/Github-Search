package com.sercanorhangazi.mvvmpractise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sercanorhangazi.mvvmpractise.retrofit.RetrofitInstance
import com.sercanorhangazi.mvvmpractise.searchUser.models.UserSearchResultModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitInstance.api.getUserSearchResults("sercan",1).enqueue(object: Callback<UserSearchResultModel>{
            override fun onResponse(
                call: Call<UserSearchResultModel>,
                response: Response<UserSearchResultModel>
            ) {

                println("Request url : ${call.request().url().toString()}")
                response.body()?.let {
                    println("Total count is : ${it.total_count} and there are ${it.items.size} users")
                }
            }

            override fun onFailure(call: Call<UserSearchResultModel>, t: Throwable) {
                println("ERROR !!! : ${t.message.toString()}")
            }
        })
    }
}