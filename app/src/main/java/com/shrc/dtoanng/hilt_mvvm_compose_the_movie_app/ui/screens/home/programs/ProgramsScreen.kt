package com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.programs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.components.MovieItem
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.components.MovieItemLandscape
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.base.BaseMovieListUiEvent
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.nowplaying.NowPlayingMoviesViewModel
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.popular.PopularMoviesViewModel
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.utils.Category

@Composable
fun ProgramsScreen(navController: NavHostController) {

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            NowPlayingMovieRow(navController)
        }

        item {
            PopularMovieRow(navController)
        }

        item {
            //todo:
        }

        item {
            //todo:
        }
    }
}

@Composable
private fun PopularMovieRow(navHostController: NavHostController) {
    val popularMoviesViewModel = hiltViewModel<PopularMoviesViewModel>()
    val movieListState = popularMoviesViewModel.movieListState.collectAsState().value
    if (movieListState.movieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Text(
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = "Popular movies on Netflix",
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp)
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier.height(350.dp),
        ) {
            items(movieListState.movieList.size) { index ->
                MovieItem(
                    movie = movieListState.movieList[index],
                    navHostController = navHostController
                )

                if (index >= movieListState.movieList.size - 1 && !movieListState.isLoading) {
                    popularMoviesViewModel.onEvent(BaseMovieListUiEvent.Paginate(Category.POPULAR))
                }
            }
        }
    }
}

@Composable
private fun NowPlayingMovieRow(navHostController: NavHostController) {
    val nowPlayingMoviesViewModel = hiltViewModel<NowPlayingMoviesViewModel>()
    val movieListState = nowPlayingMoviesViewModel.movieListState.collectAsState().value
    if (movieListState.movieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Text(
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = "Now Playing on Netflix",
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.height(350.dp)
        ) {
            items(movieListState.movieList.size) { index ->

                MovieItemLandscape(
                    movie = movieListState.movieList[index],
                    navHostController = navHostController
                )

                if (index >= movieListState.movieList.size - 1 && !movieListState.isLoading) {
                    nowPlayingMoviesViewModel.onEvent(BaseMovieListUiEvent.Paginate(Category.POPULAR))
                }
            }
        }
    }
}