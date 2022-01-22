package com.saveo.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.saveo.model.ResultsItem
import com.saveo.repository.MovieDataSourceFactory

object MoviesRepository {

    private var sampleDataSourceFactory = MovieDataSourceFactory()

    fun observeMoviesList() = getDataFromRemote()

    private fun getDataFromRemote(): LiveData<PagedList<ResultsItem>> {
        return LivePagedListBuilder(
            sampleDataSourceFactory,
            MovieDataSourceFactory.pagedListConfig()
        ).build()
    }
}
