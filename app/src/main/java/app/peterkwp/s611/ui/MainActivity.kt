package app.peterkwp.s611.ui

import android.os.Bundle
import app.peterkwp.s611.databinding.ActivityMainBinding
import app.peterkwp.s611.util.Log
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}