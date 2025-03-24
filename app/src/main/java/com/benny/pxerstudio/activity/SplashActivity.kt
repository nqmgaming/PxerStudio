package com.benny.pxerstudio.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.benny.pxerstudio.R
import com.benny.pxerstudio.databinding.ActivitySplashBinding
import com.benny.pxerstudio.util.displayToast

class SplashActivity : AppCompatActivity() {

    private var handler: Handler? = null
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashImageView.animate()
            .alpha(1f)
            .scaleY(1.1f)
            .scaleX(1.1f)
            .setDuration(2000L)
            .interpolator = AccelerateDecelerateInterpolator()

        binding.splashTextView.animate()
            .alpha(1f)
            .scaleY(1.1f)
            .scaleX(1.1f)
            .setDuration(2000L)
            .interpolator = AccelerateDecelerateInterpolator()

        handler = Handler()
        checkPermissionsAndProceed()
    }

    private fun checkPermissionsAndProceed() {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            navigateToDrawingActivity()
        } else {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 0x456)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0x456) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                navigateToDrawingActivity()
            } else {
                displayToast(R.string.storage_permission_denied)
                handler!!.postDelayed({ recreate() }, 1000)
            }
        }
    }

    private fun navigateToDrawingActivity() {
        handler!!.postDelayed({
            startActivity(Intent(this@SplashActivity, DrawingActivity::class.java))
            finish()
        }, 2000L)
    }
}
