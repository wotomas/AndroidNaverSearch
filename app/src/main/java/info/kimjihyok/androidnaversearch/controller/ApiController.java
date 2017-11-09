package info.kimjihyok.androidnaversearch.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import info.kimjihyok.androidnaversearch.model.ImageResult;
import info.kimjihyok.androidnaversearch.model.SearchResult;
import info.kimjihyok.androidnaversearch.model.WebResult;
import info.kimjihyok.androidnaversearch.controller.retrofit.NaverSearchAPI;
import io.reactivex.Observable;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public class ApiController {
  public static final String TAG = "ApiManager";
  private NaverSearchAPI naverSearchAPI;
  private final int MAX_RETRIES;

  public ApiController(NaverSearchAPI naverSearchAPI, int maxRetries) {
    this.naverSearchAPI = naverSearchAPI;
    this.MAX_RETRIES = maxRetries;
  }

  public Observable<SearchResult<WebResult>> getWebSearchList(String queryText, int size, int page) {
    //Retry 3 times with exponential backoff interval, and if all fails, return empty observable
    return naverSearchAPI.webSearchList(queryText, size, page)
        .retryWhen(errors -> errors
            .scan(0, (errCount, throwable) -> {
              if (errCount > MAX_RETRIES)
                throw new IOException("Network Retry Finished 3 times! Network not available");
              return errCount + 1;
            })
            .flatMap(retryCount -> Observable.timer((long) Math.pow(4, retryCount), TimeUnit.SECONDS))
        );
  }

  public Observable<SearchResult<ImageResult>> getImageSearchList(String queryText, int size, int page) {
    //Retry 3 times with exponential backoff interval, and if all fails, return empty observable
    return naverSearchAPI.imageSearchList(queryText, size, page)
        .retryWhen(errors -> errors
            .scan(0, (errCount, throwable) -> {
              if (errCount > MAX_RETRIES)
                throw new IOException("Network Retry Finished 3 times! Network not available");
              return errCount + 1;
            })
            .flatMap(retryCount -> Observable.timer((long) Math.pow(4, retryCount), TimeUnit.SECONDS))
        );
  }
}
