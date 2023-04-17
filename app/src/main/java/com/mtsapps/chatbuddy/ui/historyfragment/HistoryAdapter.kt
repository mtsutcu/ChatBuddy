package com.mtsapps.chatbuddy.ui.historyfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.chatbuddy.databinding.HistoryItemBinding
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.ui.homefragment.HomeFragmentDirections

class HistoryAdapter(var vievModel: HistoryViewModel) :
    androidx.recyclerview.widget.ListAdapter<Chat, HistoryAdapter.ItemsViewHolder>(ItemsUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding =
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemsViewHolder(binding, vievModel)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }


    class ItemsViewHolder(
        private val binding: HistoryItemBinding,
        private val vievModel: HistoryViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chat, position: Int) {

            binding.chatTitleText.text = item.title
            binding.root.setOnClickListener {
                val trans = HistoryFragmentDirections.Companion.toHistoryDetail(item)
                Navigation.findNavController(it).navigate(trans)
            }


        }
    }

    class ItemsUtils : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.title == newItem.title
                    && oldItem.messages == newItem.messages

        }

    }
}