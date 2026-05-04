package com.oazisn.tmdb.screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.oazisn.tmdb.data.model.Movie
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w300"

@Composable
fun MovieCard(
    movie: Movie,
    cardWidth: Int = 100,
    onClick: (Movie) -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.08f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

    Card(
        modifier = Modifier
            .width(cardWidth.dp)
            .aspectRatio(2f / 3f)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(movie)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isHovered) 4.dp else 0.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SubcomposeAsyncImage(
                model = IMAGE_BASE_URL + movie.posterPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(NetflixColors.GrayPlaceholder),
                        contentAlignment = Alignment.Center
                    ) {}
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(NetflixColors.GrayPlaceholder),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = movie.title.take(2).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = NetflixColors.WhitePrimary
                        )
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.4f)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC000000))
                        )
                    )
            )

            if (isHovered) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = NetflixColors.WhitePrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = NetflixSpacing.XS, vertical = NetflixSpacing.XXS)
                )
            }
        }
    }
}
