package app.peter.s611.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.peter.s611.presentation.book.NewBookFragment
import app.peter.s611.presentation.bookmark.BookmarkFragment
import app.peter.s611.presentation.data.TabType
import app.peter.s611.presentation.history.HistoryFragment

class BookPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TabType.S611_NEW to { NewBookFragment() },
        TabType.S611_BOOKMARK to { BookmarkFragment() },
        TabType.S611_HISTORY to { HistoryFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}