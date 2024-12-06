package com.anton.movie_catalog_kotlin.navigationBar


import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationBarViewModel : ViewModel() {
    sealed class Screen(val route: String, val iconResId: Int, val selectedIconResId: Int) {
        data object FeedScreen : Screen(NavigationRoutes.FEED, R.drawable.feed_icon_description, R.drawable.feed_icon_selected)
        data object MovieScreen : Screen(NavigationRoutes.MOVIE, R.drawable.movie_icon_description, R.drawable.movie_icon_selected)
        data object FavoritesScreen : Screen(NavigationRoutes.FAVORITES, R.drawable.favorite_icon_description, R.drawable.favorites_icon_selected)
        data object ProfileScreen : Screen(NavigationRoutes.PROFILE, R.drawable.profile_icon_description, R.drawable.profile_icon_selected)
    }

    private val _currentPage = MutableStateFlow<Screen>(Screen.FeedScreen)
    val currentPage = _currentPage.asStateFlow()

    fun changePage(screen: Screen) {
        _currentPage.value = screen
    }

}