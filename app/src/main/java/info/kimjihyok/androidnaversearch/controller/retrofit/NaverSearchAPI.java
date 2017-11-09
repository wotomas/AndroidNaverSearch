package info.kimjihyok.androidnaversearch.controller.retrofit;

import info.kimjihyok.androidnaversearch.model.ImageResult;
import info.kimjihyok.androidnaversearch.model.SearchResult;
import info.kimjihyok.androidnaversearch.model.WebResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public interface NaverSearchAPI {
  @GET("/v1/search/webkr?sort=sim")
  Observable<SearchResult<WebResult>> webSearchList(@Query("query") String queryText, @Query("display") int size, @Query("start") int page);

  @GET("/v1/search/image?sort=sim")
  Observable<SearchResult<ImageResult>> imageSearchList(@Query("query") String queryText, @Query("display") int size, @Query("start") int page);
}
