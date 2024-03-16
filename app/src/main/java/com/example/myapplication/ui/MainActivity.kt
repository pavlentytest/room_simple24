package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.DictRepository
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dictViewModel: DictViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = DictViewModelFactory(DictRepository.Base(application))
        dictViewModel = ViewModelProvider(this, factory)[DictViewModel::class.java]

        with(binding) {
            myViewModel = dictViewModel
            lifecycleOwner = this@MainActivity
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        dictViewModel.observeWords(this) { words ->
            binding.recyclerView.adapter = RecyclerViewAdapter(words) { selectedItem ->
                dictViewModel.initUpdateAndDelete(selectedItem)
            }
        }

    }
}