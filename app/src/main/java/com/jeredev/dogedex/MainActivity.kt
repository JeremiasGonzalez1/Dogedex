package com.jeredev.dogedex

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.content.ContextCompat
import com.jeredev.dogedex.api.ApiServiceInterceptor
import com.jeredev.dogedex.auth.LoginActivity
import com.jeredev.dogedex.databinding.ActivityMainBinding
import com.jeredev.dogedex.doglist.DogListActivity
import com.jeredev.dogedex.model.User
import com.jeredev.dogedex.settings.SettingsActivity
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                setUpCamera()
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

        binding.takePhotoFab.setOnClickListener {
            if (isCameraReady) {
                takePhoto()
            }
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
                    setUpCamera()
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
            setUpCamera()
        }

    }

    private fun setUpCamera() {
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }


    private fun takePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error taking photo ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // insert your code here.
                    val photoUri = outputFileResults.savedUri
                    openWholeImageActivity(photoUri.toString())

                }
            })

    }

    private fun openWholeImageActivity(photoUri: String) {
        val intent = Intent(this, WholeImageActivity::class.java)
        intent.putExtra(WholeImageActivity.PHOTO_URI_KEY, photoUri)
        startActivity(intent)
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

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees

                imageProxy.close()
            }

            //bind use cases to camera
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun getOutputPhotoFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir
        else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized)
            cameraExecutor.shutdown()
    }
}