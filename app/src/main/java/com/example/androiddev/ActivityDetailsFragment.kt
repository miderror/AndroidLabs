package com.example.androiddev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androiddev.databinding.FragmentActivityDetailsBinding

class ActivityDetailsFragment : Fragment() {
    private var _binding: FragmentActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        binding.tbActivityType.title = args?.getString("type")
        binding.tvDistance.text = args?.getString("distance")
        binding.tvDuration.text = args?.getString("duration")
        binding.tvTimeAgo.text = args?.getString("timeAgo")
        binding.tvFinish.text = args?.getString("endTime")
        binding.tvStart.text = args?.getString("startTime")

        val username = args?.getString("username")
        if (!username.isNullOrEmpty()) {
            binding.tvUsername.text = username
            binding.tvUsername.visibility = View.VISIBLE
        } else {
            binding.tvUsername.visibility = View.GONE
        }

        binding.tbActivityType.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(activity: ActivityListItem.ActivityItem) = ActivityDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("type", activity.type)
                putString("distance", activity.distance)
                putString("duration", activity.duration)
                putString("timeAgo", activity.timeAgo)
                putString("startTime", activity.startTime)
                putString("endTime", activity.endTime)
                putString("username", activity.username)
            }
        }
    }
}