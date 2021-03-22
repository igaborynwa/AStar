package com.example.astar

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.astar.databinding.ActivityMainBinding
import com.example.astar.viewmodel.MyViewModel


class MainActivity : AppCompatActivity() {
    lateinit var prefs : SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE)

        myViewModel.path.observe(this, Observer{
                value ->binding.path=value
        })

        binding.btnLoad.setOnClickListener {
           myViewModel.readFromDB()
        }

        binding.btnSearch.setOnClickListener {
            myViewModel.calculatePath()
        }
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean("firstrun", true)) {
            myViewModel.readFromCSV(resources.openRawResource(R.raw.nodes), resources.openRawResource(R.raw.edges))
            prefs.edit().putBoolean("firstrun", false).apply()
        }
    }


}