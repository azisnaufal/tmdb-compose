# Netflix Design System (Hawkins) Integration

This document defines the design standards for the TMDB project, following the Netflix "Hawkins" design system. All UI development must follow these guidelines to maintain a cinematic, immersive, and consistent user experience.

> **Reference:** Netflix's internal design system is called **Hawkins**, built on a **design token foundation**. Their internal styleguide (Honeycomb) defines tokens for colors, typography, spacing, and elevation. This document adapts those principles for Compose Multiplatform.

---

## 1. Core Philosophy

| Principle | Description |
| :--- | :--- |
| **Content First** | UI should feel like a theater — dark, unobtrusive, focused on high-quality visuals. |
| **Cinematic Immersion** | Large imagery, bold typography, minimal chrome. The interface disappears behind the content. |
| **Dark-Only** | No light mode. Dark backgrounds reduce eye strain in viewing contexts and make content pop. |
| **Progressive Disclosure** | Show minimal info by default (poster + title). Reveal details on interaction (hover/focus/expand). |
| **Consistency via Tokens** | All visual decisions (color, spacing, type, elevation) flow from named design tokens, not hardcoded values. |

---

## 2. Design Tokens

### 2.1 Color Palette

**Dark Mode Only.** No light theme.

| Token Name | Hex Code | Opacity | Usage |
| :--- | :--- | :--- | :--- |
| `color.red.primary` | `#E50914` | 100% | Logos, primary CTA, active states, progress bars, error states |
| `color.red.hover` | `#F40612` | 100% | Primary button hover state |
| `color.red.muted` | `#E50914` | 60% | Subtle red accents, badges |
| `color.black.canvas` | `#000000` | 100% | Main background (canvas) |
| `color.gray.dark` | `#141414` | 100% | Secondary backgrounds, cards, nav bar, surface elevation 1 |
| `color.gray.surface` | `#1A1A1A` | 100% | Elevated cards, modals, surface elevation 2 |
| `color.gray.medium` | `#333333` | 100% | Borders, dividers, subtle separators |
| `color.gray.placeholder` | `#4D4D4D` | 100% | Placeholder images, empty states |
| `color.gray.muted` | `#808080` | 100% | Secondary text, inactive states, metadata labels |
| `color.gray.dim` | `#999999` | 100% | Tertiary text, timestamps |
| `color.gray.hover` | `#B3B3B3` | 100% | Text hover states |
| `color.white.primary` | `#FFFFFF` | 100% | Primary text, high-contrast actions |
| `color.white.overlay` | `#FFFFFF` | 70% | Semi-transparent overlays, disabled text |
| `color.overlay.dark` | `#000000` | 70% | Modal backdrops, image overlays |
| `color.overlay.gradient` | `#000000` | 0%→100% | Vertical fade on billboard and card images |

#### Semantic Color Mapping

| Context | Token |
| :--- | :--- |
| Screen background | `color.black.canvas` |
| Card/surface background | `color.gray.dark` |
| Elevated surface (modal, dropdown) | `color.gray.surface` |
| Primary text | `color.white.primary` |
| Secondary text | `color.gray.muted` |
| Metadata text | `color.gray.dim` |
| Border/divider | `color.gray.medium` |
| Error/danger | `color.red.primary` |
| Success | `#46D369` (Netflix green) |
| Star rating | `#F5C518` (IMDB gold) |

---

### 2.2 Typography

Netflix uses **Netflix Sans** (proprietary). For this project, use clean geometric sans-serif fallbacks: **Inter** (preferred), Roboto, or System Sans.

#### Type Scale

| Role | Weight | Size (dp) | Letter Spacing | Line Height | Usage |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `display` | Bold (700) | 32-40 | -0.02em | 1.1 | Billboard title, hero text |
| `headline` | Bold (700) | 24-28 | -0.01em | 1.2 | Section headers, row titles |
| `title` | SemiBold (600) | 18-20 | 0 | 1.3 | Card titles, dialog headings |
| `body` | Regular (400) | 14-16 | 0 | 1.5 | Descriptions, content text |
| `caption` | Medium (500) | 12 | 0.01em | 1.4 | Metadata (year, rating, duration) |
| `overline` | Medium (500) | 10 | 0.08em | 1.5 | Category labels, tags, badges |
| `button` | Bold (700) | 14 | 0.02em | 1.0 | Button labels, CTAs |

#### Compose Typography Tokens

```kotlin
val NetflixTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.02).sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.01).sp,
        lineHeight = 30.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_semibold)),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.01.sp,
        lineHeight = 16.sp
    )
)
```

---

### 2.3 Spacing

Base unit: **4dp**. All spacing must be multiples of 4.

| Token | Value | Usage |
| :--- | :--- | :--- |
| `space.none` | 0dp | — |
| `space.xxs` | 2dp | Icon-to-text gap, tight inline spacing |
| `space.xs` | 4dp | Minimal padding, tag inner padding |
| `space.sm` | 8dp | Compact card padding, list item gaps |
| `space.md` | 12dp | Card inner padding, form field gaps |
| `space.base` | 16dp | Standard padding, content margins, button padding |
| `space.lg` | 24dp | Section gaps, larger card padding |
| `space.xl` | 32dp | Row spacing, section top/bottom margins |
| `space.2xl` | 48dp | Page-level margins, billboard content offset |
| `space.3xl` | 64dp | Hero section padding |

#### Spacing Rules
- Screen edge margin: `space.base` (16dp) on mobile, `space.2xl` (48dp) on TV/desktop
- Between content rows: `space.xl` (32dp)
- Between items in a row: `space.xs` (4dp)
- Card inner padding: `space.md` (12dp)
- Button horizontal padding: `space.base` (16dp), vertical: `space.sm` (8dp)

---

### 2.4 Elevation & Shadows

Elevation communicates depth hierarchy. Netflix uses subtle elevation — content speaks louder than shadows.

| Level | Token | Shadow | Z-Index | Usage |
| :--- | :--- | :--- | :--- | :--- |
| 0 | `elevation.none` | None | 0 | Flat cards at rest |
| 1 | `elevation.low` | `0 2dp 4dp rgba(0,0,0,0.4)` | 1 | Hovered card, dropdown |
| 2 | `elevation.medium` | `0 4dp 12dp rgba(0,0,0,0.5)` | 2 | Expanded card, popover |
| 3 | `elevation.high` | `0 8dp 24dp rgba(0,0,0,0.6)` | 3 | Modal, bottom sheet |
| 4 | `elevation.top` | `0 4dp 16dp rgba(0,0,0,0.5)` | 10 | Navigation bar (sticky) |

#### Compose Elevation

```kotlin
// Card at rest
Card(elevation = CardDefaults.cardElevation(defaultElevation = 0.dp))

// Card on hover
Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp))
```

---

### 2.5 Border Radius

| Token | Value | Usage |
| :--- | :--- | :--- |
| `radius.none` | 0dp | Full-bleed images, billboard |
| `radius.sm` | 4dp | Small buttons, tags, badges |
| `radius.md` | 8dp | Cards, inputs, dialog corners |
| `radius.lg` | 12dp | Modals, bottom sheets, large cards |
| `radius.full` | 50% (or 999dp) | Avatar, circular icons, FAB |

---

### 2.6 Iconography

| Property | Value |
| :--- | :--- |
| Style | Outlined / Filled (Material Symbols) |
| Default size | 24dp |
| Small size | 16dp (inline, metadata) |
| Large size | 32dp (CTA icons) |
| Color | Inherits parent text color (`LocalContentColor.current`) |

---

## 3. Signature Components

### A. The Billboard (Hero Section)

The billboard is the most prominent UI element — a full-bleed hero showcasing featured content.

| Property | Specification |
| :--- | :--- |
| Height | 70-80% of viewport (mobile: ~400dp, TV: ~600dp) |
| Background | High-res backdrop image (`780px` or `original` from TMDB) |
| Gradient | `linear-gradient(to top, #000 0%, #000 15%, transparent 50%, transparent 100%)` — bottom fade blends into content |
| Left gradient | `linear-gradient(to right, #000 30%, transparent 60%)` — text readability on left side |
| Content position | Bottom-left, offset `space.2xl` (48dp) from edges |
| Title | `display` typography, max 2 lines, ellipsis overflow |
| Description | `body` typography, max 3 lines, 60% width, `color.white.overlay` |
| Metadata | Genre tags + rating + year in `caption` style |
| Play button | 48dp height, white bg, black icon + text, `radius.sm` |
| More Info button | 48dp height, `color.gray.medium` bg at 70%, white icon + text, `radius.sm` |
| Maturity rating badge | Border-only pill, `radius.full`, `color.gray.muted` text, `space.xs` padding |

#### Compose Reference

```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(billboardHeight)
) {
    // Backdrop image
    AsyncImage(
        model = backdropUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    // Bottom gradient overlay
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .align(Alignment.BottomCenter)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color(0xFF000000))
                )
            )
    )
    // Content
    Column(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(horizontal = 48.dp, vertical = 32.dp)
    ) {
        Text(title, style = NetflixTypography.displayLarge)
        Spacer(Modifier.height(8.dp))
        Text(overview, style = NetflixTypography.bodyLarge,
             maxLines = 3, overflow = TextOverflow.Ellipsis,
             color = Color.White.copy(alpha = 0.7f))
        Spacer(Modifier.height(16.dp))
        Row { /* Play + More Info buttons */ }
    }
}
```

---

### B. Content Rows (Carousels)

Horizontal scrolling rows of content cards — the primary browsing pattern.

| Property | Specification |
| :--- | :--- |
| Row title | `headline` typography, `color.white.primary`, left-aligned |
| Row title position | `space.base` (16dp) left margin, `space.sm` (8dp) below title to cards |
| Card width | Mobile: 100dp, Tablet: 130dp, TV/Desktop: 185dp |
| Card aspect ratio | `2:3` for movie posters, `16:9` for thumbnails/episodes |
| Card gap | `space.xs` (4dp) between cards |
| Scroll behavior | Snap-to-center paging, hide scrollbar |
| Peek cards | Show partial next/prev card (8-16dp visible) to indicate scrollability |
| Left/right fade | Subtle gradient fade on edges to indicate more content |

#### Card Hover/Focus State

| Phase | Behavior |
| :--- | :--- |
| **Rest** | No elevation, no scale, `radius.md` corners |
| **Hover/Focus** | `scale(1.08)`, `elevation.medium` shadow, z-index raised, 200ms ease-in-out transition |
| **Expanded** (optional) | Card expands to show: thumbnail preview, title, metadata (year, rating, match%), genre tags, play/add-to-list buttons. Appears as a dropdown below the card. |

```kotlin
val scale by animateFloatAsState(
    targetValue = if (isHovered) 1.08f else 1f,
    animationSpec = tween(durationMillis = 200, easing = EaseInOut)
)

Card(
    modifier = Modifier
        .width(cardWidth)
        .aspectRatio(2f / 3f)
        .scale(scale)
        .shadow(if (isHovered) 12.dp else 0.dp),
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(
        defaultElevation = if (isHovered) 4.dp else 0.dp
    )
) {
    // Poster image + gradient overlay at bottom
}
```

---

### C. Buttons

| Variant | Background | Text Color | Border | Icon | Radius | Height |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Primary** | `#E50914` | `#FFFFFF` | None | Optional left icon | `radius.sm` (4dp) | 40-48dp |
| **Secondary** | `#333333` @ 70% | `#FFFFFF` | None | Optional left icon | `radius.sm` (4dp) | 40-48dp |
| **Ghost** | Transparent | `#FFFFFF` | `1dp #808080` | Optional left icon | `radius.sm` (4dp) | 40-48dp |
| **Play (Billboard)** | `#FFFFFF` | `#000000` | None | Play icon left | `radius.sm` (4dp) | 48dp |
| **Icon Only** | `#333333` @ 50% | `#FFFFFF` | `1dp #808080` | Center icon (circular) | `radius.full` | 40dp |
| **Tag/Chip** | Transparent | `#FFFFFF` | `1dp #808080` | None | `radius.full` | 32dp |

#### Button States

| State | Change |
| :--- | :--- |
| **Default** | As specified above |
| **Hovered** | Background lightens by 10%, subtle glow |
| **Pressed** | Scale to `0.97`, background darkens slightly |
| **Disabled** | `alpha = 0.4`, no interaction |
| **Focused (TV)** | 2dp white outline, slight scale up |

---

### D. Navigation Bar

| Property | Specification |
| :--- | :--- |
| Position | Fixed top, full width |
| Background | `#000000` with 80-90% opacity (blur behind if supported) |
| Height | 56dp (mobile), 64dp (TV/desktop) |
| Content | Left: Logo, Center: Nav links (Home, TV Shows, Movies, My List), Right: Search + Profile |
| Scrolled state | Background becomes `#141414` solid, `elevation.top` shadow |
| Active link | `color.white.primary`, bold |
| Inactive link | `color.gray.muted` → `color.white.primary` on hover |
| Logo | Netflix wordmark, `color.red.primary`, 100dp width |

---

### E. Detail/Info Screen

| Property | Specification |
| :--- | :--- |
| Layout | Full-screen, scrollable |
| Hero | 50% viewport height, backdrop image with bottom + left gradient overlays |
| Content offset | Content starts overlaid on hero image bottom |
| Title | `display` typography |
| Metadata row | Rating badge + Year + Duration + Maturity rating, `caption` style, inline with dots as separators |
| Genre tags | Horizontal `Tag/Chip` components |
| Overview | `body` typography, `color.white.overlay`, max width 600dp |
| Action buttons | Same row: Play + Add to List + Thumbs Up + Share (icon buttons) |
| Cast section | Horizontal scroll, circular avatars (80dp) + name below |
| Similar content | Standard content row below |

---

### F. Search

| Property | Specification |
| :--- | :--- |
| Trigger | Search icon in nav bar, expands to input field |
| Input field | Full nav height, `color.gray.dark` background, white text, no visible border |
| Placeholder | "Titles, people, genres" in `color.gray.muted` |
| Results | Grid layout (3-5 columns), poster cards, same as content row cards |
| Empty state | "No results found" centered, `color.gray.muted` |
| Recent searches | Shown as vertical list with clock icon |

---

### G. Loading States

| State | Specification |
| :--- | :--- |
| **Skeleton/Shimmer** | Rectangular placeholder matching card dimensions, `color.gray.dark` fill, animated shimmer sweep (left-to-right, white at 10% opacity) |
| **Spinner** | Circular `CircularProgressIndicator`, `color.red.primary`, 32dp |
| **Image loading** | Placeholder: `color.gray.placeholder` fill with centered icon |

---

## 4. Responsive Breakpoints

| Breakpoint | Width | Columns | Card Width | Row Cards Visible | Screen Edge Margin |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Mobile** | < 600dp | 1 | 100dp | 3-4 peek | 16dp |
| **Tablet** | 600-839dp | 2 | 130dp | 4-5 | 24dp |
| **Desktop** | 840-1199dp | 3 | 150dp | 5-6 | 48dp |
| **TV/Large** | ≥ 1200dp | 4+ | 185dp | 6-7 | 48dp |

---

## 5. Motion & Animation

| Animation | Duration | Easing | Usage |
| :--- | :--- | :--- | :--- |
| Card scale (hover) | 200ms | Ease-in-out | Content row card hover |
| Card expand | 300ms | Ease-out | Card detail expansion |
| Page transition | 300ms | Ease-in-out | Screen navigation |
| Shimmer sweep | 1500ms | Linear, repeating | Skeleton loading |
| Fade in/out | 200-300ms | Ease-in-out | Image loading, overlay appearance |
| Nav bar background | 200ms | Ease-in-out | Scroll-triggered opacity change |
| Button press scale | 100ms | Ease-in | Press feedback |
| Snackbar slide | 300ms | Ease-out | Bottom notification |

```kotlin
// Standard transition spec
val NetflixTween = tween<Float>(
    durationMillis = 200,
    easing = EaseInOut
)

// Shimmer animation
val shimmerTransition = rememberInfiniteTransition()
val shimmerTranslate = shimmerTransition.animateFloat(
    initialValue = -300f,
    targetValue = 300f,
    animationSpec = infiniteRepeatable(
        animation = tween(durationMillis = 1500, easing = LinearEasing)
    )
)
```

---

## 6. Compose Multiplatform Theme Setup

```kotlin
// Color tokens
val NetflixColors = darkColorScheme(
    primary = Color(0xFFE50914),
    onPrimary = Color.White,
    secondary = Color(0xFF333333),
    onSecondary = Color.White,
    background = Color(0xFF000000),
    onBackground = Color.White,
    surface = Color(0xFF141414),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1A1A1A),
    onSurfaceVariant = Color(0xFF999999),
    outline = Color(0xFF333333),
    error = Color(0xFFE50914)
)

// Theme wrapper
@Composable
fun NetflixTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NetflixColors,
        typography = NetflixTypography,
        content = content
    )
}
```

---

## 7. Accessibility

| Requirement | Implementation |
| :--- | :--- |
| **Touch target** | Minimum 48dp x 48dp for all interactive elements |
| **Color contrast** | All text must meet WCAG AA (4.5:1 for body, 3:1 for large text) |
| **Focus indicators** | 2dp white outline on focused elements (TV/keyboard navigation) |
| **Content descriptions** | All images and icons must have `contentDescription` |
| **Font scaling** | Support system font scaling up to 1.5x without layout break |
| **Reduce motion** | Respect `shouldUseLtrLayout` / system reduce-motion preference — disable animations when requested |
