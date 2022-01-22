package com.saveo.repository

import androidx.paging.PageKeyedDataSource
import com.saveo.model.MoviesResponse
import com.saveo.model.ResultsItem
import com.saveo.network.ApiClient
import com.saveo.utils.Constants
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response;

class MovieDataSource : PageKeyedDataSource<Int, ResultsItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultsItem>
    ) {
        fetchData(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultsItem>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page - 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultsItem>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page + 1)
        }
    }

    private fun fetchData(
        page: Int,
        callback: (List<ResultsItem?>) -> Unit
    ) {
        val response = ApiClient.getService().getPopularMoviesList(Constants.API_KEY, "en-Us", page)
        response.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful)
                    response.body()?.results?.let { callback(it) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {

            }
        })
    }
}
