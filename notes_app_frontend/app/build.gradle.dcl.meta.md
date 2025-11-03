# Build notes for CI/Preview

- Default build task for preview should be `:app:assembleDebug`.
- Avoid using `installDebug` in CI as it requires a connected device/emulator.
- Use `./gradlew :app:assembleDebug` to succeed preview readiness checks.

This file documents the intended CI task behavior for the preview environment.
