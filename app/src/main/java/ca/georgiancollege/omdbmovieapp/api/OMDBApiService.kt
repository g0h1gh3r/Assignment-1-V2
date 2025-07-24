package ca.georgiancollege.omdbmovieapp.api

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson

// Import your data classes with correct package name
import ca.georgiancollege.omdbmovieapp.model.Movie
import ca.georgiancollege.omdbmovieapp.model.MovieSearch
import ca.georgiancollege.omdbmovieapp.model.MovieDetails

class OMDBApiService {
    private val apiKey = "YOUR_API_KEY_HERE" // Replace with your actual API key
    private val baseUrl = "http://www.omdbapi.com/"
    private val gson = Gson()

    suspend fun searchMovies(query: String): MovieSearch? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("$baseUrl?apikey=$apiKey&s=$query")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                gson.fromJson(response, MovieSearch::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getMovieDetails(imdbId: String): MovieDetails? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("$baseUrl?apikey=$apiKey&i=$imdbId")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                gson.fromJson(response, MovieDetails::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}