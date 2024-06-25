package com.zuinigerijder.kotlinshowinfo

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.activity.ComponentActivity.BATTERY_SERVICE
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlin.time.Duration.Companion.milliseconds

class BatteryInfoUtil {
    companion object {
        @Composable
        fun getBatteryInfo() : String {
            var result = "\nBatteryInfo\n"
            val context = LocalContext.current
            val batteryManager = context.getSystemService(BATTERY_SERVICE) as BatteryManager
            val info =
                IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
                        intentFilter -> context.registerReceiver(null, intentFilter)
                }
            if (info != null) {

                // percentage
                val level = info.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = info.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                if (scale > 0) {
                    val batteryPct = level * 100 / scale.toFloat()
                    result += "$batteryPct% "
                }
                result += "$level/$scale"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val batteryLow = info.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
                    if (batteryLow) {
                        result += " (BatteryLow)"
                    }
                }

                // status
                val status = info.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                result += "\nStatus: " + getBatteryStatusText(status)

                // charging
                val plugged = info.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                if (plugged > -1) {
                    result += "\nCharging: " + getBatteryPluggedText(plugged)}

                // health
                val health = info.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
                result += "\nHealth: " + getBatteryHealthText(health)

                val technology = info.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
                result += "\nTechnology: $technology"

                val present = info.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)
                if (!present) {
                    result += "\nPresent: false"
                }

                val voltage = info.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -99)
                result += "\nVoltage: $voltage mV"

                val temperature = info.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -99) / 10.0
                result += "\nTemperature: $temperature °C"

                val currentCapacity =
                    batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                result += "\nCurrent Capacity percentage: $currentCapacity%"
                var currentChargeCounter =
                    batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
                if (currentCapacity > 0) {
                    val totalCapacity = (currentChargeCounter / currentCapacity * 100).toLong() / 1000
                    result += "\nTotal Capacity: $totalCapacity mAh"
                }
                currentChargeCounter /= 1000
                result += "\nCurrent Charge counter: $currentChargeCounter mAh"
                val currentNow =
                    batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000
                result += "\nCurrent Now: $currentNow mA"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val chargeTimeRemaining = batteryManager.computeChargeTimeRemaining()
                    val chargeTimeRemainingDuration = chargeTimeRemaining.milliseconds
                    result += "\nCharge Time remaining: $chargeTimeRemainingDuration"
                }
            }
            return result
        }

        private fun getBatteryHealthText(batteryHealth: Int): String {
            return when (batteryHealth) {
                BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified Failure"
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> " (Over Voltage)"
                BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
                BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
                BatteryManager.BATTERY_HEALTH_UNKNOWN -> "Unknown"
                else -> "$batteryHealth"
            }
        }

        private fun getBatteryStatusText(batteryStatus: Int): String {
            return when (batteryStatus) {
                BatteryManager.BATTERY_STATUS_FULL -> "Full"
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
                BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
                BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
                BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
                else -> "$batteryStatus"
            }
        }

        private fun getBatteryPluggedText(batteryPlugged: Int): String {
            return when (batteryPlugged) {
                BatteryManager.BATTERY_PLUGGED_AC -> "AC"
                BatteryManager.BATTERY_PLUGGED_USB -> "USB"
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
                BatteryManager.BATTERY_PLUGGED_DOCK -> "Dock"
                0 -> "No charger"
                else -> "$batteryPlugged"
            }
        }
    }
}