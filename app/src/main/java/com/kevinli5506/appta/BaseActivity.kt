package com.kevinli5506.appta

import android.app.Activity
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity():AppCompatActivity() {
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val handleReturn =  super.dispatchTouchEvent(ev)
        val view = currentFocus

        val x = ev.getX().toInt()
        val y = ev.getY().toInt()
        if(view is EditText){
            val innerView = currentFocus as EditText
            if (ev.getAction() == MotionEvent.ACTION_UP &&
                !getLocationOnScreen(innerView).contains(x, y)){
                hideSoftKeyboard(this)
            }
        }
        return handleReturn
    }
    open fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus.windowToken, 0
        )
    }
    protected open fun getLocationOnScreen(mEditText: EditText): Rect {
        val mRect = Rect()
        val location = IntArray(2)
        mEditText.getLocationOnScreen(location)
        mRect.left = location[0]
        mRect.top = location[1]
        mRect.right = location[0] + mEditText.width
        mRect.bottom = location[1] + mEditText.height
        return mRect
    }
}