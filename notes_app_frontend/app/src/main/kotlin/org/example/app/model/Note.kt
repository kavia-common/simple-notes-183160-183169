package org.example.app.model

import java.util.UUID

/**
 * PUBLIC_INTERFACE
 * Data model representing a Note.
 */
data class Note(
    /** Stable unique ID for the note. */
    val id: String = UUID.randomUUID().toString(),
    /** Title of the note (can be empty). */
    var title: String = "",
    /** Body/content of the note. */
    var content: String = "",
    /** Last updated timestamp millis. Used for sorting. */
    var updatedAt: Long = System.currentTimeMillis()
)
