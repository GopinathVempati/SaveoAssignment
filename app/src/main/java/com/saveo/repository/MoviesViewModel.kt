package com.saveo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saveo.model.MovieDetailsResponse
import com.saveo.model.ResultsItem
import com.saveo.network.APIResult
import com.saveo.network.ApiClient
import com.saveo.utils.Constants
import com.saveo.viewmodel.MoviesRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    val moviesList by lazy {
        MoviesRepository.observeMoviesList()
    }

    private lateinit var resultsItem: ResultsItem

    fun setResultDetails(resultsItem: ResultsItem) {
        this.resultsItem = resultsItem
    }

    fun getResultDetails(): ResultsItem = resultsItem


    var _movieDetailsResponse: MutableLiveData<APIResult<Response<MovieDetailsResponse>>> = MutableLiveData()
    var movieDetailsResponse: LiveData<APIResult<Response<MovieDetailsResponse>>> =
        _movieDetailsResponse

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val response = ApiClient.getService().getMovieDetails(movieId, Constants.API_KEY,"en-US")
            _movieDetailsResponse.postValue(APIResult.Success(response))
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}