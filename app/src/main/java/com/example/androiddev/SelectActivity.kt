package com.example.androiddev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddev.databinding.ActivitySelectBinding
import com.example.androiddev.databinding.LayoutActivityStartedBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Random

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding
    private var selectedActivity: ActivityType? = null
    private lateinit var activityRepository: ActivityRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appDatabase = AppDatabase.getDatabase(this)
        activityRepository = ActivityRepository(appDatabase.activityDao())

        val activityTypes = listOf(
            ActivityType.CYCLING,
            ActivityType.RUNNING,
            ActivityType.WALKING
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
            selectedActivity?.let { activityType ->
                generateAndSaveActivity(activityType)
                showActivityStartedView(activityType)
            }
        }
    }

    private fun generateAndSaveActivity(type: ActivityType) {
        val startTime = Date()
        val randomDuration = (1..3).random() * 60 * 60 * 1000L
        val endTime = Date(startTime.time + randomDuration)
        val coordinates = generateRandomCoordinates()

        val activity = ActivityEntity(
            type = type,
            startTime = startTime,
            endTime = endTime,
            coordinates = coordinates
        )

        lifecycleScope.launch {
            activityRepository.insert(activity)
        }
    }

    private fun generateRandomCoordinates(count: Int = 10): List<ActivityEntity.Coordinate> {
        val result = mutableListOf<ActivityEntity.Coordinate>()
        val baseLatitude = 55.7522
        val baseLongitude = 37.6156
        for (i in 0 until count) {
            val randomLat = baseLatitude + (0..100).random() / 1000.0
            val randomLon = baseLongitude + (0..100).random() / 1000.0
            result.add(ActivityEntity.Coordinate(randomLat, randomLon))
        }
        return result
    }

    private fun showActivityStartedView(activityType: ActivityType) {
        binding.container.removeAllViews()
        val startedBinding =
            LayoutActivityStartedBinding.inflate(layoutInflater, binding.container, true)

        startedBinding.tvActivityType.text = activityType.readableName()
        startedBinding.tvDistance.text = "14 км"
        startedBinding.tvTime.text = "00:01:46"

        startedBinding.btnFinish.setOnClickListener {
            finish()
        }
    }
}