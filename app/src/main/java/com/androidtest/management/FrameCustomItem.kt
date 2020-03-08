package com.androidtest.management

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout

class FrameCustomItem : FrameLayout {

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        isSaveEnabled = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = (MeasureSpec.getSize(widthMeasureSpec)) * (0.3)
        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(newWidthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(newWidthMeasureSpec, heightMeasureSpec)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = superState?.let { BundleSaveState(it) }
        // savedState.getBundle().putString("key", value);

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val bundleSaveState = state as BundleSaveState
        super.onRestoreInstanceState(state)

        val bundle = bundleSaveState.getBundle()
    }

}