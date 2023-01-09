package com.jeredev.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.databinding.ActivityDogListBinding
import com.jeredev.dogedex.dogdetail.DogDetailActivity
import com.jeredev.dogedex.dogdetail.DogDetailActivity.Companion.DOG_KEY

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel= binding.loadingWheel
        val recyclerView = binding.rvDogs

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()
        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        dogListViewModel.dogList.observe(this) {
            adapter.submitList(it)
        }

        dogListViewModel.status.observe(this) { status ->
            when (status) {
                ApiResponseStatus.LOADING -> {loadingWheel.visibility = View.VISIBLE
                }

                ApiResponseStatus.SUCCESS -> {loadingWheel.visibility = View.GONE
                }
                ApiResponseStatus.ERROR -> {
                    Toast.makeText(this, "Error al descargar datos", Toast.LENGTH_SHORT).show()
                    loadingWheel.visibility = View.GONE
                }
                else -> {
                    Toast.makeText(this, "Estatus desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}