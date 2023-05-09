package com.mtsapps.chatbuddy.utils

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.chatbuddy.ui.homefragment.HomeAdapter

class AdaptiveItemDecoration : RecyclerView.ItemDecoration() {
    private val itemSpacingSmall: Int = 20
    private val itemSpacingBig: Int = 60

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State

    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: return
        val currentViewType = parent.adapter?.getItemViewType(position) ?: return

        if (position > 0) {
            val previousViewType = parent.adapter?.getItemViewType(position - 1) ?: return
            val spacing = when {
                currentViewType == previousViewType -> {
                    itemSpacingSmall
                }
                position == itemCount - 1 -> {
                    itemSpacingBig
                }
                else -> {
                    itemSpacingBig
                }
            }
            outRect.top = spacing
        } else {
            outRect.top = itemSpacingBig
        }
    }
}