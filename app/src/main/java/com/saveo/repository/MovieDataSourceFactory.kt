package com.saveo.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.saveo.model.ResultsItem

class MovieDataSourceFactory : DataSource.Factory<Int, ResultsItem>() {

    private val liveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, ResultsItem?> {
        val source = MovieDataSource()
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 20
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}
