package ru.kpfu.itis.ponomarev.lexify.presentation.view.decoration

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class HeaderItemDecoration(
    private val listener: StickyHeaderInterface,
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val top = parent.getChildAt(0) ?: return

        val topPos = parent.getChildAdapterPosition(top)
        if (topPos == RecyclerView.NO_POSITION) {
            return
        }

        val currentHeader = getHeaderForItem(topPos, parent)
        fixLayoutSize(parent, currentHeader)
        val contactPoint = currentHeader.bottom
        val childInContact = getChildInContact(parent, contactPoint) ?: return

        val pos = parent.getChildAdapterPosition(childInContact)
        if (pos != RecyclerView.NO_POSITION && listener.isHeader(pos)) {
            moveHeader(c, currentHeader, childInContact)
            return
        }

        drawHeader(c, currentHeader)
    }

    private fun getHeaderForItem(pos: Int, parent: RecyclerView): View {
        val headerPos = listener.getHeaderPositionForItem(pos)
        val headerLayout = listener.getHeaderLayout(pos)
        val header = LayoutInflater.from(parent.context).inflate(headerLayout, parent, false)
        listener.bindHeaderData(header, headerPos)
        return header
    }

    private fun drawHeader(canvas: Canvas, header: View) {
        with (canvas) {
            save()
            translate(0f, 0f)
            header.draw(this)
            restore()
        }
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View, nextHeader: View) {
        with (canvas) {
            save()
            translate(0f, (nextHeader.top - currentHeader.height).toFloat())
            currentHeader.draw(this)
            restore()
        }
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint && child.top <= contactPoint) {
                return child
            }
        }
        return null
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.getPaddingLeft() + parent.getPaddingRight(),
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    interface StickyHeaderInterface {

        fun getHeaderPositionForItem(pos: Int): Int
        fun getHeaderLayout(pos: Int): Int
        fun bindHeaderData(header: View, pos: Int)
        fun isHeader(pos: Int): Boolean
    }
}