package ui.anwesome.com.fourbitmapview

/**
 * Created by anweshmishra on 25/01/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class FourBitmapView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                
            }
        }
        return true
    }
}