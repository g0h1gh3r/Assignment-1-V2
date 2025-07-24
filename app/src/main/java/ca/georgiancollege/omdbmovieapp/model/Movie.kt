package ca.georgiancollege.omdbmovieapp.model

data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

data class MovieSearch(
    val Search: List<Movie>?,
    val totalResults: String,
    val Response: String
)

data class MovieDetails(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val imdbRating: String,
    val imdbID: String,
    val Type: String,
    val Response: String
)