package com.sercanorhangazi.mvvmpractise.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sercanorhangazi.mvvmpractise.databinding.UserSearchFragmentBinding
import com.sercanorhangazi.mvvmpractise.model.UserSearchResultModel
import com.sercanorhangazi.mvvmpractise.viewModel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchFragment() : Fragment() {

    private lateinit var binding: UserSearchFragmentBinding
    private val userSearchVM by viewModels<SearchUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        userSearchVM.getUserSearchResult()
        observeUserSearch()

        binding.tvDemo.setOnClickListener {
            userSearchVM.getUserSearchResult(page = 2)
        }
    }

    private fun observeUserSearch() {
        userSearchVM.observeUserSearchLiveData()
            .observe(viewLifecycleOwner, object: Observer<UserSearchResultModel>{
                override fun onChanged(t: UserSearchResultModel?) {
                    t?.let {
                        println("Observing search result. Total: ${t.total_count} ${t.items[0].login}")
                    }
                }

            })
    }

}