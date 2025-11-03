package org.example.app

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import org.example.app.data.NotesRepository
import org.example.app.model.Note
import org.example.app.ui.Theme
import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * Displays the list of notes with ability to add, edit and delete.
 */
class NotesListActivity : Activity() {

    private lateinit var repo: NotesRepository
    private lateinit var listView: ListView
    private lateinit var emptyView: TextView
    private lateinit var addBtn: ImageButton
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        window.statusBarColor = Theme.primary

        repo = NotesRepository(this)
        listView = findViewById(R.id.notesListView)
        emptyView = findViewById(R.id.emptyView)
        addBtn = findViewById(R.id.btnAdd)

        adapter = NotesAdapter()
        listView.adapter = adapter
        registerForContextMenu(listView)

        listView.setOnItemClickListener { _, _, position, _ ->
            val note = adapter.getItem(position)
            val intent = Intent(this, NoteEditorActivity::class.java)
            intent.putExtra(NoteEditorActivity.EXTRA_NOTE_ID, note.id)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteDialog(adapter.getItem(position))
            true
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, NoteEditorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val notes = repo.getAll()
        adapter.setItems(notes)
        emptyView.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun showDeleteDialog(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Delete note")
            .setMessage("Are you sure you want to delete \"${note.title.ifBlank { "Untitled" }}\"?")
            .setPositiveButton("Delete") { _, _ ->
                val ok = repo.delete(note.id)
                if (!ok) {
                    Toast.makeText(this, getString(R.string.error_delete), Toast.LENGTH_SHORT).show()
                } else {
                    refresh()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private inner class NotesAdapter : BaseAdapter() {
        private var items: List<Note> = emptyList()

        fun setItems(n: List<Note>) {
            items = n
            notifyDataSetChanged()
        }

        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Note = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.item_note, parent, false)
            val title = view.findViewById<TextView>(R.id.noteTitle)
            val preview = view.findViewById<TextView>(R.id.notePreview)
            val n = getItem(position)
            title.text = n.title.ifBlank { "Untitled" }
            val firstLine = n.content.trim().lineSequence().firstOrNull().orEmpty()
            preview.text = firstLine
            return view
        }
    }
}
