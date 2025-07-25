package ca.georgiancollege.omdbmovieapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ca.georgiancollege.omdbmovieapp.databinding.ActivityDetailsBinding
import ca.georgiancollege.omdbmovieapp.viewmodel.DetailsViewModel

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide the title
        supportActionBar?.hide()

        setContentView(R.layout.activity_details)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imdbId = intent.getStringExtra("IMDB_ID")
        if (imdbId != null) {
            detailsViewModel.loadMovieDetails(imdbId)
        }

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        detailsViewModel.movieDetails.observe(this) { movie ->
            binding.tvMovieTitle.text = movie.Title
            binding.tvMovieYear.text = "Year: ${movie.Year}"
            binding.tvMovieRated.text = "Rated: ${movie.Rated}"
            binding.tvMovieDirector.text = "Director: ${movie.Director}"
            binding.tvMovieGenre.text = "Genre: ${movie.Genre}"
            binding.tvMovieRating.text = "IMDB Rating: ${movie.imdbRating}"
            binding.tvMovieRuntime.text = "Runtime: ${movie.Runtime}"
            binding.tvMovieActors.text = "Actors: ${movie.Actors}"
            binding.tvMoviePlot.text = movie.Plot

            if (movie.Poster != "N/A") {
                Glide.with(this)
                    .load(movie.Poster)
                    .into(binding.ivMoviePoster)
            }
        }

        detailsViewModel.loading.observe(this) { isLoading ->
            binding.progressBarDetails.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailsViewModel.error.observe(this) { error ->
            // Handle error display if needed
        }
    }
}