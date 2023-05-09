package com.mtsapps.chatbuddy.ui.historydetail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.databinding.RecieveMessageItemBinding
import com.mtsapps.chatbuddy.databinding.SendMessageItemBinding
import com.mtsapps.chatbuddy.models.CustomMessage

class HistoryDetailAdapter(private val context: Context) :
    ListAdapter<CustomMessage, RecyclerView.ViewHolder>(MyDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).role) {
            "user" -> VIEW_USER
            "assistant" -> VIEW_BOT
            "loading" -> VIEW_LOADING
            else -> throw java.lang.IllegalArgumentException("")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_USER -> {

                val binding =
                    SendMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeOneViewHolder(binding)
            }
            VIEW_BOT -> {
                val binding =
                    RecieveMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TypeTwoViewHolder(binding, context)
            }
      
            else -> throw java.lang.IllegalArgumentException("")

        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TypeOneViewHolder -> {
                val item = getItem(position)
                holder.bind(item)
            }
            is TypeTwoViewHolder -> {
                val item = getItem(position)
                holder.bind(item, position)
            }

        }
    }

    class TypeOneViewHolder(private val binding: SendMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomMessage) {

            binding.sendMessageText.text = item.content

            Log.e("adapter-typeONeHolder", item.content)


        }
    }

    class TypeTwoViewHolder(
        private val binding: RecieveMessageItemBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomMessage, position: Int) {

            binding.recMessageText.text = item.content

            binding.constraintLayout.setOnLongClickListener {
                copyToClipboard(context, item.content)
                return@setOnLongClickListener true
            }
            Log.e("adapter-typeTwoHolder", item.content)


        }

        fun copyToClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val VIEW_USER = 0
        private const val VIEW_BOT = 1
        private const val VIEW_LOADING = 2
    }

    class MyDiffCallback : DiffUtil.ItemCallback<CustomMessage>() {
        override fun areItemsTheSame(oldItem: CustomMessage, newItem: CustomMessage): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(oldItem: CustomMessage, newItem: CustomMessage): Boolean {
            return oldItem.content == newItem.content && oldItem.role == newItem.role
        }
    }

}

