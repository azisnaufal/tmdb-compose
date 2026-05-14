package com.oazisn.tmdb.screen.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.oazisn.tmdb.component.MainComponent
import com.oazisn.tmdb.component.MainViewState
import com.oazisn.tmdb.screen.components.ErrorState
import com.oazisn.tmdb.screen.components.GenreSection
import com.oazisn.tmdb.screen.components.LoadingIndicator
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.subscribeAsState()
    val density = LocalDensity.current
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }
    val navigationBarHeight = with(density) { WindowInsets.navigationBars.getTop(density).toDp() }

    Box(modifier = Modifier.fillMaxSize().background(NetflixColors.BlackCanvas)) {
        when (val s = state) {
            is MainViewState.Loading -> {
                LoadingIndicator(modifier = Modifier.fillMaxSize())
            }
            is MainViewState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Spacer(modifier = Modifier.height(statusBarHeight + TopBarHeight))
                        }
                        if (s.data.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No results found",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = NetflixColors.GrayMuted
                                    )
                                }
                            }
                        } else {
                            items(s.data, key = { it.genre.id }) { genreWithMovies ->
                                GenreSection(
                                    genreWithMovies = genreWithMovies,
                                    onMovieClick = { component.onMovieClick(it) }
                                )
                                Spacer(modifier = Modifier.height(NetflixSpacing.XL))
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(navigationBarHeight))
                        }
                    }

                    SearchTopBar(
                        component = component,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(top = statusBarHeight)
                    )
                }
            }
            is MainViewState.Error -> {
                ErrorState(
                    message = s.message,
                    onRetry = { component.retry() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private val TopBarHeight = 56.dp

@Composable
private fun SearchTopBar(
    component: MainComponent,
    modifier: Modifier = Modifier
) {
    var isSearchExpanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(NetflixColors.BlackCanvas)
            .height(TopBarHeight)
            .fillMaxWidth()
    ) {
        AnimatedContent(
            targetState = isSearchExpanded,
            transitionSpec = {
                fadeIn(tween(200)) togetherWith fadeOut(tween(200))
            },
            modifier = Modifier.fillMaxSize()
        ) { expanded ->
            if (expanded) {
                SearchExpandedBar(
                    component = component,
                    onCollapse = {
                        isSearchExpanded = false
                        component.onSearchQueryChanged("")
                    }
                )
            } else {
                CollapsedBar(
                    onSearchClick = { isSearchExpanded = true }
                )
            }
        }
    }
}

@Composable
private fun CollapsedBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = NetflixSpacing.Base),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TMDb",
            style = MaterialTheme.typography.headlineMedium,
            color = NetflixColors.RedPrimary
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onSearchClick() },
            contentAlignment = Alignment.Center
        ) {
            SearchIcon()
        }
    }
}

@Composable
private fun SearchExpandedBar(
    component: MainComponent,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    val query by component.searchQuery.subscribeAsState()

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(end = NetflixSpacing.Base),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onCollapse() },
            contentAlignment = Alignment.Center
        ) {
            BackArrowIcon()
        }
        Spacer(modifier = Modifier.width(NetflixSpacing.XS))
        TextField(
            value = query,
            onValueChange = { component.onSearchQueryChanged(it) },
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Titles, people, genres",
                    color = NetflixColors.GrayMuted
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NetflixColors.WhitePrimary,
                unfocusedTextColor = NetflixColors.WhitePrimary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = NetflixColors.WhitePrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun SearchIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(24.dp)) {
        val strokeWidth = 2.5.dp.toPx()
        val circleRadius = size.width * 0.3f
        val circleCenter = Offset(size.width * 0.4f, size.height * 0.4f)

        drawCircle(
            color = NetflixColors.WhitePrimary,
            radius = circleRadius,
            center = circleCenter,
            style = Stroke(width = strokeWidth)
        )

        drawLine(
            color = NetflixColors.WhitePrimary,
            start = Offset(
                circleCenter.x + circleRadius * 0.7f,
                circleCenter.y + circleRadius * 0.7f
            ),
            end = Offset(size.width * 0.9f, size.height * 0.9f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun BackArrowIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(20.dp)) {
        val strokeWidth = 3.dp.toPx()
        drawLine(
            color = NetflixColors.WhitePrimary,
            start = Offset(size.width * 0.7f, size.height * 0.15f),
            end = Offset(size.width * 0.3f, size.height * 0.5f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = NetflixColors.WhitePrimary,
            start = Offset(size.width * 0.3f, size.height * 0.5f),
            end = Offset(size.width * 0.7f, size.height * 0.85f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
