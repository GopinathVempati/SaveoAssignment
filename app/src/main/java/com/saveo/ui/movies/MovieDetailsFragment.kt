package com.saveo.ui.movies

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.saveo.R
import com.saveo.databinding.FragmentMovieDetailsBinding
import com.saveo.model.GenresItem
import com.saveo.network.APIResult
import com.saveo.repository.MoviesViewModel
import com.saveo.utils.Constants
import com.saveo.utils.Constants.HttpCode.SUCCESS
import com.saveo.utils.loadImageFromUrl
import com.saveo.utils.toDispalyFormat

class MovieDetailsFragment : Fragment() {

    lateinit var movieDetailsBinding: FragmentMovieDetailsBinding
    val moviesViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return movieDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = moviesViewModel.getResultDetails()
        setUpObservers()
        result.id?.let { moviesViewModel.getMovieDetails(it) }
    }

    private fun setUpObservers() {
        moviesViewModel.movieDetailsResponse.observe(viewLifecycleOwner, {
            when (it) {
                is APIResult.Success -> {
                    if (it.data.isSuccessful) {
                        it.data.body()?.let {
                            it.genres?.let { it1 -> updateGeneres(it1) }
                            movieDetailsBinding.movieImageView.loadImageFromUrl(it.posterPath)
                            movieDetailsBinding.movieTitle.text = it.originalTitle
                            movieDetailsBinding.movieInfo.text =
                                "R | ".plus(it.runtime).plus(" mins | ")
                                    .plus(it.releaseDate.toDispalyFormat())
                        }
                    } else {
                        //hideProgressBar()
                    }
                }
                is APIResult.Error -> {
                    // hideProgressBar()
                }
            }
        })
    }


    private fun updateGeneres(tagList: List<GenresItem>) {
        movieDetailsBinding.generesInfo.removeAllViews()
        if (tagList.isNotEmpty()) {
            for (index in tagList.indices) {
                val tagName = tagList[index]
                val chip = Chip(requireActivity())
                val paddingDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5f,
                    resources.displayMetrics
                ).toInt()
                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
                chip.text = tagName.name
                chip.setTextColor(resources.getColor(R.color.blumine, null))
                chip.setTextAppearance(R.style.chipStyle)
                movieDetailsBinding.generesInfo.addView(chip)
            }
        }
    }

}