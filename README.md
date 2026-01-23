# Punch-hole Download Progress

Xposed module that displays download progress as an animated ring around the camera cutout. Hooks into SystemUI notifications to monitor downloads from browsers and the system download manager.

![Android CI](https://github.com/hxreborn/punch-hole-download-progress/actions/workflows/android-ci.yml/badge.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/API-31%2B-3DDC84?logo=android&logoColor=white)

<div align="center">
  <img src=".github/assets/demo.png" alt="Download progress ring around camera cutout" width="320" />
</div>

## Features

- Progress ring around camera cutout using Display Cutout API
- Tracks Download Manager and major browsers (Chrome, Firefox, Brave, Edge, etc.)
- Multiple concurrent downloads with highest-progress priority
- Customizable ring thickness, spacing, opacity, and color
- Completion animations: snap, pop, segmented (with optional pulse)
- Minimum visibility threshold for fast downloads
- Battery Saver integration: dim or disable when active

## Requirements

- Android 12+
- [LSPosed](https://github.com/JingMatrix/LSPosed)
- Device with camera cutout

## Installation

1. Install and enable module in LSPosed
2. Scope to `com.android.systemui`
3. Reboot

## Build

```bash
git clone --recurse-submodules https://github.com/hxreborn/punch-hole-download-progress.git
cd punch-hole-download-progress
./gradlew buildLibxposedApi
./gradlew assembleRelease
```

Requires JDK 21 and Android SDK. Configure `local.properties`:

```properties
sdk.dir=/path/to/android/sdk

# Optional signing
RELEASE_STORE_FILE=<path/to/keystore.jks>
RELEASE_STORE_PASSWORD=<store_password>
RELEASE_KEY_ALIAS=<key_alias>
RELEASE_KEY_PASSWORD=<key_password>
```

## License

<a href="LICENSE"><img src=".github/assets/gplv3.svg" height="90" alt="GPLv3"></a>

This project is licensed under the GNU General Public License v3.0 – see the [LICENSE](LICENSE) file for details.
