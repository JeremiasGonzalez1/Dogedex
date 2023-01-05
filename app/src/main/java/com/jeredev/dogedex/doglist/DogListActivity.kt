package com.jeredev.dogedex.doglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeredev.dogedex.databinding.ActivityDogListBinding

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel : DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.rvDogs

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()

        dogListViewModel.dogList.observe(this){
            adapter.submitList(it)
        }
    }
}