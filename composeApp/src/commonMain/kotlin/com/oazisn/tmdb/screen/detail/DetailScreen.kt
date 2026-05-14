package com.oazisn.tmdb.screen.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.oazisn.tmdb.component.DetailComponent
import com.oazisn.tmdb.component.DetailViewState
import com.oazisn.tmdb.screen.components.ErrorState
import com.oazisn.tmdb.screen.components.LoadingIndicator
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.roundToInt

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

@Composable
fun DetailScreen(component: DetailComponent) {
    val state by component.state.subscribeAsState()
    val isFavorite by component.isFavorite.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(NetflixColors.BlackCanvas)) {
        when (val s = state) {
            is DetailViewState.Loading -> {
                LoadingIndicator(modifier = Modifier.fillMaxSize())
            }
            is DetailViewState.Success -> {
                DetailContent(
                    component = component,
                    isFavorite = isFavorite,
                    movie = s.movie
                )
            }
            is DetailViewState.Error -> {
                ErrorState(
                    message = s.message,
                    onRetry = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    component: DetailComponent,
    isFavorite: Boolean,
    movie: com.oazisn.tmdb.data.model.MovieDetail
) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                SubcomposeAsyncImage(
                    model = IMAGE_BASE_URL + movie.backdropPath,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    NetflixColors.BlackCanvas
                                ),
                                startY = 0f,
                                endY = 420.dp.value * 3f
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = NetflixSpacing.Base)
                        .padding(bottom = NetflixSpacing.Base)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.displayLarge,
                        color = NetflixColors.WhitePrimary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(NetflixSpacing.SM))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StarIcon(
                            filled = true,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${(movie.voteAverage * 10).roundToInt() / 10.0}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = NetflixColors.StarRating
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NetflixColors.BlackCanvas)
                    .padding(horizontal = NetflixSpacing.Base)
                    .padding(top = NetflixSpacing.MD, bottom = NetflixSpacing.XXL)
            ) {
                if (movie.overview.isNotBlank()) {
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = NetflixColors.WhiteOverlay,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(NetflixSpacing.SM)
                .fillMaxWidth()
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(NetflixColors.GrayMedium.copy(alpha = 0.6f))
                    .clickable { component.onBack() },
                contentAlignment = Alignment.Center
            ) {
                BackArrowIcon()
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(NetflixColors.GrayMedium.copy(alpha = 0.6f))
                    .clickable { component.toggleFavorite() },
                contentAlignment = Alignment.Center
            ) {
                HeartIcon(filled = isFavorite)
            }
        }
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

@Composable
private fun HeartIcon(filled: Boolean, modifier: Modifier = Modifier) {
    val color = if (filled) Color.Red else NetflixColors.WhitePrimary
    Canvas(modifier = modifier.size(22.dp)) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(width * 0.5f, height * 0.9f)
            cubicTo(
                width * 0.1f, height * 0.6f,
                width * -0.1f, height * 0.2f,
                width * 0.15f, height * 0.1f
            )
            cubicTo(
                width * 0.3f, height * 0.0f,
                width * 0.45f, height * 0.15f,
                width * 0.5f, height * 0.35f
            )
            cubicTo(
                width * 0.55f, height * 0.15f,
                width * 0.7f, height * 0.0f,
                width * 0.85f, height * 0.1f
            )
            cubicTo(
                width * 1.1f, height * 0.2f,
                width * 0.9f, height * 0.6f,
                width * 0.5f, height * 0.9f
            )
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = if (filled) Fill else androidx.compose.ui.graphics.drawscope.Stroke(width = 2.5.dp.toPx())
        )
    }
}

@Composable
private fun StarIcon(filled: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = if (filled) NetflixColors.StarRating else NetflixColors.GrayMuted
        val path = Path().apply {
            val center = Offset(size.width / 2f, size.height / 2f)
            val outerRadius = size.width * 0.45f
            val innerRadius = size.width * 0.18f
            for (i in 0 until 5) {
                val outerAngle = (i * 72.0 - 90.0) * (PI / 180.0)
                val innerAngle = (i * 72.0 + 36.0 - 90.0) * (PI / 180.0)
                val outerX = center.x + outerRadius * cos(outerAngle).toFloat()
                val outerY = center.y + outerRadius * sin(outerAngle).toFloat()
                val innerX = center.x + innerRadius * cos(innerAngle).toFloat()
                val innerY = center.y + innerRadius * sin(innerAngle).toFloat()
                if (i == 0) moveTo(outerX, outerY) else lineTo(outerX, outerY)
                lineTo(innerX, innerY)
            }
            close()
        }
        drawPath(path = path, color = color, style = Fill)
    }
}
