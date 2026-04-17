package app.peter.s611.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.peter.s611.databinding.FragmentSearchBinding
import app.peter.s611.application.di.module.view.ViewModelFactory

import app.peter.s611.application.Log
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: SearchViewModel
    private val args: SearchFragmentArgs by navArgs()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private fun subscribeUi(adapter: SearchAdapter) {
        Log.d(TAG, "subscribeUi()")
        viewModel.searchBookList.observe(viewLifecycleOwner) { bookList ->
            Log.d(TAG, "subscribeUi() viewModel.searchBookList [$bookList]")
            adapter.addAllData(bookList)
        }
    }

    private fun navigateToDetail(view: View, isbn: String) {
        val direction = SearchFragmentDirections.actionSearchFragmentToDetailFragment(isbn)
        view.findNavController().navigate(direction)
    }

    private fun hideSoftKeyboard() {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(binding.search.windowToken, 0)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            (recyclerView.layoutManager as LinearLayoutManager?)?.let {
                val position = it.findLastCompletelyVisibleItemPosition()
                recyclerView.adapter?.let { adapter ->
                    val remainCount = adapter.itemCount - position
                    if (remainCount < AUTO_LOAD_THRESHOLD) {
                        viewModel.currentSearchQuery.value?.let { query ->
                            if (query.isNotEmpty()) {
                                viewModel.searchBook(query)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView()")
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[SearchViewModel::class.java]
        viewModel.resetSearchState()
        viewModel.clearSearchResult()

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val adapter = SearchAdapter(requestManager) {
            Log.d(TAG, "onCreateView() item click [${it.isbn}]")
            navigateToDetail(binding.root, it.isbn)
        }
        binding.searchList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
            this.addOnScrollListener(scrollListener)
        }
        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideSoftKeyboard()
                query?.let {
                    viewModel.resetSearchState()
                    viewModel.clearSearchResult()
                    viewModel.setCurrentSearchQuery(query)
                    viewModel.addHistory(it)
                    viewModel.searchBook(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        subscribeUi(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchQuery = args.query
        Log.d(TAG, "onViewCreated() searchQuery[$searchQuery]")
        if (searchQuery.isNotEmpty()) {
            binding.search.apply {
                setQuery(searchQuery, true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView()")
        _binding = null
    }

    companion object {
        private const val AUTO_LOAD_THRESHOLD = 5
        private const val TAG = "SearchFragment"
    }
}