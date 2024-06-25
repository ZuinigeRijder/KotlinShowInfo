package com.zuinigerijder.kotlinshowinfo

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun OpenUrlButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        modifier = modifier,
        onClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ZuinigeRijder/hyundai_kia_connect_monitor"))
            context.startActivity(browserIntent)
        }
    ) {
        Text(text = "Source code")
    }
}