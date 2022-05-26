package app.peter.s611.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import app.peter.s611.databinding.FragmentBookmarkBinding
import app.peter.s611.di.module.view.ViewModelFactory
import app.peter.s611.ui.MainViewModel
import app.peter.s611.ui.main.ViewPagerFragmentDirections
import app.peter.s611.util.Log
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookmarkFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentBookmarkBinding

    private fun subscribeUi(adapter : BookmarkAdapter) {
        viewModel.bookmark.observe(viewLifecycleOwner) { bookList ->
            Log.d(TAG, "subscribeUi() viewModel.bookmark [$bookList]")
            adapter.addAllData(bookList)
        }
    }

    private fun launchUi() {
        Log.d(TAG, "launchUi()")
        viewModel.getBookmark()
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
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val adapter = BookmarkAdapter(requestManager) {
            Log.d(TAG, "onCreateView() item click [${it.isbn}]")
            navigateToDetail(binding.root, it.isbn)
        }
        binding.bookmarkList.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        ItemTouchHelper(ItemMoveCallback(adapter)).apply {
            attachToRecyclerView(binding.bookmarkList)
        }
        binding.ascending.setOnClickListener { adapter.sortList() }
        binding.descending.setOnClickListener { adapter.reverseSortList() }
        subscribeUi(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")
        launchUi()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView()")
        val adapter = binding.bookmarkList.adapter as BookmarkAdapter
        viewModel.updateBookmark(adapter.getList())
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "BookmarkFragment"
    }
}