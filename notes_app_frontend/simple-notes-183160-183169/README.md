# simple-notes-183160-183169

This workspace contains an Android preview implementation of a simple notes app under notes_app_frontend. It simulates the requested iOS notes app functionality in the available environment.

Features:
- List notes sorted by last updated time
- Create, edit, and delete notes
- Local JSON persistence in app-private storage
- Light theme accents (Primary #3b82f6, Success #06b6d4, Secondary #64748b)

Build:
- ./gradlew build

Run:
- ./gradlew :app:installDebug
- Launch "Simple Notes" on the connected device/emulator.
