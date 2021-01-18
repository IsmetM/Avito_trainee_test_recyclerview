package ru.skillbranch.avito_intern_test.utils

import android.content.res.Configuration

object Utils {

    private val TWO_COLUMNS = 2
    private val FOUR_COLUMNS = 4

    fun getColumnCount(orientation: Int) =
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> TWO_COLUMNS
            Configuration.ORIENTATION_LANDSCAPE -> FOUR_COLUMNS
            else -> TWO_COLUMNS
        }
}