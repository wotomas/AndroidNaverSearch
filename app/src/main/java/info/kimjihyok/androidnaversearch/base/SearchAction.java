package info.kimjihyok.androidnaversearch.base;

import io.reactivex.Observable;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public interface SearchAction {
  Observable<String> textSearchObservable();
  Observable<String> getCurrentString();
  Observable<Boolean> clearSearchObservable();
}
