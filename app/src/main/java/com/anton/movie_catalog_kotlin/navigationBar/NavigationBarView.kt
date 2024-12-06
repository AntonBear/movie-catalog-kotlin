package com.anton.movie_catalog_kotlin.navigationBar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.favoritesScreen.FavoritesScreen
import com.anton.movie_catalog_kotlin.feedScreen.FeedScreenDelegate
import com.anton.movie_catalog_kotlin.feedScreen.FeedScreenView
import com.anton.movie_catalog_kotlin.moviesScreen.MoviesScreen
import com.anton.movie_catalog_kotlin.profileScreen.ProfileScreen
import com.anton.movie_catalog_kotlin.ui.theme.MoviecatalogkotlinTheme


class NavigationBarView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviecatalogkotlinTheme {
                val navController = rememberNavController()
                val viewModel: NavigationBarViewModel = viewModel()
                NavigationBarView(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    val viewModel = NavigationBarViewModel()
    NavigationBarView(navController = navController, viewModel = viewModel)
}

@Composable
fun NavigationBarView(navController: NavHostController, viewModel: NavigationBarViewModel) {
    Scaffold(
        containerColor = colorResource(id = R.color.backgroundColor)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.FEED,
                modifier = Modifier.weight(1f)
            ) {
                composable(NavigationBarViewModel.Screen.FeedScreen.route) {
                    FeedScreenView(
                        object : FeedScreenDelegate {
                            override fun onMovieClick() {
                                navController.navigate("movie")
                            }
                        },
                        navController
                    )
                }
                composable(NavigationBarViewModel.Screen.MovieScreen.route) {
                    MoviesScreen(
                        navController
                    )
                }
                composable(NavigationBarViewModel.Screen.FavoritesScreen.route) {
                    FavoritesScreen(
                        navController
                    )
                }
                composable(NavigationBarViewModel.Screen.ProfileScreen.route) {
                    ProfileScreen(
                        navController
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 5.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_faded)),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp.Unspecified)
                        .padding(8.dp)
                ) {
                    NavItem(
                        screen = NavigationBarViewModel.Screen.FeedScreen,
                        viewModel = viewModel,
                        onClick = {
                            navController.navigate(NavigationRoutes.FEED)
                            viewModel.changePage(NavigationBarViewModel.Screen.FeedScreen)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    NavItem(
                        screen = NavigationBarViewModel.Screen.MovieScreen,
                        viewModel = viewModel,
                        onClick = {navController.navigate(NavigationRoutes.MOVIE)
                            viewModel.changePage(NavigationBarViewModel.Screen.MovieScreen)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    NavItem(
                        screen = NavigationBarViewModel.Screen.FavoritesScreen,
                        viewModel = viewModel,
                        onClick = {
                            navController.navigate(NavigationRoutes.FAVORITES)
                            viewModel.changePage(NavigationBarViewModel.Screen.FavoritesScreen)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    NavItem(
                        screen = NavigationBarViewModel.Screen.ProfileScreen,
                        viewModel = viewModel,
                        onClick = {
                            navController.navigate(NavigationRoutes.PROFILE)
                            viewModel.changePage(NavigationBarViewModel.Screen.ProfileScreen)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun NavItem(
    screen: NavigationBarViewModel.Screen,
    viewModel: NavigationBarViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentScreen by viewModel.currentPage.collectAsState()
    val isSelected = currentScreen == screen

    IconButton(
        onClick = onClick,
        modifier = modifier
    )
    {
        val iconResId = if (isSelected) screen.selectedIconResId else screen.iconResId
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}





