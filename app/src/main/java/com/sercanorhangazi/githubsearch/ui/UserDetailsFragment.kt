package com.sercanorhangazi.githubsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.sercanorhangazi.githubsearch.databinding.FragmentUserDetailsBinding
import com.sercanorhangazi.githubsearch.model.UserDetail
import com.sercanorhangazi.githubsearch.viewModel.UserDetailsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment: Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val userDetailsVM by viewModels<UserDetailsVM>()
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { _bundle ->
            val login = _bundle.getString("login")
            login?.let { _login ->
                username = _login
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username?.let { username ->
            userDetailsVM.getUserDetails(username)
            observeUserDetails()
        }
    }

    private fun observeUserDetails() {
        userDetailsVM.observeUserDetailsLiveData().observe(viewLifecycleOwner) { _user ->
            _user?.let { user ->
                setUserDetails(user)
            }
        }
    }

    private fun setUserDetails(user: UserDetail) {
        binding.apply {
            Glide.with(this@UserDetailsFragment).load(user.avatar_url).into(ivAvatar)
            tvName.text = user.name
            tvUsername.text = "@${user.login}"

            btnViewOnWeb.setOnClickListener {
                var uri = user.html_url
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(i)
            }
        }
    }

}