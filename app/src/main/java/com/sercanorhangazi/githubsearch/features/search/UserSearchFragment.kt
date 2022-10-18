package com.sercanorhangazi.githubsearch.features.search

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sercanorhangazi.githubsearch.R
import com.sercanorhangazi.githubsearch.databinding.UserSearchFragmentBinding
import com.sercanorhangazi.githubsearch.util.onQueryTextSubmit
import com.sercanorhangazi.githubsearch.util.showIfOrInvisible
import com.sercanorhangazi.githubsearch.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class UserSearchFragment: Fragment(R.layout.user_search_fragment), MenuProvider {

    private val viewModel: SearchUserViewModel by viewModels()

    private var currentBinding: UserSearchFragmentBinding? = null
    private val binding get() = currentBinding!!

    private lateinit var newsArticleAdapter: SearchUserPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentBinding = UserSearchFragmentBinding.bind(view)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        newsArticleAdapter = SearchUserPagingAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = newsArticleAdapter.withLoadStateFooter(
                    SearchUserLoadStateAdapter(retry = newsArticleAdapter::retry)
                )
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAnimator?.changeDuration = 0
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.searchResults.collectLatest { data ->
                    tvInstructions.isVisible = false
                    swipeRefreshLayout.isEnabled = true
                    newsArticleAdapter.submitData(data)
                }
            }

            swipeRefreshLayout.isEnabled = false

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                newsArticleAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.source.refresh }
                    .filter { it.source.refresh is LoadState.NotLoading }
                    .collect {
                        if (viewModel.pendingScrollToTopAfterNewQuery) {
                            recyclerView.scrollToPosition(0)
                            viewModel.pendingScrollToTopAfterNewQuery = false
                        }
                        if (viewModel.pendingScrollToTopAfterRefresh && it.mediator?.refresh is LoadState.NotLoading) {
                            recyclerView.scrollToPosition(0)
                            viewModel.pendingScrollToTopAfterRefresh = false
                        }
                    }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                newsArticleAdapter.loadStateFlow
                    .collect() { loadState ->
                        when (val refresh = loadState.mediator?.refresh) {
                            LoadState.Loading -> {
                                tvError.isVisible = false
                                btnRetry.isVisible = false
                                swipeRefreshLayout.isRefreshing = true
                                tvNoResults.isVisible = false
                                recyclerView.showIfOrInvisible {
                                    !viewModel.newQueryInProgress && newsArticleAdapter.itemCount > 0
                                }

                                viewModel.refreshInProgress = true
                                viewModel.pendingScrollToTopAfterRefresh = true
                            }
                            is LoadState.NotLoading -> {
                                tvError.isVisible = false
                                btnRetry.isVisible = false
                                swipeRefreshLayout.isRefreshing = false
                                recyclerView.isVisible = newsArticleAdapter.itemCount > 0

                                val noResults = newsArticleAdapter.itemCount < 1
                                        && loadState.append.endOfPaginationReached
                                        && loadState.source.append.endOfPaginationReached
                                tvNoResults.isVisible = noResults

                                viewModel.refreshInProgress = false
                                viewModel.newQueryInProgress = false
                            }
                            is LoadState.Error -> {
                                swipeRefreshLayout.isRefreshing = false
                                tvNoResults.isVisible = false
                                recyclerView.isVisible = newsArticleAdapter.itemCount > 0

                                val noCachedResults = newsArticleAdapter.itemCount < 1
                                        && loadState.source.append.endOfPaginationReached

                                tvError.isVisible = noCachedResults
                                btnRetry.isVisible = noCachedResults

                                val errorMessage = getString(R.string.could_not_load_search_results, refresh.error.localizedMessage)
                                tvError.text = errorMessage

                                if (viewModel.refreshInProgress) {
                                    showSnackBar(errorMessage)
                                }
                                viewModel.refreshInProgress = false
                                viewModel.newQueryInProgress = false
                                viewModel.pendingScrollToTopAfterRefresh = false
                            }
                            null -> {}
                        }
                    }
            }

            swipeRefreshLayout.setOnRefreshListener {
                newsArticleAdapter.refresh()
            }

            btnRetry.setOnClickListener {
                newsArticleAdapter.retry()
            }
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_user, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchView.onQueryTextSubmit { query ->
            viewModel.onSearchQuerySubmit(query)
            searchView.clearFocus()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.action_refresh -> {
                newsArticleAdapter.refresh()
                return true
            }
        }
        return false
    }

//    override fun onBottomNavigationFragmentReselected() {
//        binding.recyclerView.scrollToPosition(0)
    //TODO("onBottomNavigationFragmentReselected")
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        currentBinding = null
    }
}