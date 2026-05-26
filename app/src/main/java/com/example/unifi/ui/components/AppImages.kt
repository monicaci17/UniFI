package com.example.unifi.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppImage(
    @DrawableRes imageRes: Int,
    description: String,
    modifier: Modifier = Modifier,
    size: Dp = 88.dp
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = description,
        modifier = modifier.size(size)
    )
}
