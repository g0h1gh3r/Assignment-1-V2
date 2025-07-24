package ca.georgiancollege.omdbmovieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ca.georgiancollege.omdbmovieapp.api.OMDBApiService
import ca.georgiancollege.omdbmovieapp.model.Movie
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val apiService = OMDBApiService()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun searchMovies(query: String) {
        if (query.isBlank()) return

        _loading.value = true
        _error.value = ""

        viewModelScope.launch {
            try {
                val result = apiService.searchMovies(query)
                if (result?.Response == "True") {
                    _movies.value = result.Search ?: emptyList()
                } else {
                    _error.value = "No movies found"
                    _movies.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
                _movies.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}