package com.sercanorhangazi.githubsearch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sercanorhangazi.githubsearch.R
import com.sercanorhangazi.githubsearch.databinding.UserSearchFragmentBinding
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import com.sercanorhangazi.githubsearch.util.Resource
import com.sercanorhangazi.githubsearch.viewModel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class UserSearchFragment : Fragment() {

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
    }

    private fun setupRecyclerView() {
        userSearchAdapter = UserSearchCellAdapter(ArrayList()) {
            userSearchCellTapped(it)
        }
        binding.rvUser.adapter = userSearchAdapter
        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //val lm = recyclerView.layoutManager as LinearLayoutManager
                val maxOffset = recyclerView.computeVerticalScrollRange() - recyclerView.height
                val currentOffset = recyclerView.computeVerticalScrollOffset()
                if (currentOffset == maxOffset) {
                    userSearchVM.getNextPage()
                    observeUserSearch()
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
                    val searchFlow = userSearchVM.getUserSearchResult(query)
                }
                binding.svUsername.clearFocus()
                observeUserSearch()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun observeUserSearch() {

        Log.d("DEBUG", "observeUserSearch: !!!")
        userSearchVM.userSearchResults?.observe(viewLifecycleOwner) { _res ->
            _res.data?.let { result ->
                Log.d("DEBUG", "observeUserSearch: data exists : ${result.total_count} fetched ${result.items.size} first one is ${result.items.first().login}")

                userSearchAdapter.setItems(ArrayList(result.items), result.query).also {
                    userSearchVM.userSearchResults?.removeObservers(viewLifecycleOwner)
                }
            }
            _res.error?.let {
                Log.d("DEBUG", "observeUserSearch: error exists : ${it.localizedMessage}")
                it.printStackTrace()
            }

            binding.apply {
                pbSearch.isVisible = _res is Resource.Loading && _res.data?.items.isNullOrEmpty()
                tvErrorMessage.isVisible = _res is Resource.Error && _res.data?.items.isNullOrEmpty()
                tvErrorMessage.text = _res.error?.localizedMessage
            }
        }

    }

}