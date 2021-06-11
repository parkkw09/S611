package app.peterkwp.s611.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import app.peterkwp.s611.R
import app.peterkwp.s611.databinding.FragmentViewPagerBinding
import app.peterkwp.s611.di.module.view.ViewModelFactory
import app.peterkwp.s611.ui.MainViewModel
import app.peterkwp.s611.ui.data.TabType
import app.peterkwp.s611.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewPagerFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView()")
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        val search = binding.search

        viewPager.adapter = BookPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        search.setOnClickListener {
            val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToSearchFragment("")
            viewModel.setCurrentSearchQuery("")
            it.findNavController().navigate(direction)
        }

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            TabType.S611_NEW -> R.drawable.book
            TabType.S611_BOOKMARK -> R.drawable.bookmark
            TabType.S611_HISTORY -> R.drawable.history
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            TabType.S611_NEW -> getString(R.string.new_book)
            TabType.S611_BOOKMARK -> getString(R.string.bookmark)
            TabType.S611_HISTORY -> getString(R.string.history)
            else -> null
        }
    }

    companion object {
        private const val TAG = "ViewPagerFragment"
    }
}