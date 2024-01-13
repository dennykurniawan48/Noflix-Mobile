package com.dennydev.noflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.model.response.movies.Movies
import com.dennydev.noflix.repository.AuthStoreRepository
import com.dennydev.noflix.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val datastoreRepository: AuthStoreRepository,
    private val repository: MoviesRepository
): ViewModel() {
    val token = datastoreRepository.flowToken
    private val _isSignedIn = mutableStateOf(false)
    val isSignedIn: State<Boolean> = _isSignedIn
    private val _movies = mutableStateOf<ApiResponse<Movies>>(ApiResponse.Idle())
    val movies: State<ApiResponse<Movies>> = _movies
    private val _loadMovie = mutableStateOf(true)
    val loadMovie: State<Boolean> = _loadMovie

    init {
        viewModelScope.launch {
            datastoreRepository.flowToken.collect { token ->
                _isSignedIn.value = token.isNotEmpty()
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            datastoreRepository.removeToken()
            _isSignedIn.value = false
        }
    }

    fun changeLoadMovie(){
        _loadMovie.value = !_loadMovie.value
    }

    fun getSignedStatus(): Boolean{
        var signed = false
        viewModelScope.launch {
            token.collectLatest {
                signed = it.isNotEmpty()
            }
        }
        return signed
    }

    fun getMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            _movies.value = repository.movies()
        }
    }
}