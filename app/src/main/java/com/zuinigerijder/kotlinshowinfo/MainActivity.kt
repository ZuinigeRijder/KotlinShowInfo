package com.zuinigerijder.kotlinshowinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                    KotlinShowInfo(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun KotlinShowInfo(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OpenUrlButton(modifier)
        Text(
            text = "" +
                    DateTimeUtil.getDateTimeInfo() + "\n" +
                    BatteryInfoUtil.getBatteryInfo() + "\n" +
                    DisplayInfoUtil.getDisplayInfo() + "\n" +
                    DeviceInfoUtil.getDeviceInfo(),
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        )
    }
}



@Preview(showBackground = true, showSystemUi = true, name = "Kotlin Show Info Preview")
@Composable
fun KotlinShowInfoPreview() {
    KotlinShowInfoTheme {
        KotlinShowInfo()
    }
}