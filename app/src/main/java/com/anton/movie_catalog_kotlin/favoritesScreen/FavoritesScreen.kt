package com.anton.movie_catalog_kotlin.favoritesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController


class FavoritesScreenViewModelProvider : PreviewParameterProvider<FavoritesScreenViewModel> {
    override val values = sequenceOf(FavoritesScreenViewModel())
}

@Composable
@Preview(showBackground = true)
fun FavoritesScreenPreview(@PreviewParameter(FavoritesScreenViewModelProvider::class) favoritesScreenViewModel: FavoritesScreenViewModel) {
    FavoritesScreen(/*favoritesScreenViewModel*/)
}

@Composable
fun FavoritesScreen(/*favoritesScreenViewModel: FavoritesScreenViewModel = viewModel()*/) {

    val navController = LocalView.current.findNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = {}) {
            Text("FavoritesScreen")
        }
    }
}