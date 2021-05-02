package com.j.android.creatures.ui

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class NoSwipeViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

  override fun onTouchEvent(event: MotionEvent) = false

  override fun onInterceptTouchEvent(event: MotionEvent) = false
}