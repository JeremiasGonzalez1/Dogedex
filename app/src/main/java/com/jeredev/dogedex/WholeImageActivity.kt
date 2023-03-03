package com.jeredev.dogedex

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.jeredev.dogedex.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWholeImageBinding

    companion object {
        const val PHOTO_URI_KEY = "photo_uri_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photoUri = intent.extras?.getString(PHOTO_URI_KEY)
        val uri = Uri.parse(photoUri)

        if (uri.path == null) {
            Toast.makeText(this, "Error to showing image, no photo uri", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        binding.wholeImage.load(File(uri.path))

    }
}