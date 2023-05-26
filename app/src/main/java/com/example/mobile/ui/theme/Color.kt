package com.example.mobile.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.mobile.R

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

var assignment_color_1 = Color(0xFF8EA7F1)
var assignment_color_2 = Color(0xFF61A8CE)

var cycle_color_1 = Color(0xFFB892FF)
var cycle_color_2 = Color(0xFF616CCE)

var condition_color_1 = Color(0xFF57989E)
var condition_color_2 = Color(0xFF6163CE)

var init_color_1 = Color(0xFF83E9E9)
var init_color_2 = Color(0xFF44ABB9)

var print_color_1 = Color(0xFF83E6E9)
var print_color_2 = Color(0xFF7BB944)

var bottom_bar_color = Color(0xFFA7C3FF)

var drawer_content_color = Color(0xFF090F1D)

var color_on_change_theme1 = Color(0xFF000000)
var color_on_change_theme2 = Color(0xFF0036B3)

var screen = mutableStateOf(R.drawable.screen_light)

fun DarkTheme() {
    color_on_change_theme1 = Color(0xFFFFFFFF)
    color_on_change_theme2 = Color(0xFFFCDD1F)
    bottom_bar_color = Color(0xFF213152)
    screen.value = R.drawable.screen_dark
}

fun LightTheme() {
    color_on_change_theme1 = Color(0xFF000000)
    color_on_change_theme2 = Color(0xFF0036B3)
    bottom_bar_color = Color(0xFFA7C3FF)
    screen.value = R.drawable.screen_light
}
