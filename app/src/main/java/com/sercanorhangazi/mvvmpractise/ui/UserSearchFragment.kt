package com.sercanorhangazi.mvvmpractise.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sercanorhangazi.mvvmpractise.databinding.UserSearchFragmentBinding
import com.sercanorhangazi.mvvmpractise.model.User
import com.sercanorhangazi.mvvmpractise.model.UserSearchResultModel
import com.sercanorhangazi.mvvmpractise.viewModel.SearchUserViewModel
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

        userSearchAdapter = UserSearchCellAdapter(ArrayList<User>()) {
            println(it)
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
        userSearchVM.observeUserSearchLiveData()
            .observe(viewLifecycleOwner, object: Observer<UserSearchResultModel>{
                override fun onChanged(t: UserSearchResultModel?) {
                    t?.let {
                        userSearchAdapter.setItems(ArrayList(t.items), t.query ?: "")
                        println("Observing search result. Total: ${it.total_count}")
                    }
                }
            })
    }

}