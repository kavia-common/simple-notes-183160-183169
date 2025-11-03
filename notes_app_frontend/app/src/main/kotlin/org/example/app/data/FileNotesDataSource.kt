package org.example.app.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import org.example.app.model.Note
import java.io.File
import java.nio.charset.Charset

/**
 * PUBLIC_INTERFACE
 * A simple file-based data source for persisting notes as JSON.
 */
class FileNotesDataSource(private val context: Context) {

    private val fileName = "notes.json"

    private fun notesFile(): File {
        return File(context.filesDir, fileName)
    }

    /**
     * PUBLIC_INTERFACE
     * Loads all notes from disk. If the store is empty, returns a seeded list.
     */
    fun loadNotes(): MutableList<Note> {
        val f = notesFile()
        if (!f.exists()) {
            // Seed initial note on first run
            val seeded = mutableListOf(
                Note(
                    title = "Welcome to Notes",
                    content = "Tap the + button to add a new note.\nTap a note to edit it.\nSwipe left to delete.",
                    updatedAt = System.currentTimeMillis()
                )
            )
            saveNotes(seeded)
            return seeded
        }
        return try {
            val text = f.readText(Charset.forName("UTF-8"))
            if (text.isBlank()) return mutableListOf()
            val arr = JSONArray(text)
            val list = mutableListOf<Note>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                list.add(
                    Note(
                        id = o.optString("id"),
                        title = o.optString("title"),
                        content = o.optString("content"),
                        updatedAt = o.optLong("updatedAt", System.currentTimeMillis())
                    )
                )
            }
            list
        } catch (e: Exception) {
            // Basic error handling: start fresh if corrupted
            mutableListOf()
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Saves all notes to disk, overwriting the file atomically.
     */
    fun saveNotes(notes: List<Note>) {
        val arr = JSONArray()
        notes.forEach { n ->
            val o = JSONObject()
                .put("id", n.id)
                .put("title", n.title)
                .put("content", n.content)
                .put("updatedAt", n.updatedAt)
            arr.put(o)
        }
        val f = notesFile()
        // Write atomically to reduce corruption risk
        val tmp = File(f.parentFile, "${f.name}.tmp")
        tmp.writeText(arr.toString(), Charsets.UTF_8)
        if (f.exists()) f.delete()
        tmp.renameTo(f)
    }
}
