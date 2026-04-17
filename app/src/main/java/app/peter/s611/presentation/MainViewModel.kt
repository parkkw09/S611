package app.peter.s611.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import app.peter.s611.application.Utils
import com.google.android.gms.appset.AppSet
import com.google.android.gms.appset.AppSetIdClient
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    var appName: String? = null
    var reviewManager: ReviewManager? = null
    var client: AppSetIdClient? = null

    fun initClient(context: Context, appName: String) {
        client = AppSet.getClient(context)
        reviewManager = ReviewManagerFactory.create(context)
        this.appName = Utils.convertAppName(context, appName)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}