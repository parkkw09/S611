package app.peterkwp.s611.ui.search

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
import app.peterkwp.s611.databinding.FragmentSearchBinding
import app.peterkwp.s611.di.module.view.ViewModelFactory
import app.peterkwp.s611.ui.MainViewModel
import app.peterkwp.s611.util.Log
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MainViewModel
    private val args: SearchFragmentArgs by navArgs()

    private lateinit var binding: FragmentSearchBinding

    private var pageCount = 1
    private var loading = false
    private var complete = false

    private fun subscribeUi(adapter: SearchAdapter) {
        Log.d(TAG, "subscribeUi()")
        viewModel.searchBookList.observe(viewLifecycleOwner, { bookList ->
            Log.d(TAG, "subscribeUi() viewModel.searchBookList [$bookList]")
            when (pageCount == 1) {
                true -> adapter.addAllData(bookList)
                false -> adapter.addAllMore(bookList)
            }
        })
        viewModel.currentSearchQuery.observe(viewLifecycleOwner, { query ->
            Log.d(TAG, "subscribeUi() viewModel.currentSearchQuery [$query]")
            if (query.isEmpty()) {
                processClearResult()
            } else {
                processSearch(query)
            }
        })
    }

    private fun processSearch(query: String) {
        Log.d(TAG, "processSearch()")
        val adapter = binding.searchList.adapter as SearchAdapter
        viewModel.searchBook(query, pageCount.toString()) { page, total ->
            Log.d(TAG, "processSearch() current page[$page] totalCount[$total]")
            if (adapter.size() >= total && pageCount != 1) {
                loading = false
                complete = true
            } else {
                loading = false
                pageCount++
            }
        }
    }

    private fun processClearResult() {
        val adapter = binding.searchList.adapter as SearchAdapter
        adapter.clearData()
        viewModel.clearSearchResult()
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
                        if (!loading && !complete) {
                            loading = true
                            viewModel.currentSearchQuery.value?.let { query ->
                                processSearch(query)
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
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
                    pageCount = 1
                    loading = false
                    complete = false

                    viewModel.setCurrentSearchQuery(query)
                    viewModel.addHistory(it)
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
        pageCount = 1
        loading = false
        complete = false

        if (searchQuery.isNotEmpty()) {
            binding.search.apply {
                setQuery(searchQuery, true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView()")
        pageCount = 1
        loading = false
        complete = false
    }

    companion object {
        private const val AUTO_LOAD_THRESHOLD = 5
        private const val TAG = "SearchFragment"
    }
}