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
        Log.e("position", position.toString())


        /*if (position == parent.adapter!!.itemCount-1) {
            outRect.bottom = itemSpacingBig
        }*/
        val currentViewType = parent.adapter?.getItemViewType(position) ?: return

        if (position > 0) {
            // Get view type of previous item, if not the first item
            val previousViewType = parent.adapter?.getItemViewType(position - 1) ?: return

            // Set spacing based on view type
            val spacing = when {
                currentViewType == previousViewType -> {
                    // If current item view type is same as previous item view type, set smaller spacing
                    itemSpacingSmall
                }
                position == itemCount - 1 -> {
                    // If current item is the last item, set bottom spacing
                    itemSpacingBig
                }
                else -> {
                    // Otherwise, set normal spacing
                    itemSpacingBig
                }
            }

            // Apply spacing to the item view
            outRect.top = spacing
        } else {
            // If first item, set normal spacing
            outRect.top = itemSpacingBig
        }
    }
}