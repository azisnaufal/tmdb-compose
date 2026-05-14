package com.oazisn.tmdb.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.oazisn.tmdb.component.DetailComponent
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

@Composable
fun DetailScreen(component: DetailComponent) {
    val movie = component.movie

    Box(modifier = Modifier.fillMaxSize().background(NetflixColors.BlackCanvas)) {
        SubcomposeAsyncImage(
            model = IMAGE_BASE_URL + movie.backdropPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.TopCenter)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            NetflixColors.BlackCanvas
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .padding(NetflixSpacing.SM)
                .align(Alignment.TopStart)
                .size(40.dp)
                .clip(CircleShape)
                .background(NetflixColors.GrayMedium.copy(alpha = 0.6f))
                .clickable { component.onBack() },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(20.dp)) {
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

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = NetflixSpacing.Base)
                .padding(bottom = NetflixSpacing.XXL)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.displayLarge,
                color = NetflixColors.WhitePrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(NetflixSpacing.SM))
            if (movie.overview.isNotBlank()) {
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    color = NetflixColors.WhiteOverlay,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
            }
        }
    }
}
