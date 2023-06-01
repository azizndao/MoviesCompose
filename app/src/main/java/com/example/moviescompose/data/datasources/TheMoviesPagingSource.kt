package com.example.moviescompose.data.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviescompose.data.model.TheMovieResponse
import kotlinx.collections.immutable.toPersistentList
import timber.log.Timber


class TheMoviesPagingSource<T : Any>(
  private val pageFactory: suspend (page: Int) -> TheMovieResponse<T>
) : PagingSource<Int, T>() {

  override val jumpingSupported: Boolean = true

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = try {
    val nextPageNumber = params.key ?: 1
    val response = pageFactory(params.key ?: 1)
    LoadResult.Page(
      data = response.results.toPersistentList(),
      prevKey = null,
      nextKey = if (nextPageNumber < response.totalPages) nextPageNumber + 1 else null
    )
  } catch (exception: Exception) {
    Timber.e(exception)
    LoadResult.Error(exception)
  }

  override fun getRefreshKey(state: PagingState<Int, T>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}