package com.saveo.ui.movies

import com.saveo.repository.MoviesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saveo.databinding.FragmentMoviesBinding
import com.saveo.model.ResultsItem
import com.saveo.utils.BaseFragment
import com.saveo.utils.getItemDecoration
import com.saveo.utils.initViewModel

class MoviesFragment : BaseFragment() {

    val viewModel: MoviesViewModel by activityViewModels()
    lateinit var moviesBinding: FragmentMoviesBinding
    lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return moviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesBinding.popularMoviesRv.addItemDecoration(getItemDecoration(right = 8, top = 16, bottom = 16, left = 8))
        moviesBinding.moviesRv.addItemDecoration(getItemDecoration(right = 8, top = 16, bottom = 16, left = 8))

        setUpAdapter()
        setUpObservers()
    }

    private fun setUpAdapter() {
        adapter = MoviesAdapter(object : MoviesAdapter.ItemClickListener {
            override fun onItemClick(resultsItem: ResultsItem) {
                viewModel.setResultDetails(resultsItem)
                findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment2())
            }
        })
        moviesBinding.popularMoviesRv.adapter = adapter
        moviesBinding.moviesRv.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.moviesList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}