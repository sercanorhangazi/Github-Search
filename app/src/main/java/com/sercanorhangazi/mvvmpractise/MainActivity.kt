package com.sercanorhangazi.mvvmpractise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sercanorhangazi.mvvmpractise.databinding.ActivityMainBinding
import com.sercanorhangazi.mvvmpractise.searchUser.viewModel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}