package app.peter.s611.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.peter.s611.databinding.FragmentNewBookBinding
import app.peter.s611.di.module.view.ViewModelFactory
import app.peter.s611.ui.MainViewModel
import app.peter.s611.ui.main.ViewPagerFragmentDirections
import app.peter.s611.util.Log
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
        viewModel.bookList.observe(viewLifecycleOwner, { bookList ->
            Log.d(TAG, "subscribeUi() viewModel.bookList [$bookList]")
            adapter.submitList(bookList)
        })
    }

    private fun launchUi() {
        Log.d(TAG, "launchUi()")
        viewModel.getNewBook()
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