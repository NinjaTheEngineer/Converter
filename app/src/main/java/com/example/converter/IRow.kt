package com.example.converter

import android.graphics.drawable.Drawable

interface IRow {
    class ThemeRow(val theme:String, val image: Drawable) : IRow
    class Ingredient(val name: String, val density: Double) : IRow
}