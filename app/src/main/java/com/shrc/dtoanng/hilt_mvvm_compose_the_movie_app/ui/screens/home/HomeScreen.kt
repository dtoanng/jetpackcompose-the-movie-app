package com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.R
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.navigation.Screen
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.nowplaying.NowPlayingMoviesScreen
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.programs.ProgramsScreen
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.upcoming.UpcomingMoviesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
//    val homeViewModel = hiltViewModel<HomeViewModel>()
//    val movieState = homeViewModel.movieListState.collectAsState().value

    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface,
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                //onEvent = homeViewModel::onEvent
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.Programs.rout
            ) {
                composable(Screen.Programs.rout) {
                    ProgramsScreen(navController = navController)
                }
                composable(Screen.NowPlayingMovieList.rout) {
                    NowPlayingMoviesScreen(
                        navController = navController
                    )
                }
                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(
                        navHostController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    //onEvent: (MovieListUiEvent) -> Unit
) {

    val items = listOf(
        BottomItem(title = stringResource(R.string.home), icon = Icons.Rounded.Home),
        BottomItem(title = stringResource(R.string.search), icon = Icons.Rounded.Search),
        BottomItem(title = stringResource(R.string.account), icon = Icons.Rounded.AccountCircle),
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface),
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                //onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.apply {
                                    popBackStack()
                                    navigate(Screen.Programs.rout)
                                }
                            }

                            1 -> {
                                //onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.apply {
                                    popBackStack()
                                    navigate(Screen.NowPlayingMovieList.rout)
                                }
                            }

                            2 -> {
                                //onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.apply {
                                    popBackStack()
                                    navigate(Screen.UpcomingMovieList.rout)
                                }
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                )
            }
        }
    }
}

data class BottomItem(val title: String, val icon: ImageVector)