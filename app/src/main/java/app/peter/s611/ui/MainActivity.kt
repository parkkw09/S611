package app.peter.s611.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import app.peter.s611.databinding.ActivityMainBinding
import app.peter.s611.util.Log
import com.google.android.gms.appset.AppSet
import com.google.android.gms.appset.AppSetIdInfo
import com.google.android.gms.tasks.Task
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = AppSet.getClient(applicationContext)
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

    companion object {
        private const val TAG = "MainActivity"
    }
}