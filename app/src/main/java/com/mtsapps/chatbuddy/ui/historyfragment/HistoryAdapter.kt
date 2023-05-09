package com.mtsapps.chatbuddy.ui.historyfragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.databinding.CustomDialogBinding
import com.mtsapps.chatbuddy.databinding.HistoryItemBinding
import com.mtsapps.chatbuddy.models.Chat

class HistoryAdapter(
    var vievModel: HistoryViewModel,
    val context: Context,
    val layoutInflater: LayoutInflater
) :
    androidx.recyclerview.widget.ListAdapter<Chat, HistoryAdapter.ItemsViewHolder>(ItemsUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding =
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemsViewHolder(binding, vievModel, context, layoutInflater)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }


    class ItemsViewHolder(
        private val binding: HistoryItemBinding,
        private val vievModel: HistoryViewModel,
        private val context: Context,
        private val layoutInflater: LayoutInflater
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chat, position: Int) {
            binding.root.setOnClickListener {
                val trans = HistoryFragmentDirections.toHistoryDetail(item)
                Navigation.findNavController(it).navigate(trans)
            }
            binding.root.setOnLongClickListener {

                val dialog = Dialog(context, R.style.CustomDialogTheme)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)
                val text = dialog.findViewById<TextView>(R.id.dialog_text)
                text.text = context.getString(R.string.itemDeleted)
                dialogBinding.positiveButton.text = context.getString(R.string.ok)
                dialogBinding.negativeButton.text = context.getString(R.string.cancel)

                dialogBinding.positiveButton.setOnClickListener {
                    Log.e("positive", "successful")
                    vievModel.deleteChat(item)
                    dialog.dismiss()
                                   Toast.makeText(context, context.getString(R.string.theChatDeleted), Toast.LENGTH_SHORT).show()


                }
                dialogBinding.negativeButton.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
true
            }
            binding.chatTitleText.text = item.title



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