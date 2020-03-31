package com.syr.demo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.FragmentActivity
import com.lhcredit.commonres.widget.ProgressBarView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * @author Karen
 * @date 2020-03-25
 */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress1.setDrawable(
            resources.getColor(R.color.colorAccent),
            backgroundColor = Color.parseColor("#DCDCDC")
        )
        progress2.setDrawable(
            resources.getColor(R.color.colorAccent),
            ProgressBarView.CornerType.ALL,
            backgroundColor = Color.parseColor("#DCDCDC")
        )
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progress1.progress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun changeCorner(view: View) {
        progress1.progress = 0
        when (progress1.configuredCornerType) {
            ProgressBarView.CornerType.NONE -> {
                progress1.setCornerType(ProgressBarView.CornerType.LEFTTOP)
                tvButton.text = "左下圆角"
            }
            ProgressBarView.CornerType.LEFTTOP -> {
                progress1.setCornerType(ProgressBarView.CornerType.LEFTBOTTOM)
                tvButton.text = "左边圆角"
            }
            ProgressBarView.CornerType.LEFTBOTTOM -> {
                progress1.setCornerType(ProgressBarView.CornerType.LEFT)
                tvButton.text = "右上圆角"
            }
            ProgressBarView.CornerType.LEFT -> {
                progress1.setCornerType(ProgressBarView.CornerType.RIGHTTOP)
                tvButton.text = "右下圆角"
            }
            ProgressBarView.CornerType.RIGHTTOP -> {
                progress1.setCornerType(ProgressBarView.CornerType.RIGHTBOTTOM)
                tvButton.text = "右边圆角"
            }
            ProgressBarView.CornerType.RIGHTBOTTOM -> {
                progress1.setCornerType(ProgressBarView.CornerType.RIGHT)
                tvButton.text = "圆角"
            }
            ProgressBarView.CornerType.RIGHT -> {
                progress1.setCornerType(ProgressBarView.CornerType.ALL)
                tvButton.text = "无圆角"
            }
            ProgressBarView.CornerType.ALL -> {
                progress1.setCornerType(ProgressBarView.CornerType.NONE)
                tvButton.text = "左上圆角"
            }
        }
        progress1.progress = 100
    }

    fun left2Right(view: View) {
        progress2.progress = 0
        progress2.setGravity(Gravity.START)
        progress2.startAnim(100)
    }

    fun right2left(view: View) {
        progress2.progress = 0
        progress2.setGravity(Gravity.RIGHT)
        progress2.startAnim(100)
    }

    fun changeColor(view: View) {
        progress2.progress = 0
        progress2.setProgressColor(getRandColor())
        progress2.startAnim(100)
    }

    fun toList(view: View) {
        startActivity(Intent(this, ListActivity::class.java))
    }

    private fun getRandColor(): Int {
        var r: String
        var g: String
        var b: String
        val random = Random()
        r = Integer.toHexString(random.nextInt(256)).toUpperCase()
        g = Integer.toHexString(random.nextInt(256)).toUpperCase()
        b = Integer.toHexString(random.nextInt(256)).toUpperCase()
        r = if (r.length == 1) "0$r" else r
        g = if (g.length == 1) "0$g" else g
        b = if (b.length == 1) "0$b" else b
        return Color.parseColor("#${r + g + b}")
    }


}