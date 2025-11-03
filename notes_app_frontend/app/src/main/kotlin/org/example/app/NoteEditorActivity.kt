package org.example.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.example.app.data.NotesRepository
import org.example.app.model.Note
import org.example.app.ui.Theme
import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * Editor screen for creating or editing a note.
 * Intent extras:
 * - EXTRA_NOTE_ID: String? -> if provided, loads existing note for editing
 */
class NoteEditorActivity : Activity() {

    companion object {
        const val EXTRA_NOTE_ID = "note_id"
    }

    private lateinit var repo: NotesRepository
    private var current: Note? = null

    private lateinit var inputTitle: EditText
    private lateinit var inputContent: EditText
    private lateinit var btnSave: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnDelete: Button
    private lateinit var titleBar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        window.statusBarColor = Theme.primary

        repo = NotesRepository(this)

        inputTitle = findViewById(R.id.inputTitle)
        inputContent = findViewById(R.id.inputContent)
        btnSave = findViewById(R.id.btnSave)
        btnBack = findViewById(R.id.btnBack)
        btnDelete = findViewById(R.id.btnDelete)
        titleBar = findViewById(R.id.editorTitleBar)

        val id = intent.getStringExtra(EXTRA_NOTE_ID)
        if (id != null) {
            val note = repo.getById(id)
            if (note != null) {
                current = note.copy()
                inputTitle.setText(current!!.title)
                inputContent.setText(current!!.content)
                titleBar.text = getString(R.string.edit_note)
                btnDelete.visibility = View.VISIBLE
            } else {
                // If missing, treat as new
                current = null
                btnDelete.visibility = View.GONE
            }
        } else {
            current = null
            btnDelete.visibility = View.GONE
        }

        btnBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveNote() }
        btnDelete.setOnClickListener { deleteNote() }
    }

    private fun saveNote() {
        val title = inputTitle.text?.toString()?.trim().orEmpty()
        val content = inputContent.text?.toString()?.trim().orEmpty()

        // Even empty notes can be saved; it's a basic app.
        val toSave = current?.apply {
            this.title = title
            this.content = content
        } ?: Note(title = title, content = content)

        try {
            repo.upsert(toSave)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.error_save), Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteNote() {
        val c = current ?: run { finish(); return }
        try {
            val ok = repo.delete(c.id)
            if (!ok) {
                Toast.makeText(this, getString(R.string.error_delete), Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        } catch (_: Exception) {
            Toast.makeText(this, getString(R.string.error_delete), Toast.LENGTH_SHORT).show()
        }
    }
}
