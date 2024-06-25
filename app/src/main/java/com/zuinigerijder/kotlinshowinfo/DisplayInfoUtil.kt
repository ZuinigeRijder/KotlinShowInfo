package com.zuinigerijder.kotlinshowinfo

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale
import kotlin.math.hypot

class DisplayInfoUtil {
    companion object {
        @Suppress("DEPRECATION")
        @Composable
        fun getDisplayInfo() : String {
            var text = "\nDisplayInfo"
            val locale = Locale.getDefault()

            val context = LocalContext.current
            val windowManager = (context as Activity).windowManager
            val displayMetrics = context.resources.displayMetrics
            val widthPixels = if (windowManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowManager.currentWindowMetrics.bounds.width()
            } else {
                displayMetrics.widthPixels
            }
            val heightPixels = if (windowManager == null) {
                displayMetrics.heightPixels
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowManager.currentWindowMetrics.bounds.height()
            } else {
                windowManager.defaultDisplay.getRealMetrics(displayMetrics)
                displayMetrics.heightPixels
            }

            val widthInInches = (widthPixels / displayMetrics.xdpi).toDouble()
            val widthInInchesString = String.format(locale, "%.1f", widthInInches)
            text += "\nWidth : $widthPixels pixels, ${displayMetrics.xdpi.toInt()} dp, $widthInInchesString inch"

            val heightInInches = (heightPixels / displayMetrics.ydpi).toDouble()
            val heightInInchesString = String.format(locale, "%.1f", heightInInches)
            text += "\nHeight : $heightPixels pixels, ${displayMetrics.ydpi.toInt()} dp, $heightInInchesString inch"

            // compute average dpi for width and height, because dpi for them can be different
            val dpi = (
                    ((widthPixels * displayMetrics.xdpi) + (heightPixels * displayMetrics.ydpi)) /
                    (widthPixels + heightPixels)
                    )
            text += "\ndpi: ${dpi.toInt()}"

            val screenInches = hypot(widthInInches, heightInInches)
            val screenInchesString = String.format(locale, "%.1f", screenInches)
            text += "\nScreen size: $screenInchesString inch"

            val gcd = calculateGreatestCommonDivider(heightPixels, widthPixels)
            text += "\nAspect Ratio: " + heightPixels / gcd + ":" + widthPixels / gcd

            return text
        }

        private fun calculateGreatestCommonDivider(height: Int, width: Int): Int {
            if (width == 0)
                return height
            return calculateGreatestCommonDivider(width, height % width)
        }
    }
}

