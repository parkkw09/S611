package app.peter.s611.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import app.peter.s611.R
import app.peter.s611.databinding.FragmentViewPagerBinding
import app.peter.s611.di.module.view.ViewModelFactory
import app.peter.s611.ui.MainViewModel
import app.peter.s611.ui.data.TabType
import app.peter.s611.util.Log
import com.google.android.gms.appset.AppSetIdInfo
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.model.ReviewErrorCode
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewPagerFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

    private fun testReviewManager() {
        Log.d(TAG, "testReviewManager()")
        viewModel.reviewManager?.let { manager ->
            val request: Task<ReviewInfo> = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // We can get the ReviewInfo object
                    val reviewInfo: ReviewInfo = task.result
                    Log.d(TAG, "OnComplete() reviewInfo = " + reviewInfo.describeContents())
                    activity?.let { activity ->
                        val flow: Task<Void> = manager.launchReviewFlow(activity, reviewInfo)
                        flow.addOnCompleteListener { launchTask ->
                            // The flow has finished. The API does not indicate whether the user
                            // reviewed or not, or even whether the review dialog was shown. Thus, no
                            // matter the result, we continue our app flow.
                            Log.d(TAG, "OnComplete() ${launchTask.isComplete}")
                        }
                        flow.addOnFailureListener { e -> Log.d(TAG, "OnFailure() [${e.localizedMessage}]") }
                        flow.addOnCanceledListener { Log.d(TAG, "OnCancel()") }
                    }
                } else {
                    // There was some problem, log or handle the error code.
                    @ReviewErrorCode val reviewErrorCode: Int =
                        (task.exception as ReviewException).errorCode
                    Log.d(TAG, "Successful false =$reviewErrorCode")
                }
            }
        }
    }

    private fun testAppSetID() {
        Log.d(TAG, "testAppSetID()")
        viewModel.client?.let { client ->
            val task: Task<AppSetIdInfo> = client.appSetIdInfo

            task.addOnSuccessListener {
                // Determine current scope of app set ID.
                val scope: Int = it.scope

                // Read app set ID value, which uses version 4 of the
                // universally unique identifier (UUID) format.
                val id: String = it.id

                Log.d(TAG, "onCreate() App set ID scope[$scope]")
                Log.d(TAG, "onCreate() App set ID id[$id]")
                Log.d(TAG, "onCreate() App set ID Thread id[${Thread.currentThread()}]")

                activity?.run {
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("App set ID scope[$scope]")
                        .setMessage("id[$id]")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                    dialog.show()
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
        activity?.run {
            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        } ?: run {
            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        }
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")
        binding.apply {
            viewPager.adapter = BookPagerAdapter(this@ViewPagerFragment)

            // Set the icon and text for each tab
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.setIcon(getTabIcon(position))
                tab.text = getTabTitle(position)
            }.attach()

            (activity as AppCompatActivity).setSupportActionBar(toolbar)

            appName.setOnClickListener { testAppSetID() }
            review.setOnClickListener { testReviewManager() }
            search.setOnClickListener {
                val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToSearchFragment("")
                viewModel.setCurrentSearchQuery("")
                it.findNavController().navigate(direction)
            }
        }
    }

    companion object {
        private const val TAG = "ViewPagerFragment"
    }
}