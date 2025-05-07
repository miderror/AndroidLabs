package com.example.androiddev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddev.databinding.FragmentMyActivityBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MyActivityFragment : Fragment() {

    private var _binding: FragmentMyActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val appDatabase = AppDatabase.getDatabase(requireContext())
        val activityRepository = ActivityRepository(appDatabase.activityDao())
        val activityViewModelFactory = ActivityViewModelFactory(activityRepository)
        activityViewModel =
            ViewModelProvider(this, activityViewModelFactory)[ActivityViewModel::class.java]

        activityViewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            val activityListItems = generateActivityListItems(activities)
            val adapter = ActivityListAdapter(activityListItems) { activity ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ActivityDetailsFragment.newInstance(activity))
                    .addToBackStack("activity_details")
                    .commit()
            }
            recyclerView.adapter = adapter
        }
    }

    private fun generateActivityListItems(activities: List<ActivityEntity>): List<ActivityListItem> {
        val result = mutableListOf<ActivityListItem>()
        var currentHeader: String? = null
        val headerFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        for (activity in activities) {
            val header = headerFormat.format(activity.startTime)
            if (header != currentHeader) {
                result.add(ActivityListItem.DateHeader(header))
                currentHeader = header
            }

            val formattedDistance = "%.2f км".format(calculateDistance(activity.coordinates))
            val formattedDuration = formatDuration(activity.startTime, activity.endTime)
            val timeAgo = formatTimeAgo(activity.endTime)

            result.add(
                ActivityListItem.ActivityItem(
                    distance = formattedDistance,
                    duration = formattedDuration,
                    type = activity.type.readableName(),
                    timeAgo = timeAgo,
                    startTime = timeFormat.format(activity.startTime),
                    endTime = timeFormat.format(activity.endTime),
                    username = null
                )
            )
        }

        return result
    }

    private fun calculateDistance(coordinates: List<ActivityEntity.Coordinate>): Double {
        if (coordinates.size < 2) return 0.0
        var totalDistance = 0.0
        for (i in 0 until coordinates.size - 1) {
            val c1 = coordinates[i]
            val c2 = coordinates[i + 1]
            val dLat = Math.toRadians(c2.latitude - c1.latitude)
            val dLon = Math.toRadians(c2.longitude - c1.longitude)
            val a = sin(dLat / 2) * sin(dLat / 2) +
                    cos(Math.toRadians(c1.latitude)) * cos(Math.toRadians(c2.latitude)) *
                    sin(dLon / 2) * sin(dLon / 2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            totalDistance += 6371 * c
        }
        return totalDistance
    }

    private fun formatDuration(start: Date, end: Date): String {
        val diff = end.time - start.time
        val days = (diff / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((diff / (1000 * 60 * 60)) % 24).toInt()
        val minutes = ((diff / (1000 * 60)) % 60).toInt()

        val parts = mutableListOf<String>()
        if (days > 0) parts.add(Pluralization.day(days))
        if (hours > 0) parts.add(Pluralization.hour(hours))
        if (minutes > 0) parts.add(Pluralization.minute(minutes))
        return parts.joinToString(" ")
    }

    private fun formatTimeAgo(date: Date): String {
        val now = Date()
        val diff = now.time - date.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 0 -> "${Pluralization.dayGenitive(days.toInt())} назад"
            hours > 0 -> "${Pluralization.hourGenitive(hours.toInt())} назад"
            minutes > 0 -> "${Pluralization.minuteGenitive(minutes.toInt())} назад"
            else -> "только что"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}