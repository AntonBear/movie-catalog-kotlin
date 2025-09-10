package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.models_old.MovieDetailsModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewField(
    movieDetails: MovieDetailsModel?,
    showReviewDialog: Boolean,
    onShowReviewDialog: () -> Unit,
    viewModel: MovieDetailsViewModel
) {

    var currentReviewIndex by remember { mutableIntStateOf(0) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = colorResource(id = R.color.dark_faded),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_review),
                    contentDescription = "Reviews Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Отзывы",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            movieDetails?.let { details ->
                val reviews = details.reviews
                val currentReview = reviews?.getOrNull(currentReviewIndex)

                currentReview?.let { review ->
                    val author = review.author
                    val rating = review.rating
                    val reviewText = review.reviewText
                    val createDateTime = review.createDateTime

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(author.avatar ?: ""),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(author.nickName ?: "Anonymous", style = MaterialTheme.typography.bodyMedium)
                                val formattedDate = remember(createDateTime) {
                                    createDateTime?.let {
                                        try {
                                            val dateTime = LocalDateTime.parse(it)
                                            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
                                            dateTime.format(formatter)
                                        } catch (e: Exception) {
                                            it
                                        }
                                    } ?: ""
                                }

                                Text(formattedDate, style = MaterialTheme.typography.bodySmall)
                                Text(reviewText ?: "", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = colorResource(id = R.color.green)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = "Rating Icon",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(rating.toString() ?: "", fontSize = 16.sp)
                            }
                        }

                        IconButton(
                            onClick = {
                                reviews.let {
                                    currentReviewIndex = (currentReviewIndex - 1 + it.size) % it.size
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_left),
                                contentDescription = "Previous Review"
                            )
                        }
                        IconButton(
                            onClick = {
                                reviews.let {
                                    currentReviewIndex = (currentReviewIndex + 1) % it.size
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chevron_right),
                                contentDescription = "Next Review"
                            )
                        }
                    }
                } ?: run {
                    Text("Отзывов пока нет")
                }
            }



            Button(
                onClick = { onShowReviewDialog() },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Red,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text(text = stringResource(id = R.string.change_review))
            }
        }
    }
}

