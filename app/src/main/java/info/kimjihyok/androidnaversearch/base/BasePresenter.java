package info.kimjihyok.androidnaversearch.base;

/**
 * Created by jkimab on 2017. 11. 6..
 */
public interface BasePresenter<V> {
  void attachView(V view);
  void detachView();
}
