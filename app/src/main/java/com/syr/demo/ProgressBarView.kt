package com.lhcredit.commonres.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ProgressBar

/**
 * @author Karen
 * @date 2020-03-25
 */
class ProgressBarView : ProgressBar {
    private var animator: ValueAnimator? = null
    var duration: Long = 600
    val corner = 10f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    fun startAnim(progress: Int, onAnimationEnd: () -> Unit = {}) {
        animator = ValueAnimator.ofFloat(0f, progress.toFloat())
        animator?.duration = duration
        animator?.addUpdateListener { animation ->
            val value = animation?.animatedValue as Float
            setProgress(value.toInt())
        }
        animator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                onAnimationEnd()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animator?.start()
    }

    override fun onDetachedFromWindow() {
        if (animator?.isRunning == true) animator?.end()
        super.onDetachedFromWindow()
    }

    var configuredId: String? = null//已设置过的progressbar；解决多次设置空白问题
    var configuredColor = -1
    var configuredBackgroundColor = Color.TRANSPARENT
    var configuredCornerType: CornerType = CornerType.NONE
    var configuredGravity = -1
    fun setProgressColor(color: Int) {
        setDrawable(
            color, configuredCornerType, configuredGravity,
            configuredBackgroundColor, configuredId
        )
    }

    fun setCornerType(cornerType: CornerType) {
        setDrawable(
            configuredColor, cornerType, configuredGravity,
            configuredBackgroundColor, configuredId
        )
    }

    fun setGravity(gravity: Int) {
        setDrawable(
            configuredColor, configuredCornerType, gravity,
            configuredBackgroundColor, configuredId
        )
    }

    fun setBgColor(backgroundColor: Int) {
        setDrawable(
            configuredColor, configuredCornerType, configuredGravity,
            backgroundColor, configuredId
        )
    }

    fun setDrawable(
        color: Int = Color.RED,
        cornerType: CornerType = CornerType.NONE,
        gravity: Int = Gravity.START,
        backgroundColor: Int = Color.TRANSPARENT, id: String? = null
    ) {
        if (configuredId != id || configuredColor != color || configuredCornerType != cornerType || configuredGravity != gravity) {
            val gradientDrawable = GradientDrawable()
            //cornerRadii是长度为8的float数组，每两个值对应分别为左上，右上，右下，左下，两个值分别为x和y方向的半径
            gradientDrawable.cornerRadii = getCornerArray(cornerType)
            gradientDrawable.setColor(color)
            progressDrawable = ClipDrawable(gradientDrawable, gravity, ClipDrawable.HORIZONTAL)

            val backgroundDrawable = GradientDrawable()
            backgroundDrawable.cornerRadii = getCornerArray(cornerType)
            backgroundDrawable.setColor(backgroundColor)
            background = backgroundDrawable

            configuredId = id
            configuredColor = color
            configuredCornerType = cornerType
            configuredGravity = gravity
            configuredBackgroundColor = backgroundColor
        }
    }

    private fun getCornerArray(cornerType: CornerType): FloatArray {
        val cornerPx = dp2px(corner)
        when (cornerType) {
            CornerType.LEFT -> {
                return floatArrayOf(cornerPx, cornerPx, 0f, 0f, 0f, 0f, cornerPx, cornerPx)
            }
            CornerType.LEFTTOP -> {
                return floatArrayOf(cornerPx, cornerPx, 0f, 0f, 0f, 0f, 0f, 0f)
            }
            CornerType.LEFTBOTTOM -> {
                return floatArrayOf(0F, 0f, 0f, 0f, 0f, 0f, cornerPx, cornerPx)
            }
            CornerType.RIGHT -> {
                return floatArrayOf(0f, 0f, cornerPx, cornerPx, cornerPx, cornerPx, 0f, 0f)
            }
            CornerType.RIGHTTOP -> {
                return floatArrayOf(0f, 0f, cornerPx, cornerPx, 0f, 0f, 0f, 0f)
            }
            CornerType.RIGHTBOTTOM -> {
                return floatArrayOf(0f, 0f, 0f, 0f, cornerPx, cornerPx, 0f, 0f)
            }
            CornerType.ALL -> {
                return floatArrayOf(
                    cornerPx,
                    cornerPx,
                    cornerPx,
                    cornerPx,
                    cornerPx,
                    cornerPx,
                    cornerPx,
                    cornerPx
                )
            }
            else -> {
                return floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
            }
        }
    }

    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

    enum class CornerType {
        NONE, LEFT, RIGHT, ALL, LEFTTOP, LEFTBOTTOM, RIGHTTOP, RIGHTBOTTOM
    }
}