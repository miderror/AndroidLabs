package com.example.androiddev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddev.databinding.FragmentMyActivityBinding

class MyActivityFragment : Fragment() {

    private var _binding: FragmentMyActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyActivityBinding.inflate(inflater, container, false)

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
                ""
            ),
            ActivityListItem.DateHeader("Май 2022 года"),
            ActivityListItem.ActivityItem(
                "1 000 м",
                "60 минут",
                "Велосипед",
                "29.05.2022",
                "14:49",
                "16:31",
                ""
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