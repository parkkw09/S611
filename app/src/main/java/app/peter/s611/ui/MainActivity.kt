package app.peter.s611.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.peter.s611.R
import app.peter.s611.databinding.ActivityMainBinding
import app.peter.s611.di.module.view.ViewModelFactory
import app.peter.s611.util.Log
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MainViewModel

    private fun initializeApplication() {
        viewModel.initClient(applicationContext, resources.getString(R.string.app_name))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeApplication()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}