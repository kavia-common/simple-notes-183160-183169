package org.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle

// PUBLIC_INTERFACE
/**
 * Entry activity that forwards to the Notes list screen.
 *
 * This is the LAUNCHER activity defined in AndroidManifest. It immediately
 * redirects to NotesListActivity and finishes so the back stack is clean.
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, NotesListActivity::class.java))
        finish()
    }
}
