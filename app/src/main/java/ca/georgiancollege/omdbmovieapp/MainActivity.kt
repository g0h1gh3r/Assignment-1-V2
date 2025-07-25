package ca.georgiancollege.omdbmovieapp

import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgiancollege.omdbmovieapp.adapter.MovieAdapter
import ca.georgiancollege.omdbmovieapp.databinding.ActivityMainBinding
import ca.georgiancollege.omdbmovieapp.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(emptyList()) { movie ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("IMDB_ID", movie.imdbID)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupClickListeners() {
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchViewModel.searchMovies(query)
            }
        }

        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            binding.btnSearch.performClick()
            true
        }
    }

    private fun observeViewModel() {
        searchViewModel.movies.observe(this) { movies ->
            movieAdapter.updateMovies(movies)
        }

        searchViewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        searchViewModel.error.observe(this) { error ->
            binding.tvError.text = error
            binding.tvError.visibility = if (error.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

}