package org.example.app.ui

import android.graphics.Color

/**
 * PUBLIC_INTERFACE
 * Light theme constants for the app UI.
 */
object Theme {
    // Hex colors specified in the task
    const val PRIMARY_HEX = "#3b82f6"
    const val SUCCESS_HEX = "#06b6d4"
    const val SECONDARY_HEX = "#64748b"
    const val BACKGROUND_HEX = "#f9fafb"
    const val SURFACE_HEX = "#ffffff"
    const val TEXT_HEX = "#111827"
    const val ERROR_HEX = "#EF4444"

    val primary = Color.parseColor(PRIMARY_HEX)
    val success = Color.parseColor(SUCCESS_HEX)
    val secondary = Color.parseColor(SECONDARY_HEX)
    val background = Color.parseColor(BACKGROUND_HEX)
    val surface = Color.parseColor(SURFACE_HEX)
    val text = Color.parseColor(TEXT_HEX)
    val error = Color.parseColor(ERROR_HEX)
}
