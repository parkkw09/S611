package app.peter.s611.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.peter.s611.databinding.FragmentHistoryBinding
import app.peter.s611.application.di.module.view.ViewModelFactory
import app.peter.s611.presentation.MainViewModel
import app.peter.s611.presentation.main.ViewPagerFragmentDirections
import app.peter.s611.application.Log
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HistoryFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: MainViewModel

    private fun subscribeUi(adapter : HistoryAdapter) {
        viewModel.history.observe(viewLifecycleOwner) { historyList ->
            Log.d(TAG, "subscribeUi() viewModel.history [$historyList]")
            adapter.submitList(historyList)
        }
    }

    private fun launchUi() {
        Log.d(TAG, "launchUi()")
        viewModel.getHistory()
    }

    private fun navigateToDetail(view: View, query: String) {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToSearchFragment(query)
        view.findNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView()")
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val adapter = HistoryAdapter() {
            Log.d(TAG, "onCreateView() item click [${it}]")
            navigateToDetail(binding.root, it)
        }
        binding.historyList.apply {
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
        private const val TAG = "HistoryFragment"
    }
}