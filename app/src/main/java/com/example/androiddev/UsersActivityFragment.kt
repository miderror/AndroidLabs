package com.example.androiddev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddev.databinding.FragmentUsersActivityBinding

class UsersActivityFragment : Fragment() {

    private var _binding: FragmentUsersActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersActivityBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val sampleData = listOf(
            ActivityListItem.DateHeader("Вчера"),
            ActivityListItem.ActivityItem(
                "14.32 км",
                "2 часа 46 минут",
                "Серфинг",
                "14 часов назад",
                "14:49",
                "16:31",
                "@van_darkholme"
            ),
            ActivityListItem.ActivityItem(
                "228 м",
                "14 часов 48 минут",
                "Качели",
                "14 часов назад",
                "14:49",
                "16:31",
                "@techniquepasha"
            ),
            ActivityListItem.ActivityItem(
                "10 км",
                "1 час 10 минут",
                "Езда на кадилак",
                "14 часов назад",
                "14:49",
                "16:31",
                "@morgen_shtern"
            ),
        )

        val adapter = ActivityListAdapter(sampleData) { activity ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ActivityDetailsFragment.newInstance(activity))
                .addToBackStack("activity_details")
                .commit()
        }
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}