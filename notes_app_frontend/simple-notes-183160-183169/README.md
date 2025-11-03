# simple-notes-183160-183169

This workspace contains an Android preview implementation of a simple notes app under notes_app_frontend. It simulates the requested iOS notes app functionality in the available environment.

Features:
- List notes sorted by last updated time
- Create, edit, and delete notes
- Local JSON persistence in app-private storage
- Light theme accents (Primary #3b82f6, Success #06b6d4, Secondary #64748b)

Build (no device required):
- ./gradlew :app:assembleDebug
  - Produces APK at app/build/outputs/apk/debug/app-debug.apk
- Optional: ./gradlew :app:bundleDebug
- Full build: ./gradlew build

Run on a device/emulator (optional):
- ./gradlew :app:installDebug
- Launch "Simple Notes" on the connected device/emulator.
