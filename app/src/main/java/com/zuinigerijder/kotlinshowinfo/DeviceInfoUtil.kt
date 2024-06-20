package com.zuinigerijder.kotlinshowinfo

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable

class DeviceInfoUtil {
    companion object {
        @SuppressLint("HardwareIds")
        @Composable
        fun getDeviceInfo(contentResolver: ContentResolver?): String {
            var text = "\nDeviceInfo\n" +
                "Brand: ${Build.BRAND} \n" +
                "Model: ${Build.MODEL} \n" +
                "ID: ${Build.ID} \n" +
                "SDK: ${Build.VERSION.SDK_INT} \n" +
                "Manufacture: ${Build.MANUFACTURER} \n" +
                "Brand: ${Build.BRAND} \n" +
                "User: ${Build.USER} \n" +
                "Type: ${Build.TYPE} \n" +
                "Base: ${Build.VERSION_CODES.BASE} \n" +
                "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
                "Board: ${Build.BOARD} \n" +
                "Host: ${Build.HOST} \n" +
                "FingerPrint: ${Build.FINGERPRINT} \n" +
                "Version Code: ${Build.VERSION.RELEASE}\n"
            if (contentResolver != null) {
                text += "DeviceID: ${
                    Settings.Secure.getString(
                        contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                } \n"
            }
            return text
        }
    }
}