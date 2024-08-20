package com.example.assaxiybookcompose.presenter.ui.components.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class CustomRippleColor(private val color: Color) : RippleTheme {
    @Composable
    override fun defaultColor(): Color {
        return color
    }

    @Composable
    override fun rippleAlpha(): RippleAlpha {
        return RippleAlpha(
            draggedAlpha = 0.5f,
            focusedAlpha = 0.5f,
            hoveredAlpha = 0.5f,
            pressedAlpha = 0.5f
        )
    }
}