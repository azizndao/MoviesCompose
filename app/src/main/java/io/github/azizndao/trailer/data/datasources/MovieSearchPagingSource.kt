package io.github.azizndao.trailer.data.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.azizndao.model.Movie
import io.github.azizndao.network.MovieApiService
import kotlinx.collections.immutable.toPersistentList
import timber.log.Timber

class MovieSearchPagingSource(
  private val query: String,
  private val api: MovieApiService,
) : PagingSource<Int, Movie>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> = try {
    if (query.isEmpty()) {
      LoadResult.Page(
        data = emptyList(),
        prevKey = null,
        nextKey = null
      )
    } else {
      val nextPageNumber = params.key ?: 1
      val response = api.searchMovie(query, page = nextPageNumber)
      LoadResult.Page(
        data = response.results.toPersistentList(),
        prevKey = null,
        nextKey = if (nextPageNumber < response.totalPages) nextPageNumber + 1 else null
      )
    }
  } catch (e: Exception) {
    Timber.e(e)
    LoadResult.Error(e)
  }

  override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}