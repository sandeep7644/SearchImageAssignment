package com.example.assignment.custom_views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class BoldTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val font =
            Typeface.createFromAsset(getContext().assets, "fonts/Montserrat-SemiBold.ttf")
        this.setTypeface(font)
    }

}