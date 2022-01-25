package io.tarif.toastcompat.ktx

import android.content.res.Resources
import android.util.TypedValue
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import java.util.*

/**
 * @Author: Tarif Chakder
 * @Date: 25-01-2022
 */


val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.sp : Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this, Resources.getSystem().displayMetrics)

val isRTL get() = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL
