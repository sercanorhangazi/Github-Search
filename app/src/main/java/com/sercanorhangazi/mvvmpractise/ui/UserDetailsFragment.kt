package com.sercanorhangazi.mvvmpractise.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sercanorhangazi.mvvmpractise.R
import com.sercanorhangazi.mvvmpractise.databinding.FragmentUserDetailsBinding
import com.sercanorhangazi.mvvmpractise.model.User
import com.sercanorhangazi.mvvmpractise.viewModel.UserDetailsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment() : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val userDetailsVM by viewModels<UserDetailsVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDetailsVM.getUserDetails("sercan")
        observeUserDetails()
    }

    private fun observeUserDetails() {
        userDetailsVM.observeUserDetailsLiveData().observe(viewLifecycleOwner, object: Observer<User> {
            override fun onChanged(t: User?) {
                t?.let {
                    println("Observing user details. ${t.avatar_url}")
                }
            }

        })
    }

}