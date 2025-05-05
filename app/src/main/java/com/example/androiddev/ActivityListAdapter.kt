package com.example.androiddev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddev.databinding.ListItemActivityBinding
import com.example.androiddev.databinding.ListItemDateHeaderBinding


class ActivityListAdapter(
    private val items: List<ActivityListItem>,
    private val onItemClick: (ActivityListItem.ActivityItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ActivityItemViewHolder(private val binding: ListItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActivityListItem.ActivityItem) {
            binding.tvActivityType.text = item.type
            binding.tvDistance.text = item.distance
            binding.tvDuration.text = item.duration
            binding.tvUsername.text = item.username
            binding.tvTimeAgo.text = item.timeAgo
        }
    }

    inner class DateHeaderViewHolder(private val binding: ListItemDateHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: ActivityListItem.DateHeader) {
            binding.tvDateHeader.text = header.date
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ActivityListItem.ActivityItem -> R.layout.list_item_activity
            is ActivityListItem.DateHeader -> R.layout.list_item_date_header
            else -> throw IllegalArgumentException("invalid view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.list_item_activity -> {
                val binding = ListItemActivityBinding.inflate(inflater, parent, false)
                ActivityItemViewHolder(binding)
            }

            R.layout.list_item_date_header -> {
                val binding = ListItemDateHeaderBinding.inflate(inflater, parent, false)
                DateHeaderViewHolder(binding)
            }

            else -> throw IllegalArgumentException("invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityListItem.ActivityItem -> {
                (holder as ActivityItemViewHolder).bind(item)
                holder.itemView.setOnClickListener { onItemClick(item) }
            }
            is ActivityListItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item)
            else -> throw IllegalArgumentException("invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size
}