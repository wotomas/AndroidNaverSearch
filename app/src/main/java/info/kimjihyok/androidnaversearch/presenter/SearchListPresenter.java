package info.kimjihyok.androidnaversearch.presenter;

import android.util.Log;

import info.kimjihyok.androidnaversearch.BuildConfig;
import info.kimjihyok.androidnaversearch.Config;
import info.kimjihyok.androidnaversearch.Util;
import info.kimjihyok.androidnaversearch.adapter.ListInterface;
import info.kimjihyok.androidnaversearch.base.BasePresenter;
import info.kimjihyok.androidnaversearch.base.SearchAction;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */
public class SearchListPresenter implements BasePresenter<SearchListPresenter.View> {
  private static final String TAG = "SearchListPresenter";

  private ReactiveEntityStore<Persistable> requeryController;
  private CompositeDisposable compositeDisposable;
  private ListInterface listInterface;
  private ApiController apiController;
  private SearchAction searchAction;
  private int currentSearchMode;

  public interface View {
    Observable<Boolean> onPullToRefreshGesture();
    Observable<Integer> onLoadMore();
    Observable<String> getQueryText();

    void storeLastQueryTextInMemory(String text);
    void showRefreshSpinner(boolean shouldShow);
    void showToast(String text);
  }

  public SearchListPresenter(ListInterface listInterface
      , ApiController apiController
      , SearchAction searchAction
      , int currentSearchMode
      , ReactiveEntityStore<Persistable> requeryController) {
    this.listInterface = listInterface;
    this.apiController = apiController;
    this.searchAction = searchAction;
    this.currentSearchMode = currentSearchMode;
    this.requeryController = requeryController;
  }

  @Override
  public void attachView(View view) {
    compositeDisposable = new CompositeDisposable();

    // search and refresh
    compositeDisposable.add(
        Observable.merge(
                searchAction.textSearchObservable().doOnNext(view::storeLastQueryTextInMemory),
                view.onPullToRefreshGesture().flatMap(v -> view.getQueryText()))
            .doOnNext(v -> listInterface.clear())
            .doOnNext(query -> view.showRefreshSpinner(!Util.isEmpty(query)))
            .filter(query -> !Util.isEmpty(query))
            .flatMap(query -> {
              if (currentSearchMode == Config.WEB_SEARCH_TAB) {
                return apiController.getWebSearchList(query, 20, 1)
                    .flatMap(webResultSearchResult -> Observable.fromIterable(webResultSearchResult.getItems()));
              } else {
                return apiController.getImageSearchList(query, 40, 1)
                    .flatMap(imageResultSearchResult -> Observable.fromIterable(imageResultSearchResult.getItems()));
              }
            })
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext -> {
              view.showRefreshSpinner(false);
              listInterface.add(onNext);
            }, onError -> {
              view.showRefreshSpinner(false);
              view.showToast("Error!");
              if (BuildConfig.DEBUG) Log.e(TAG, "attachView() ", onError);
            }));


    compositeDisposable.add(
        view.onLoadMore()
            .flatMap(page -> view.getQueryText()
                .filter(query -> !Util.isEmpty(query))
                .observeOn(Schedulers.io())
                .flatMap(query -> {
                  if (currentSearchMode == Config.WEB_SEARCH_TAB) {
                    return apiController.getWebSearchList(query, 40, page);
                  } else {
                    return apiController.getImageSearchList(query, 40, page);
                  }
                }))
            .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems()))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext -> listInterface.add(onNext), onError -> {
              view.showToast("Error!");
              if (BuildConfig.DEBUG) Log.e(TAG, "attachView() ", onError);
            }));
  }

  @Override
  public void detachView() {
    compositeDisposable.clear();
  }

}
