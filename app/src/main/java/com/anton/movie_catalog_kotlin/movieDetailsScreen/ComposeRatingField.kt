package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.models_old.FilmDetails


@Composable
fun RatingField(
    iconResId: Int,
    text: String,
    kinopoiskDetails: FilmDetails?,
) {

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = colorResource(R.color.dark_faded),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            RatingSection(kinopoiskDetails)
        }
    }
}


@Composable
fun RatingSection(kinopoiskDetails: FilmDetails?) {
    val iconKinopoisk =
        rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.ic_kinopoisk_logo))
    val iconImdb = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.ic_imdb_logo))
    val iconLogoMd = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.ic_logo_md))

    Row(Modifier.fillMaxWidth()) {
        RatingBox(
            modifier = Modifier.weight(1.4f),
            icon = iconLogoMd,
            rating = kinopoiskDetails?.ratingFilmCritics ?: 0.0,
            showRating = kinopoiskDetails?.ratingFilmCritics != null && kinopoiskDetails?.ratingFilmCritics != 0.0,
        )
        RatingBox(
            modifier = Modifier.weight(1f),
            icon = iconKinopoisk,
            rating = kinopoiskDetails?.ratingKinopoisk ?: 0.0,
            showRating = kinopoiskDetails?.ratingKinopoisk != null && kinopoiskDetails?.ratingKinopoisk != 0.0,
        )
        RatingBox(
            modifier = Modifier.weight(1f),
            icon = iconImdb,
            rating = kinopoiskDetails?.ratingImdb ?: 0.0,
            showRating = kinopoiskDetails?.ratingImdb != null && kinopoiskDetails?.ratingImdb != 0.0,
        )
    }
}

@Composable
fun RatingBox(
    modifier: Modifier = Modifier,
    icon: Painter,
    rating: Double,
    showRating: Boolean
) {
    if (showRating) {
        Box(
            modifier = modifier
                .padding(3.dp)
                .background(colorResource(id = R.color.dark), RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(modifier = Modifier.size(35.dp)) {
                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().padding(end = 8.dp)
                    )
                }
                Text(
                    text = rating.toString(),
                    fontFamily = FontFamily(Font(R.font.manrope_bold)),
                    fontSize = 24.sp

                )
            }
        }
    }
}


