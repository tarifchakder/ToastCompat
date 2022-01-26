package io.tarif.toastcompat.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat

/**
 * @Author: Tarif Chakder
 * @Date: 25-01-2022
 */

@SuppressLint("ViewConstructor")
class ToastCompat : LinearLayout {

    private var cornerRadius = 0
    private var backgroundType = SOLID
    private var drawable: Drawable? = null
    private var drawableTint: Int = -1
    private var toastBackgroundColor = 0
    private var toastGradientStartColor = 0
    private var toastGradientEndColor = 0
    private var strokeColor = 0
    private var strokeWidth = 0
    private var iconStart = 0
    private var iconSize = 33
    private var iconEnd = 0
    private var textColor = 0
    private var font = 0
    private var typeFace: Typeface? = null
    private var length: Int
    private var style = 0
    private var textSize = 0F
    private var isTextSizeFromStyleXml = false
    private var textBold = false
    private var text: String = ""
    private var typedArray: TypedArray? = null
    private var textView: TextView? = null
    private var toastGravity = GRAVITY_BOTTOM
    private var xOffset = 0
    private var yOffset = 0
    private var toast: Toast? = null
    private var rootLayout: LinearLayout? = null
    private var paddingVertical = 8.dp
    private var paddingHorizontal = 15.dp
    private var noIconSidePadding = 16.dp


    constructor(context: Context, text: String, length: Int, @StyleRes style: Int) : super(context) {
        this.text = text
        this.length = length
        this.style = style
    }

    private constructor(builder: Builder) : super(builder.context) {
        toastBackgroundColor = builder.backgroundColor
        cornerRadius = builder.cornerRadius
        iconSize = builder.iconSize
        iconEnd = builder.iconEnd
        iconStart = builder.iconStart
        strokeColor = builder.strokeColor
        strokeWidth = builder.strokeWidth
        textColor = builder.textColor
        textSize = builder.textSize
        textBold = builder.textBold
        font = builder.font
        text = builder.text
        toastGravity = builder.gravity
        xOffset = builder.xOffset
        yOffset = builder.yOffset
        length = builder.length
        typeFace = builder.typeface
        backgroundType = builder.backgroundType
        toastGradientStartColor = builder.gradientStartColor
        toastGradientEndColor = builder.gradientEndColor
        paddingVertical = builder.paddingVertical
        paddingHorizontal = builder.paddingHorizontal
        noIconSidePadding = builder.drawablePadding
        drawable = builder.drawable
        drawableTint = builder.drawableTint
    }

    @SuppressLint("CustomViewStyleable")
    private fun init() {
        val v = inflate(context, R.layout.toast_layout, null)
        rootLayout = v.rootView as LinearLayout
        textView = v.findViewById(R.id.textview)

        val solidColor = ContextCompat.getColor(context, R.color.default_background)
        val gdStartColor = ContextCompat.getColor(context, R.color.default_gradient_start_color)
        val gdEndColor = ContextCompat.getColor(context, R.color.default_gradient_end_color)

        if (style > 0) {
            typedArray = context.obtainStyledAttributes(style, R.styleable.ToastCompat)
            drawable = typedArray!!.getDrawable(R.styleable.ToastCompat_toastDrawableBackground)
            drawableTint = typedArray!!.getInt(R.styleable.ToastCompat_toastDrawableTint, 0)
            backgroundType = typedArray!!.getInt(R.styleable.ToastCompat_toastBackgroundType, SOLID)
            when (backgroundType) {
                1 -> backgroundType = SOLID
                2 -> backgroundType = GRADIENT
            }

            toastGradientStartColor = typedArray!!.getColor(R.styleable.ToastCompat_toastGradientStartColor, gdStartColor)
            toastGradientEndColor = typedArray!!.getColor(R.styleable.ToastCompat_toastGradientEndColor, gdEndColor)
            toastBackgroundColor = typedArray!!.getColor(R.styleable.ToastCompat_toastColorBackground, solidColor)

            cornerRadius = typedArray!!.getDimension(
                R.styleable.ToastCompat_toastRadius,
                resources.getDimension(R.dimen.toast_default_corner_radius).toInt().toFloat()
            ).toInt()
            length = typedArray!!.getInt(R.styleable.ToastCompat_toastLength, 0)
            toastGravity = typedArray!!.getInt(R.styleable.ToastCompat_toastGravity, GRAVITY_BOTTOM)
            determineGravity(toastGravity)


            if (typedArray!!.hasValue(R.styleable.ToastCompat_toastStrokeColor) && typedArray!!.hasValue(R.styleable.ToastCompat_toastStrokeWidth)) {
                strokeWidth = typedArray!!.getDimension(R.styleable.ToastCompat_toastStrokeWidth, 0f).toInt()
                strokeColor = typedArray!!.getColor(R.styleable.ToastCompat_toastStrokeColor, Color.TRANSPARENT)
            }

            iconSize = typedArray!!.getInt(R.styleable.ToastCompat_toastIconSize, 33)
            iconStart = typedArray!!.getResourceId(R.styleable.ToastCompat_toastIconStart, 0)
            iconEnd = typedArray!!.getResourceId(R.styleable.ToastCompat_toastIconEnd, 0)


            textColor = typedArray!!.getColor(R.styleable.ToastCompat_toastTextColor, textView!!.currentTextColor)
            textBold = typedArray!!.getBoolean(R.styleable.ToastCompat_toastTextBold, false)
            textSize = typedArray!!.getDimension(R.styleable.ToastCompat_toastTextSize, 14f.sp)
            font = typedArray!!.getResourceId(R.styleable.ToastCompat_toastFont, 0)
            isTextSizeFromStyleXml = textSize > 0
        }

        initDraw()

        if (typedArray != null) {
            typedArray!!.recycle()
        }
    }

    private fun initDraw() {

        // background
        if (drawable != null) {
            rootLayout!!.background = drawable
            if (drawableTint != -1) {
                if (hasLoliPop()) {
                    rootLayout!!.background.setTint(drawableTint)
                }
            }
        } else if (drawable == null) {
            when (backgroundType) {
                SOLID -> {
                    val solid = rootLayout!!.background.mutate() as GradientDrawable
                    if (strokeWidth > 0) solid.setStroke(strokeWidth, strokeColor)
                    if (cornerRadius > -1) solid.cornerRadius = cornerRadius.toFloat()
                    if (toastBackgroundColor != 0) solid.setColor(toastBackgroundColor)
                    rootLayout!!.background = solid
                }
                GRADIENT -> {
                    val gradientDrawable =
                        GradientDrawable(GradientDrawable.Orientation.BR_TL, intArrayOf(toastGradientStartColor, toastGradientEndColor))
                    if (strokeWidth > 0) gradientDrawable.setStroke(strokeWidth, strokeColor)
                    if (cornerRadius > -1) gradientDrawable.cornerRadii = floatArrayOf(
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat(),
                        cornerRadius.toFloat()
                    )
                    rootLayout!!.background = gradientDrawable
                }
            }
        }

        // textView
        textView!!.text = text
        if (textColor != 0) textView!!.setTextColor(textColor)
        if (textSize > 0) textView!!.setTextSize(if (isTextSizeFromStyleXml) 0 else TypedValue.COMPLEX_UNIT_SP, textSize)
        if (typeFace != null) {
            textView!!.setTypeface(typeFace, if (textBold) Typeface.BOLD else Typeface.NORMAL)
        } else {
            if (font > 0) {
                textView!!.setTypeface(ResourcesCompat.getFont(context, font), if (textBold) Typeface.BOLD else Typeface.NORMAL)
            }
        }
        if (textBold && font == 0) textView!!.setTypeface(textView!!.typeface, Typeface.BOLD)

        // icon
        if (iconStart != 0) {
            val startDrawable = ContextCompat.getDrawable(context, iconStart)
            if (startDrawable != null) {
                startDrawable.setBounds(0, 0, iconSize, iconSize)
                TextViewCompat.setCompoundDrawablesRelative(textView!!, startDrawable, null, null, null)
                if (isRTL) {
                    rootLayout!!.setPadding(
                        noIconSidePadding,
                        paddingVertical,
                        paddingHorizontal,
                        paddingVertical
                    )
                } else {
                    rootLayout!!.setPadding(
                        paddingHorizontal,
                        paddingVertical,
                        noIconSidePadding,
                        paddingVertical
                    )
                }
            }
        }
        if (iconEnd != 0) {
            val endDrawable = ContextCompat.getDrawable(context, iconEnd)
            if (endDrawable != null) {
                endDrawable.setBounds(0, 0, iconSize, iconSize)
                TextViewCompat.setCompoundDrawablesRelative(textView!!, null, null, endDrawable, null)
                if (isRTL) {
                    rootLayout!!.setPadding(
                        paddingHorizontal,
                        paddingVertical,
                        noIconSidePadding,
                        paddingVertical
                    )
                } else {
                    rootLayout!!.setPadding(
                        noIconSidePadding,
                        paddingVertical,
                        paddingHorizontal,
                        paddingVertical
                    )
                }
            }
        }
        if (iconStart != 0 && iconEnd != 0) {
            val drawableLeft = ContextCompat.getDrawable(context, iconStart)
            val drawableRight = ContextCompat.getDrawable(context, iconEnd)
            if (drawableLeft != null && drawableRight != null) {
                drawableLeft.setBounds(0, 0, iconSize, iconSize)
                drawableRight.setBounds(0, 0, iconSize, iconSize)
                textView!!.setCompoundDrawables(drawableLeft, null, drawableRight, null)
                rootLayout!!.setPadding(
                    paddingHorizontal,
                    paddingVertical,
                    paddingHorizontal,
                    paddingVertical
                )
            }
        }

        if (iconEnd == 0 && iconStart == 0) {
            rootLayout!!.rootView.updatePadding(left = paddingHorizontal, top = paddingVertical, right = paddingHorizontal, bottom = paddingVertical)
        }

    }

    private fun determineGravity(gravity: Int) {
        when (gravity) {
            GRAVITY_CENTER -> toastGravity = Gravity.CENTER
            GRAVITY_TOP -> toastGravity = Gravity.TOP
            GRAVITY_BOTTOM -> toastGravity = Gravity.BOTTOM
            GRAVITY_LEFT -> toastGravity = Gravity.LEFT
            GRAVITY_START -> toastGravity = Gravity.START

        }
    }

    fun show() {
        init()
        toast = Toast(context)

        determineGravity(toastGravity)
        if (xOffset == 0 && yOffset == 0) {
            xOffset = 0
            yOffset = if (toastGravity == Gravity.CENTER) 0 else toast!!.yOffset
        }

        toast!!.setGravity(toastGravity, xOffset, yOffset)
        toast!!.duration = if (length == Toast.LENGTH_LONG) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        toast!!.view = rootLayout
        toast!!.show()
    }

    fun cancel() {
        if (toast != null) {
            toast!!.cancel()
        }
    }

    class Builder(val context: Context) {
        internal var cornerRadius = 8
        internal var backgroundType = SOLID
        internal var drawable: Drawable? = null
        internal var drawableTint = -1
        internal var gradientStartColor = 0
        internal var gradientEndColor = 0
        internal var backgroundColor = 0
        internal var strokeColor = 0
        internal var strokeWidth = 0
        internal var iconSize = 33
        internal var iconStart = 0
        internal var iconEnd = 0
        internal var textColor = Color.WHITE
        internal var font = 0
        internal var typeface: Typeface? = null
        internal var length = 0
        internal var textSize = 14f
        internal var textBold = false
        internal var text: String = ""
        internal var gravity = GRAVITY_BOTTOM
        internal var xOffset = 0
        internal var yOffset = 0
        internal var paddingVertical = 8.dp
        internal var paddingHorizontal = 15.dp
        internal var drawablePadding = 16.dp
        private var toast: ToastCompat? = null


        /**
         * @param text is String to show Toastify's  message
         *
         * */
        fun text(text: String): Builder {
            this.text = text
            return this
        }

        /**
         * @param textColor is the text Color of Toastify's message
         *
         * */
        fun textColor(@ColorInt textColor: Int): Builder {
            this.textColor = textColor
            return this
        }

        /**
         * This method enabled Toastify's style BOLD if not added Then
         * it enabled  default style text style NORMAL
         * */
        fun textBold(): Builder {
            textBold = true
            return this
        }

        /**
         * @param textSize Resize your Toastify's text
         *
         * */
        fun textSize(textSize: Float): Builder {
            this.textSize = textSize
            return this
        }

        /**
         * @param font A font create from asset or file
         * like TypeFace.createFromAsset(assetManger,"fonts/robot.ttf")
         * */
        fun font(font: Typeface): Builder {
            this.typeface = font
            return this
        }

        /**
         * @param font A font resource id like R.font.roboto
         * as introduced with the new font api in Android 8
         */
        fun font(@FontRes font: Int): Builder {
            this.font = font
            return this
        }

        /**
         * @param type show Toastify's background type
         * For Toastify.SOLID [ set background color solid ]
         * For Toastify.GRADIENT [ set background color gradient ]
         *
         * Remember when set Toastify.GRADIENT then set your Gradient start color and gradient color
         * by calling gradientColor
         * */
        fun backgroundType(type: Int): Builder {
            this.backgroundType = type
            return this
        }

        /**
         * @param startColor
         * @param endColor
         *
         *  Change Toastify background gradient Color
         *
         *
         * */
        fun gradientColor(@ColorInt startColor: Int, @ColorInt endColor: Int): Builder {
            this.gradientStartColor = startColor
            this.gradientEndColor = endColor
            return this
        }

        /**
         * @param backgroundColor change Toastify's background color
         * */
        fun backgroundColor(@ColorInt backgroundColor: Int): Builder {
            this.backgroundColor = backgroundColor
            return this
        }


        /**
         * @param drawable set custom shape view of Toastify background
         * If background Drawable set [ backgroundColor() , backgroundType() are optional to use ]
         * */
        fun setBackgroundDrawable(drawable: Drawable): Builder {
            this.drawable = drawable
            return this
        }

        /**
         * @param drawableTint set custom shape view of Toastify background
         * If background Drawable set [ backgroundColor() , backgroundType() are optional to use ]
         * This should be working Above LOLIPOP
         * */
        fun setDrawableTint(drawableTint: Int): Builder {
            this.drawableTint = drawableTint
            return this
        }

        fun stroke(strokeWidth: Int, @ColorInt strokeColor: Int): Builder {
            this.strokeWidth = strokeWidth.dp
            this.strokeColor = strokeColor
            return this
        }

        /**
         * @param cornerRadius Sets the corner radius of the StyleableToast's shape.
         */
        fun cornerRadius(cornerRadius: Int): Builder {
            this.cornerRadius = cornerRadius.dp
            return this
        }

        /**
         * @param paddingHorizontal vertical padding from rootView
         * @param paddingVertical horizontal padding from rootView
         * @param drawablePadding padding empty side rootView
         *
         * */
        fun padding(
            paddingVertical: Float = context.resources.getDimension(R.dimen.toast_vertical_padding),
            paddingHorizontal: Float = context.resources.getDimension(R.dimen.toast_horizontal_padding_icon_side),
            noIconSidePadding: Float = context.resources.getDimension(R.dimen.toast_horizontal_padding_empty_side)
        ): Builder {
            this.paddingVertical = paddingVertical.toInt()
            this.paddingHorizontal = paddingHorizontal.toInt()
            this.drawablePadding = noIconSidePadding.toInt()
            return this
        }

        /**
         * @param size change the Toastify Icon size
         *
         * */
        fun iconSize(size: Int): Builder {
            this.iconSize = size
            return this
        }

        fun iconStart(@DrawableRes iconStart: Int): Builder {
            this.iconStart = iconStart
            return this
        }

        fun iconEnd(@DrawableRes iconEnd: Int): Builder {
            this.iconEnd = iconEnd
            return this
        }

        /**
         * Sets where the StyleableToast will appear on the screen
         */
        fun gravity(gravity: Int, xOffset: Int = 0, yOffset: Int = 0): Builder {
            this.gravity = gravity
            this.xOffset = xOffset
            this.yOffset = yOffset
            return this
        }

        /**
         * @param length [Toast.LENGTH_SHORT] or [Toast.LENGTH_LONG]
         */
        fun length(length: Int): Builder {
            this.length = length
            return this
        }

        /**
         * @return an mutable instance of the build
         */
        fun build(): Builder {
            return this
        }

        fun show() {
            toast = ToastCompat(this)
            toast!!.show()
        }

        fun cancel() {
            if (toast != null) {
                toast!!.cancel()
            }
        }
    }

    companion object {
        const val GRADIENT = 2
        const val SOLID = 1
        const val GRAVITY_CENTER = 1
        const val GRAVITY_TOP = 2
        const val GRAVITY_BOTTOM = 3
        const val GRAVITY_LEFT = 4
        const val GRAVITY_START = 5

        fun makeText(context: Context, text: String, length: Int, @StyleRes style: Int): ToastCompat {
            return ToastCompat(context, text, length, style)
        }

        fun makeText(context: Context, text: String, @StyleRes style: Int): ToastCompat {
            return ToastCompat(context, text, Toast.LENGTH_SHORT, style)
        }

    }


}