package info.kimjihyok.androidnaversearch.controller.retrofit;

import java.util.Map;

import info.kimjihyok.androidnaversearch.controller.model.ImageResult;
import info.kimjihyok.androidnaversearch.controller.model.SearchResult;
import info.kimjihyok.androidnaversearch.controller.model.WebResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public interface NaverSearchAPI {
  @GET("/v1/search/webkr?sort=sim")
  Observable<SearchResult<WebResult>> webSearchList(@Query("query") String queryText, @Query("display") int size, @Query("start") int page);

  @GET("/v1/search/image?sort=sim")
  Observable<SearchResult<ImageResult>> imageSearchList(@Query("query") String queryText, @Query("display") int size, @Query("start") int page);
}
