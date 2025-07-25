package ca.georgiancollege.omdbmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ca.georgiancollege.omdbmovieapp.databinding.ItemMovieBinding
import ca.georgiancollege.omdbmovieapp.model.Movie

class MovieAdapter(
    private var movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, onMovieClick: (Movie) -> Unit) {
            binding.tvTitle.text = movie.Title
            binding.tvYear.text = "Year: ${movie.Year}"
            binding.tvType.text = "Type: ${movie.Type}"

            // Load poster image
            if (movie.Poster != "N/A") {
                Glide.with(binding.root.context)
                    .load(movie.Poster)
                    .into(binding.ivPoster)
            }

            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], onMovieClick)
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}