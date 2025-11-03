package org.example.app.data

import android.content.Context
import org.example.app.model.Note

/**
 * PUBLIC_INTERFACE
 * Repository for managing notes and persistence.
 */
class NotesRepository(context: Context) {
    private val ds = FileNotesDataSource(context)
    private var cache: MutableList<Note> = ds.loadNotes()

    /**
     * PUBLIC_INTERFACE
     * Returns a copy of all notes, sorted by updatedAt desc.
     */
    fun getAll(): List<Note> {
        return cache.sortedByDescending { it.updatedAt }
    }

    /**
     * PUBLIC_INTERFACE
     * Gets a note by id or null.
     */
    fun getById(id: String): Note? = cache.find { it.id == id }

    /**
     * PUBLIC_INTERFACE
     * Adds or updates a note. Returns the saved instance.
     */
    fun upsert(note: Note): Note {
        val existingIdx = cache.indexOfFirst { it.id == note.id }
        note.updatedAt = System.currentTimeMillis()
        if (existingIdx >= 0) {
            cache[existingIdx] = note
        } else {
            cache.add(note)
        }
        ds.saveNotes(cache)
        return note
    }

    /**
     * PUBLIC_INTERFACE
     * Deletes a note by id. Returns true if deleted.
     */
    fun delete(id: String): Boolean {
        val removed = cache.removeIf { it.id == id }
        if (removed) ds.saveNotes(cache)
        return removed
    }
}
