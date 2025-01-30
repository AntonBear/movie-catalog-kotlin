package com.anton.movie_catalog_kotlin.movieDetailsScreen


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.anton.movie_catalog_kotlin.R
import kotlinx.coroutines.launch

@Composable
fun ReviewDialog(
    viewModel: MovieDetailsViewModel,
    onDismiss: () -> Unit,
) {


    val rating by viewModel.rating.collectAsState()
    val isAnonymous by viewModel.isAnonChecked.collectAsState()
    val text by viewModel.text.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val sendReviewResult = remember { mutableStateOf<Result<Unit>?>(null) }


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.send_review),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.mark),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(40.dp))

                Box(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)) {
                    DividedSlider(
                        initialValue = rating,
                        onRatingChanged = { newRating ->
                            viewModel.updateRating(newRating)
                        }
                    )
                }

                TextField(
                    value = text,
                    onValueChange = { viewModel.updateText(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp),
                    shape = MaterialTheme.shapes.small,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = colorResource(R.color.dark_faded),
                        focusedContainerColor = colorResource(R.color.dark_faded),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )




                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = stringResource(R.string.anonymous_review),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    GradientSwitch(checked = isAnonymous,  onCheckedChange = {isAnon -> viewModel.updateAnonChecked(isAnon)})


                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.onSendReview()
                            Log.d("ButtonClick", "кнопка нажата")
                            onDismiss()
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(R.color.gradient_start),
                                    colorResource(R.color.gradient_end)
                                )
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = stringResource(R.string.send_review))
                }

                LaunchedEffect(sendReviewResult.value) {
                    sendReviewResult.value?.fold(
                        onSuccess = {
                            onDismiss()
                        },
                        onFailure = { exception ->
                            val errorMessage = "Ошибка: ${exception.message}"
                            snackbarHostState.showSnackbar(message = errorMessage)
                        }
                    )
                    sendReviewResult.value = null
                }

                SnackbarHost(hostState = snackbarHostState)


            }
        }
    }
}

