package com.sercanorhangazi.githubsearch.features.userDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.sercanorhangazi.githubsearch.R
import com.sercanorhangazi.githubsearch.base.BaseFragment
import com.sercanorhangazi.githubsearch.databinding.FragmentUserDetailsBinding
import com.sercanorhangazi.githubsearch.data.UserDetail
import com.sercanorhangazi.githubsearch.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator3

@AndroidEntryPoint
class UserDetailsFragment: BaseFragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val userDetailsVM by viewModels<UserDetailsVM>()
    private val username: String? by lazy {
        arguments?.getString(getString(R.string.NAV_PARAM_USER_DETAILS))
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
        snackBar("This snackbar is from base fragment")
    }

    private fun observeUserDetails() {
        userDetailsVM.userDetails.observe(viewLifecycleOwner) { response ->
            binding.apply {
                progressBar.isVisible = response is Resource.Loading
                main.isVisible = response is Resource.Success
                tvError.isVisible = response is Resource.Error

                when (response) {
                    is Resource.Error -> {
                        tvError.text = response.error?.localizedMessage ?: getString(R.string.unknown_error_occurred)
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        setUserDetails(response.data!!)
                    }
                }
            }
        }
    }

    private fun setUserDetails(user: UserDetail) {
        binding.apply {
            //Glide.with(this@UserDetailsFragment).load(user.avatar_url).into(ivAvatar)
            val images = mutableListOf(user.avatarUrl, user.avatarUrl, user.avatarUrl).toList()
            val imageAdapter = UserImageViewPagerAdapter()
            imageAdapter.submitList(images)
            vpUserImage.adapter = imageAdapter
            circleIndicator.setViewPager(vpUserImage)

            tvName.text = user.name
            tvUsername.text = "@${user.username}"

            btnViewOnWeb.setOnClickListener {
                var uri = user.htmlUrl
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(i)
            }
        }
    }

}