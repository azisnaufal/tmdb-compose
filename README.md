# TMDB - Kotlin Multiplatform Project

This project is a mini app as PoC (Proof of Concept) Kotlin Multiplatform (KMP) application targeting **Android** and **iOS**, using **Compose Multiplatform** for a shared UI across both platforms. 

## Project Overview
The project is structured to share as much code as possible, including business logic and UI components, while allowing for platform-specific implementations where necessary.

- **Main Technologies:**
  - **Language:** [Kotlin](https://kotlinlang.org/) (2.1.0)
  - **Shared UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (1.7.3)
  - **Android:** Android SDK, Jetpack Compose integration.
  - **iOS:** SwiftUI entry point, Kotlin-to-Swift interoperability.
  - **Build System:** Gradle (Kotlin DSL) with Version Catalogs.

## Architecture
The project follows the standard KMP structure:

- `composeApp/src/commonMain`: Contains the core logic, domain models, and shared Compose UI components (e.g., `App.kt`).
- `composeApp/src/androidMain`: Android-specific code, including `MainActivity` and platform-specific implementations of shared interfaces.
- `composeApp/src/iosMain`: iOS-specific code, providing the `MainViewController` to be used by the iOS application.
- `iosApp/`: A standard Xcode project/SwiftUI application that serves as the entry point for the iOS platform, hosting the shared Compose UI.

## Building and Running

### Prerequisites
- [JDK 17+](https://adoptium.net/)
- [Android Studio](https://developer.android.com/studio) or IntelliJ IDEA with the KMP plugin.
- [Xcode](https://developer.apple.com/xcode/) (required for iOS development).

### Android
To build and run the Android application:
```bash
./gradlew :composeApp:assembleDebug
```
Alternatively, use the **composeApp** run configuration in your IDE.

### iOS
To run the iOS application:
1. Open the `iosApp/iosApp.xcodeproj` in Xcode.
2. Select a simulator or physical device.
3. Click **Run**.

Alternatively, you can use the Gradle task if configured, but Xcode is the primary way to manage iOS-specific configurations.

## Development Conventions
- **Shared First:** Always prefer writing code in `commonMain` whenever possible.
- **Platform Specifics:** Use the `expect`/`actual` mechanism or interfaces with platform-specific implementations in `androidMain` and `iosMain` for features that require platform APIs.
- **Resources:** Use `compose-components-resources` for shared images, fonts, and strings, located in `composeApp/src/commonMain/composeResources`.
- **Dependency Management:** All dependencies are managed in `gradle/libs.versions.toml`.

