package ali.com.timelaps

import android.graphics.drawable.shapes.Shape
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.AbsoluteLayout
import android.widget.Button
import kotlin.random.Random

class Harder : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var displayMetrics: DisplayMetrics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harder)

        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var widthArea=displayMetrics.widthPixels
        var height=displayMetrics.heightPixels

        var rr: Random
        var absouluteLayout:AbsoluteLayout.LayoutParams


    }
}
