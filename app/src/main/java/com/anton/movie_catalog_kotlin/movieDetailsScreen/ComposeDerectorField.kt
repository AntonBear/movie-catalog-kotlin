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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.anton.movie_catalog_kotlin.R


@Composable
fun DirectorField(
    iconResId: Int,
    text: String,
    imageURL: String?,
    directorName: String?,
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
            Row(Modifier.fillMaxWidth()) {
                DirectionBox(
                    imageURL = imageURL,
                    directorName = directorName,
                )
            }
        }
    }
}



@Composable
fun DirectionBox(
    modifier: Modifier = Modifier,
    imageURL: String?,
    directorName: String?,
) {

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
                    AsyncImage(
                        model = imageURL,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().padding(end = 8.dp).clip(CircleShape) ,
                        placeholder = painterResource(id = R.drawable.background),
                        error = painterResource(id = R.drawable.background)
                    )
                }
                Text(
                    text = directorName ?: "Режиссёр отсуствтует",
                    fontFamily = FontFamily(Font(R.font.manrope_bold)),
                    fontSize = 16.sp

                )
            }
        }
    }
