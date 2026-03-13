package app.peter.s611.presentation.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.peter.s611.databinding.FragmentNewBookBinding
import app.peter.s611.application.di.module.view.ViewModelFactory
import app.peter.s611.presentation.MainViewModel
import app.peter.s611.presentation.main.ViewPagerFragmentDirections
import app.peter.s611.application.Log
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewBookFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MainViewModel

    private fun subscribeUi(adapter : NewBookAdapter) {
        viewModel.bookList.observe(viewLifecycleOwner) { bookList ->
            Log.d(TAG, "subscribeUi() viewModel.bookList [$bookList]")
            adapter.submitList(bookList)
        }
    }

    private fun launchUi() {
        Log.d(TAG, "launchUi()")
        viewModel.getNewBook(isRefresh = true)
    }

    private fun navigateToDetail(view: View, isbn: String) {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToDetailFragment(isbn)
        view.findNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView()")
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val binding = FragmentNewBookBinding.inflate(inflater, container, false)
        val adapter = NewBookAdapter(requestManager) {
            Log.d(TAG, "onCreateView() item click [${it.isbn}]")
            navigateToDetail(binding.root, it.isbn)
        }
        binding.bookList.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
            
            addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!viewModel.isNewBookLastPage) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                            viewModel.getNewBook()
                        }
                    }
                }
            })
        }
        subscribeUi(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")
        launchUi()
    }

    companion object {
        private const val TAG = "NewBookFragment"
    }
}