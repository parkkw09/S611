package app.peter.s611.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import app.peter.s611.databinding.FragmentBookmarkBinding
import app.peter.s611.application.di.module.view.ViewModelFactory

import app.peter.s611.presentation.main.ViewPagerFragmentDirections
import app.peter.s611.application.Log
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookmarkFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: BookmarkViewModel

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

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
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[BookmarkViewModel::class.java]
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
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
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "BookmarkFragment"
    }
}