package ca.georgiancollege.omdbmovieapp.api

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson
import ca.georgiancollege.omdbmovieapp.model.Movie
import ca.georgiancollege.omdbmovieapp.model.MovieSearch
import ca.georgiancollege.omdbmovieapp.model.MovieDetails

class OMDBApiService {
    private val apiKey = "f9b9618f" // Replace with your actual API key
    private val baseUrl = "http://www.omdbapi.com/"
    private val gson = Gson()

    suspend fun searchMovies(query: String): MovieSearch? {
        return withContext(Dispatchers.IO) {
            try {
                val urlString = "${baseUrl}?apikey=${apiKey}&s=${query}"
                Log.d("OMDBApi", "Search URL: $urlString")

                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                Log.d("OMDBApi", "Response Code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    Log.d("OMDBApi", "Response: $response")

                    val result = gson.fromJson(response, MovieSearch::class.java)
                    Log.d("OMDBApi", "Parsed result: Response=${result?.Response}, Search=${result?.Search?.size}")

                    result
                } else {
                    Log.e("OMDBApi", "HTTP Error: $responseCode")
                    null
                }
            } catch (e: Exception) {
                Log.e("OMDBApi", "Error: ${e.message}", e)
                null
            }
        }
    }

    suspend fun getMovieDetails(imdbId: String): MovieDetails? {
        return withContext(Dispatchers.IO) {
            try {
                // Correct format: ?apikey=[yourkey]&i=[imdb_id]
                val urlString = "${baseUrl}?apikey=${apiKey}&i=${imdbId}"
                Log.d("OMDBApi", "Details URL: $urlString")

                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode
                Log.d("OMDBApi", "Details Response Code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    Log.d("OMDBApi", "Details Response: $response")

                    gson.fromJson(response, MovieDetails::class.java)
                } else {
                    Log.e("OMDBApi", "Details HTTP Error: $responseCode")
                    null
                }
            } catch (e: Exception) {
                Log.e("OMDBApi", "Details Error: ${e.message}", e)
                null
            }
        }
    }
}