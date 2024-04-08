package ru.kpfu.itis.ponomarev.lexify.presentation.view.callback

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.util.dpToPx
import ru.kpfu.itis.ponomarev.lexify.util.spToPx
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KClass

class ItemHorizontalSwipeCallback(
    private val context: Context,
    private val actions: Map<KClass<out RecyclerView.ViewHolder>, ItemHorizontalSwipeActions>,
) : ItemTouchHelper.Callback() {

    private val clearPaint = Paint().apply {
        color = context.getColor(R.color.surface)
    }
    private val backgroundColor = context.getColor(R.color.surface_inverse)
    private val backgroundDrawable = backgroundColor.toDrawable()
    private val textPaint = Paint().apply {
        typeface = ResourcesCompat.getFont(context, R.font.lato_regular)
        color = context.getColor(R.color.on_surface_inverse)
        textSize = context.spToPx(14)
        textAlign = Paint.Align.CENTER
    }
    private val actionTextMargin = context.dpToPx(24).toFloat()

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        var flags = 0
        // maybe it'll also need to account for subclasses
        if (actions[viewHolder::class]?.left != null) flags = flags or ItemTouchHelper.RIGHT
        if (actions[viewHolder::class]?.right != null) flags = flags or ItemTouchHelper.LEFT
        return makeMovementFlags(0, flags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        actions[viewHolder::class]?.invokeAction(viewHolder, direction)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        val itemView = viewHolder.itemView

        val actionName: CharSequence
        val actionBounds: Rect
        val actionTextX: Float
        if (dX < 0) {
            actionBounds = Rect(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            actionName = actions[viewHolder::class]?.right?.actionName ?: ""
            val textWidth = textPaint.measureText(actionName.toString())
            actionTextX = min(itemView.right - actionTextMargin - textWidth / 2f, itemView.right + dX / 2f)
        } else {
            actionBounds = Rect(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt(),
                itemView.bottom,
            )
            actionName = actions[viewHolder::class]?.left?.actionName ?: ""
            val textWidth = textPaint.measureText(actionName.toString())
            actionTextX = max(actionTextMargin + textWidth / 2f, dX / 2f)
        }
        backgroundDrawable.bounds = actionBounds
        backgroundDrawable.draw(c)

        c.drawText(
            actionName.toString(),
            actionTextX,
            itemView.top + itemView.height / 2f + textPaint.textSize / 3f,
            textPaint
        )
        c.clipRect(actionBounds)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }

    data class ItemSwipeAction(
        val actionName: CharSequence,
        val action: (RecyclerView.ViewHolder) -> Unit,
    )

    data class ItemHorizontalSwipeActions(
        val left: ItemSwipeAction? = null,
        val right: ItemSwipeAction? = null,
    ) {

        fun invokeAction(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) right?.action?.invoke(viewHolder)
            if (direction == ItemTouchHelper.RIGHT) left?.action?.invoke(viewHolder)
        }
    }
}