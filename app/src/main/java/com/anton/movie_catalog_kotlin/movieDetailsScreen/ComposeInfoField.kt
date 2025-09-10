package com.anton.movie_catalog_kotlin.movieDetailsScreen



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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.models_old.FilmDetails
import com.anton.movie_catalog_kotlin.models_old.MovieDetailsModel


@Composable
fun ComposeInfoField(
    iconResId: Int,
    text: String,
    kinopoiskDetails: FilmDetails?,
    movieDetails: MovieDetailsModel?
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
            InfoFieldSection(kinopoiskDetails,movieDetails, modifier = Modifier)
        }
    }
}


@Composable
fun InfoFieldSection(kinopoiskDetails: FilmDetails?,
                            movieDetails: MovieDetailsModel?, modifier: Modifier = Modifier) {




    Row(Modifier.fillMaxWidth()) {
        InfoBox(
            title = "Страны",
            text = kinopoiskDetails?.countries?.take(2)?.map { it.country }?.joinToString(", ") ?: "Без стран",
            modifier = Modifier.weight(2f).padding(end=10.dp),
        )

        InfoBox(
            title = "Возраст",
            text = "${movieDetails?.ageLimit ?: "Без ограничений"}+" ,
            modifier = Modifier.weight(1f).padding(end=10.dp),
        )
    }


    Row(Modifier.fillMaxWidth()) {

        val formattedTime = kinopoiskDetails?.filmLength?.let { filmLengthMinutes ->
            val hours = filmLengthMinutes / 60
            val minutes = filmLengthMinutes % 60
            if (hours > 0) {
                String.format("%d ч %d мин", hours, minutes)
            } else {
                "$minutes мин."
            }
        } ?: "Не указано"


        InfoBox(
            title = "Время",
            text = formattedTime,
            modifier = Modifier.weight(1f).padding(top = 10.dp, end=10.dp),
        )

        InfoBox(
            title = "Год выхода",
            text = "${movieDetails?.ageLimit ?: "Без ограничений"}+" ,
            modifier = Modifier.weight(1f).padding(top = 10.dp, end = 10.dp),
        )



    }





}

@Composable
fun InfoBox(
    title: String,
    modifier: Modifier = Modifier,
    text: String
) {

    Box(
        modifier = modifier
            .padding(0.dp)
            .background(colorResource(id = R.color.dark), RoundedCornerShape(8.dp))

    ) {
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(6.dp),) {
            Text( text= title, fontSize = 18.sp, color = colorResource(R.color.gray))
            Text( text = text, fontSize = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }






//        Box(
//            modifier = modifier
//                .padding(3.dp)
//                .background(colorResource(id = R.color.dark), RoundedCornerShape(8.dp))
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize().padding(8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ) {
//                Box(modifier = Modifier.size(35.dp)) {
//
//                }
//                Text(
//                    text = rating.toString(),
//                    fontFamily = FontFamily(Font(R.font.manrope_bold)),
//                    fontSize = 24.sp
//
//                )
//            }
//        }



}

