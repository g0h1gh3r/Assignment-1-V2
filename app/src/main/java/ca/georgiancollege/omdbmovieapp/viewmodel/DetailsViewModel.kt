package ca.georgiancollege.omdbmovieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ca.georgiancollege.omdbmovieapp.api.OMDBApiService
import ca.georgiancollege.omdbmovieapp.model.MovieDetails
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val apiService = OMDBApiService()

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadMovieDetails(imdbId: String) {
        _loading.value = true
        _error.value = ""

        viewModelScope.launch {
            try {
                val result = apiService.getMovieDetails(imdbId)
                if (result?.Response == "True") {
                    _movieDetails.value = result
                } else {
                    _error.value = "Movie details not found"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}