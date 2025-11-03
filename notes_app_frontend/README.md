# Simple Notes App (Android preview)

This module now implements a simple notes app with:
- List of notes sorted by last updated time
- Create, edit, and delete notes
- Local persistence using a JSON file stored in app-private storage
- Light theme accents:
  - Primary: #3b82f6
  - Success: #06b6d4
  - Secondary: #64748b

Build:
- ./gradlew :app:assembleDebug
  - This assembles the debug APK without requiring a connected device/emulator.
- Optional: ./gradlew :app:bundleDebug (to build an AAB)
- Full build: ./gradlew build

Run on a device/emulator (optional, requires device):
- ./gradlew :app:installDebug
- Open "Simple Notes" on the device/emulator.

Usage:
- Tap + to add a note.
- Tap a note to edit it.
- Long press a note to delete. You can also delete inside the editor screen.

Persistence:
- Notes are saved to filesDir/notes.json. On first run, a welcome note is seeded.
