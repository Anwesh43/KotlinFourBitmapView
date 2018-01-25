package ui.anwesome.com.kotlinfourbitmapview

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.fourbitmapview.FourBitmapView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FourBitmapView.create(this,BitmapFactory.decodeResource(resources,R.drawable.nature_more))
    }
}
