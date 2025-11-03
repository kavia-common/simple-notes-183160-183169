package org.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Entry activity that forwards to the Notes list screen.
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, NotesListActivity::class.java))
        finish()
    }
}
