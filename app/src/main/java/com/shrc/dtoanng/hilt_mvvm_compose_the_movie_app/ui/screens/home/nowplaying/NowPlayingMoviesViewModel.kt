package com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.nowplaying

import androidx.lifecycle.viewModelScope
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.domain.repository.MovieRepository
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.ui.screens.home.base.BaseViewModel
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.utils.Category
import com.shrc.dtoanng.hilt_mvvm_compose_the_movie_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel() {
    init {
        getNowPlayingMovie(false)
    }

    override fun doPagingTask() {
        getNowPlayingMovie(true)
    }

    private fun getNowPlayingMovie(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getMovieList(
                forceFetchFromRemote = forceFetchFromRemote,
                category = Category.NOW_PLAYING,
                page = movieListState.value.currentPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.loading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upComingMovieList ->
                            _movieListState.update {
                                it.copy(
                                    movieList = movieListState.value.movieList + upComingMovieList.shuffled(),
                                    currentPage = movieListState.value.currentPage + 1
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}