package com.zuinigerijder.kotlinshowinfo

import android.content.ContentResolver
import android.os.BatteryManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zuinigerijder.kotlinshowinfo.ui.theme.KotlinShowInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinShowInfoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KotlinShowInfo(
                        windowManager,
                        getSystemService(BATTERY_SERVICE) as BatteryManager,
                        contentResolver,
                        modifier = Modifier.padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                            .horizontalScroll(rememberScrollState())
                    )
                }
            }
        }
    }
}

@Composable
fun KotlinShowInfo(
    windowManager: WindowManager?,
    batteryManager: BatteryManager?,
    contentResolver: ContentResolver?,
    modifier: Modifier = Modifier) {
    Text(
        text = "" +
                DateTimeUtil.getDateTimeInfo() + "\n" +
                BatteryInfoUtil.getBatteryInfo(batteryManager) + "\n" +
                DisplayInfoUtil.getDisplayInfo(windowManager) + "\n" +
                DeviceInfoUtil.getDeviceInfo(contentResolver),
        modifier = modifier
    )
}



@Preview(showBackground = true, showSystemUi = true, name = "Kotlin Show Info Preview")
@Composable
fun KotlinShowInfoPreview() {
    KotlinShowInfoTheme {
        KotlinShowInfo(null, null, null)
    }
}