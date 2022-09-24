package com.sercanorhangazi.mvvmpractise.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sercanorhangazi.mvvmpractise.R
import com.sercanorhangazi.mvvmpractise.databinding.UserSearchFragmentBinding
import com.sercanorhangazi.mvvmpractise.searchUser.viewModel.SearchUserViewModel

class UserSearchFragment() : Fragment() {

    private lateinit var binding: UserSearchFragmentBinding
    private lateinit var viewModel: SearchUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserSearchResult()
        viewModel.observeUserSearchLiveData()
    }

}