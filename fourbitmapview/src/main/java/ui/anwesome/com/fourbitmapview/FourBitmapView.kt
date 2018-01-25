package ui.anwesome.com.fourbitmapview

/**
 * Created by anweshmishra on 25/01/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
import java.util.concurrent.ConcurrentLinkedQueue

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
    data class SideBitmapContainer(var bitmap:Bitmap,var w:Float,var h:Float) {
        val state = SideBitmapContainerState()
        val bitmapList:ConcurrentLinkedQueue<SideBitmap> = ConcurrentLinkedQueue()
        init {
            bitmap = Bitmap.createScaledBitmap(bitmap,(w/2).toInt(),(h/2).toInt(),true)
            for(i in 0..3) {
                bitmapList.add(SideBitmap(i))
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            if(state.scales.size == 2) {
                canvas.drawColor(Color.parseColor("#212121"))
                bitmapList.forEach {
                    it.draw(canvas, paint, bitmap, w, h, state.scales[0], state.scales[1])
                }
            }
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
        }
    }
    data class SideBitmapContainerState(var dir:Int = 1,var scaleDir:Float = 0f,var prevScale:Float = 0f,var j:Int = 0) {
        val scales:Array<Float> = arrayOf(0f,0f)
        fun update(stopcb:(Float)->Unit) {
            scales[j] += 0.1f*scaleDir
            if(Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + scaleDir
                j += dir
                if(j == scales.size || j == -1) {
                    dir*=-1
                    j += dir
                    prevScale = scales[j]
                    scaleDir = 0f
                    stopcb(scales[j])
                }

            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(scaleDir == 0f) {
                scaleDir = 1-2*this.prevScale
                startcb()
            }
        }
    }
    data class Animator(var view:FourBitmapView,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class Renderer(var view:FourBitmapView,var time:Int = 0) {
        var container:SideBitmapContainer?=null
        val animator = Animator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                container = SideBitmapContainer(view.bitmap,w,h)
            }
            container?.draw(canvas,paint)
            time++
            animator.animate {
                container?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            container?.startUpdating {
                animator.start()
            }
        }
    }
}