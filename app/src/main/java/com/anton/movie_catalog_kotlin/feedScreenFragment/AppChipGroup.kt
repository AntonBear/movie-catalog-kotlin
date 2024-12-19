package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.chip.ChipGroup
import kotlin.math.min


class AppChipGroup : ChipGroup {
    private var mMaxHeight = Int.MAX_VALUE

    constructor(context: Context?) : super(context)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(attrs, MAX_HEIGHT_ATTRS)
        mMaxHeight = typedArray.getDimensionPixelSize(0, Int.MAX_VALUE)
        typedArray.recycle()
    }

    @SuppressLint("RestrictedApi")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height =
            min(measuredHeight.toDouble(), mMaxHeight.toDouble()).toInt()
        setMeasuredDimension(measuredWidth, height)
    }

    companion object {
        private val MAX_HEIGHT_ATTRS = intArrayOf(R.attr.maxHeight)
    }
}