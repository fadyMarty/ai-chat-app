package com.fadymarty.rak_gpt.common.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class RakColorScheme(
    val light: Color = RakPalette.Light,
    val lightHover: Color = RakPalette.LightHover,
    val lightActive: Color = RakPalette.LightActive,
    val normal: Color = RakPalette.Normal,
    val normalHover: Color = RakPalette.NormalHover,
    val normalActive: Color = RakPalette.NormalActive,
    val dark: Color = RakPalette.Dark,
    val darkActive: Color = RakPalette.DarkActive,
    val darkHover: Color = RakPalette.DarkHover,
    val darker: Color = RakPalette.Darker,
    val violet1: Color = RakPalette.Violet1,
    val violet2: Color = RakPalette.Violet2,
    val violet3: Color = RakPalette.Violet3,
    val violet4: Color = RakPalette.Violet4,
    val violet5: Color = RakPalette.Violet5,
    val violet6: Color = RakPalette.Violet6,
    val violet7: Color = RakPalette.Violet7,
    val violet8: Color = RakPalette.Violet8,
    val violet9: Color = RakPalette.Violet9,
    val violet10: Color = RakPalette.Violet10,
    val black1: Color = RakPalette.Black1,
    val black2: Color = RakPalette.Black2,
    val black3: Color = RakPalette.Black3,
    val black4: Color = RakPalette.Black4,
    val black5: Color = RakPalette.Black5,
    val black6: Color = RakPalette.Black6,
    val black7: Color = RakPalette.Black7,
    val black8: Color = RakPalette.Black8,
    val black9: Color = RakPalette.Black9,
    val black10: Color = RakPalette.Black10,
    val black11: Color = RakPalette.Black11,
    val black12: Color = RakPalette.Black12,
    val black13: Color = RakPalette.Black13,
)

val LocalRakColorScheme = staticCompositionLocalOf {
    RakColorScheme()
}