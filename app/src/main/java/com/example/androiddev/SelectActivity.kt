package com.example.androiddev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddev.databinding.ActivitySelectBinding
import com.example.androiddev.databinding.LayoutActivityStartedBinding

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding
    private var selectedActivity: ActivityTypeAdapter.ActivityType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityTypes = listOf(
            ActivityTypeAdapter.ActivityType("Велосипед", R.drawable.ic_welcome),
            ActivityTypeAdapter.ActivityType("Бег", R.drawable.ic_welcome),
            ActivityTypeAdapter.ActivityType("Шаг", R.drawable.ic_welcome)
        )

        val adapter = ActivityTypeAdapter(activityTypes) { selectedType ->
            selectedActivity = selectedType
        }

        binding.activitiesRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.activitiesRecyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.start.setOnClickListener {
            selectedActivity?.let { activity ->
                showActivityStartedView(activity.name)
            }
        }
    }

    private fun showActivityStartedView(activityName: String) {
        binding.container.removeAllViews()
        val startedBinding =
            LayoutActivityStartedBinding.inflate(layoutInflater, binding.container, true)

        startedBinding.tvActivityType.text = activityName
        startedBinding.tvDistance.text = "14 км"
        startedBinding.tvTime.text = "00:01:46"

        startedBinding.btnFinish.setOnClickListener {
            finish()
        }
    }
}