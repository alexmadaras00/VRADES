package com.example.vrades.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vrades.databinding.ActivityTutorialBinding


class TutorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}