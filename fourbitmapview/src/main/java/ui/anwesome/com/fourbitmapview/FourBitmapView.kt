package ui.anwesome.com.fourbitmapview

/**
 * Created by anweshmishra on 25/01/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
val colors:Array<String> = arrayOf("f44336","673AB7","006064","0288D1")
class FourBitmapView(ctx:Context,var bitmap:Bitmap):View(ctx) {
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
    data class SideBitmap(var i:Int) {
        fun draw(canvas:Canvas,paint:Paint, bitmap:Bitmap, w:Float, h:Float, scale1:Float,scale2:Float) {
            paint.style = Paint.Style.FILL
            val x = w/2*(i%2) +w/4
            val y = h/2*(i/2)+w/4
            val px = w/2*(i%2)
            val py = h/2*(i/2)
            val dx = px + (w/2-px)*scale2
            val dy = py + (h/2-py)*scale2
            canvas.save()
            canvas.translate(w/2+(x-w/2)*scale1,h/2+(y-h/2)*scale1)
            paint.color = Color.BLACK
            canvas.drawBitmap(bitmap,-w/4,-h/4,paint)
            canvas.restore()
            paint.color = Color.parseColor("#AA${colors[i]}")
            canvas.save()
            val path = Path()
            path.moveTo(px,py)
            path.lineTo(dx,py)
            path.lineTo(dx,dy)
            path.lineTo(px,dy)
            path.lineTo(px,py)
            canvas.drawPath(path,paint)
            canvas.restore()
        }
    }
}