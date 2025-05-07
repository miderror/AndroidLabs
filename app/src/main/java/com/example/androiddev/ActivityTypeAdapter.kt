package com.example.androiddev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddev.databinding.ItemActivityTypeBinding

class ActivityTypeAdapter(
    private val items: List<ActivityType>,
    private val onItemClick: (ActivityType) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder>() {

    private var selectedPosition = -1

    inner class ViewHolder(private val binding: ItemActivityTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActivityType, position: Int) {
            binding.tvActivityName.text = item.name()
            binding.ivActivityIcon.setImageResource(R.drawable.ic_welcome)
            binding.root.background = ContextCompat.getDrawable(
                binding.root.context,
                if (position == selectedPosition) R.drawable.card_selected else R.drawable.card_unselected
            )
            binding.root.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = position

                if (previousSelected != -1) notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)

                onItemClick(item)
            }
        }

        private fun ActivityType.name(): String {
            return when (this) {
                ActivityType.CYCLING -> "Велосипед"
                ActivityType.RUNNING -> "Бег"
                ActivityType.WALKING -> "Шаг"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActivityTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position], position)
    }

    override fun getItemCount() = items.size
}