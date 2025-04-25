package com.example.contacts.screens.contacts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R

class SwipeToDeleteCallback(
    private val context: Context,
    private val onSwipedCallback: (position: Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
        onSwipedCallback(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val paint = Paint()
        val icon =
            ContextCompat.getDrawable(context, R.drawable.arrow_back)!!

        // Draw red background
        paint.color = Color.RED
        c.drawRect(
            itemView.right + dX, itemView.top.toFloat(),
            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
        )

        // Draw icon
        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + icon.intrinsicHeight

        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
