# Project Rules - TMDB (Kotlin Multiplatform)

## Language & Framework
- Kotlin Multiplatform (KMP) with Compose Multiplatform
- Targets: Android, iOS (arm64, simulator)
- Compose Material3 for UI
- Lifecycle ViewModel for state management

## Code Organization
```
composeApp/src/
├── commonMain/       # Shared code for all platforms
│   ├── kotlin/       # Business logic, UI components, viewmodels
│   └── composeResources/ # Images, strings, fonts
├── androidMain/      # Android-specific code (services, permissions)
├── iosMain/          # iOS-specific code (native APIs, platform integrations)
└── commonTest/       # Shared tests
```

## Naming Conventions
- Packages: `com.oazisn.tmdb.feature.*`
- ViewModels: `*ViewModel.kt`
- Composable functions: `*Composable()` or camelCase with `@Composable`
- Data classes: `*Data.kt`
- Sealed classes/enums: Plural names (`NavigationRoute`, `UiState`)

## Architecture
- **MVVM pattern**: UI (Composables) → ViewModel → Repository/Data Layer
- **Unidirectional data flow**: UI observes State, emits Events
- **State management**: Use `remember` for local state, `ViewModel` for screen-level state
- **State management**: Screen-level state should use sealed class for state management

## Compose Guidelines
- Prefer `@Composable` functions over classes
- Use `Material3` components (`Card`, `Button`, `TextField`)
- Separate UI state from business logic
- Keep composables small and focused
- Use Navigation3 Library for navigation between screen 

## Theme & Design
- **Dark Mode Only** - Netflix Hawkins design system
- Wrap root composable with `NetflixTheme` from `theme/NetflixTheme.kt`
- Use theme colors via `MaterialTheme.colorScheme`
- See `DESIGN.md` for complete design specifications (colors, typography, components)

## Platform-Specific Code
- Use `expect/actual` declarations for platform APIs
- Android specifics go in `androidMain/kotlin`
- iOS specifics go in `iosMain/kotlin`
- Keep platform code minimal; implement interfaces in common layer

## Dependencies
- Use `libs.versions.toml` for version catalog
- Add shared deps to `commonMain.dependencies`
- Add platform-specific deps to respective source sets

## TMDB API Specifics
- API Base URL: `https://api.themoviedb.org/3/`
- Include API Key in requests (`api_key` query param)
- Use `Ktor` for HTTP client (commonMain)
- Cache responses locally with `Room`

## Build Commands
```bash
# Android
./gradlew :composeApp:assembleDebug

# iOS
open iosApp/iosApp.xcodeproj
# Build in Xcode
```

## Testing
- Write tests in `commonTest/`
- Use `kotlin.test` framework
- Mock network responses for unit tests

## Git Commit Conventions
- `feat: add feature description`
- `fix: bug fix description`
- `refactor: code restructuring`
- `docs: documentation updates`
