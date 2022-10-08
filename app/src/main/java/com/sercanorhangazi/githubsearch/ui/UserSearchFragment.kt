package com.sercanorhangazi.githubsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sercanorhangazi.githubsearch.R
import com.sercanorhangazi.githubsearch.databinding.UserSearchFragmentBinding
import com.sercanorhangazi.githubsearch.viewModel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchFragment: Fragment() {

    private lateinit var binding: UserSearchFragmentBinding
    private val userSearchVM by viewModels<SearchUserViewModel>()
    private lateinit var userSearchAdapter: UserSearchCellAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupRecyclerView()
        observeUserSearch()
    }

    private fun setupRecyclerView() {
        userSearchAdapter = UserSearchCellAdapter(ArrayList()) {
            userSearchCellTapped(it)
        }
        binding.rvUser.adapter = userSearchAdapter
        binding.rvUser.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //val lm = recyclerView.layoutManager as LinearLayoutManager
                val maxOffset = recyclerView.computeVerticalScrollRange()-recyclerView.height
                val currentOffset = recyclerView.computeVerticalScrollOffset()
                if (currentOffset == maxOffset) {
                    userSearchVM.getNextPage()
                }
            }
        })

    }

    private fun userSearchCellTapped(username: String) {
        val bundle = Bundle()
        bundle.putString("login", username)
        findNavController().navigate(R.id.action_userSearch_to_userDetails, bundle)
    }

    private fun setupSearchView() {
        binding.svUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                p0?.let {
                    val query = p0.trim()
                    userSearchVM.getUserSearchResult(query)
                }
                binding.svUsername.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun observeUserSearch() {
        userSearchVM.userSearchResults.observe(viewLifecycleOwner
            ) { _searchRes ->
                _searchRes?.let { searchResult ->
                    userSearchAdapter.setItems(ArrayList(searchResult.items), searchResult.query ?: "")
                    println("Observing search result. Total: ${searchResult.total_count}")
                }
            }
    }

}