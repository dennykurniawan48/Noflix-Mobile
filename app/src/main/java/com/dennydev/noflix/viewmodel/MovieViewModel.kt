package com.dennydev.noflix.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.movie.Movie
import com.dennydev.noflix.model.response.movies.Movies
import com.dennydev.noflix.repository.AuthStoreRepository
import com.dennydev.noflix.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val player: ExoPlayer
): ViewModel() {
    private val _movie = mutableStateOf<ApiResponse<Movie>>(ApiResponse.Idle())
    val movie: State<ApiResponse<Movie>> = _movie

    fun movie(id: String){
        _movie.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value = repository.movie(id)
        }
    }

    fun getPlayer(): ExoPlayer = player

    fun playMovie(uri: Uri){
        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}