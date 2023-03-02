package com.jeredev.dogedex

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.jeredev.dogedex.api.ApiServiceInterceptor
import com.jeredev.dogedex.auth.LoginActivity
import com.jeredev.dogedex.databinding.ActivityMainBinding
import com.jeredev.dogedex.doglist.DogListActivity
import com.jeredev.dogedex.model.User
import com.jeredev.dogedex.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
            } else {
                Toast.makeText(this, "You need to accept camera permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            ApiServiceInterceptor.setSessionToken(user.token)
        }

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openListDogs()
        }

        requestCameraPermissions()
    }

    private fun openListDogs() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun requestCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startCamera()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                -> {

                    AlertDialog.Builder(this).setTitle("Necesitas permisos")
                        .setMessage("Necesita aceptar los permisos de la camara para que la aplicacion pueda utilizarla")
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }.setNegativeButton(android.R.string.cancel) { _, _ ->
                        }.show()

                }
                else -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
        } else {
            startCamera()
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //used to bind  the lifeCycle of cameras to the lifecycle owner
            val cameraProvider = cameraProviderFuture.get()
            //preview
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            //select back camera  as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            //bind use cases to camera
            cameraProvider.bindToLifecycle(this, cameraSelector, preview)
        }, ContextCompat.getMainExecutor(this))
    }
}