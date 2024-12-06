package com.anton.movie_catalog_kotlin.profileScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


class ProfileScreenViewModelProvider : PreviewParameterProvider<ProfileScreenViewModel> {
    override val values = sequenceOf(ProfileScreenViewModel())
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(@PreviewParameter(ProfileScreenViewModelProvider::class) profileScreenViewModel: ProfileScreenViewModel) {
    ProfileScreen(rememberNavController(), profileScreenViewModel)
}


@Composable
fun ProfileScreen(navController: NavController, profileScreenViewModel: ProfileScreenViewModel = viewModel()) {
    LaunchedEffect(Unit) {
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            navController.navigate("movie")
        }) {
            Text("ProfileScreen")
        }
    }
}