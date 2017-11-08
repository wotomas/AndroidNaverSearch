package info.kimjihyok.androidnaversearch.presenter;

import android.text.TextUtils;
import android.util.Log;

import info.kimjihyok.androidnaversearch.BuildConfig;
import info.kimjihyok.androidnaversearch.adapter.ListInterface;
import info.kimjihyok.androidnaversearch.base.BasePresenter;
import info.kimjihyok.androidnaversearch.base.SearchAction;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.controller.TextUtil;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */
public class SearchListPresenter implements BasePresenter<SearchListPresenter.View> {
  private static final String TAG = "SearchListPresenter";

  private CompositeDisposable compositeDisposable;
  private ListInterface listInterface;
  private ApiController apiController;
  private SearchAction searchAction;

  public interface View {
    Observable<Boolean> onPullToRefreshGesture();
    Observable<Integer> onLoadMore();

    void showRefreshSpinner(boolean shouldShow);
    void showToast(String text);
  }

  public SearchListPresenter(ListInterface listInterface, ApiController apiController, SearchAction searchAction) {
    this.listInterface = listInterface;
    this.apiController = apiController;
    this.searchAction = searchAction;
  }

  @Override
  public void attachView(View view) {
    compositeDisposable = new CompositeDisposable();

    //TODO: refactor duplicated logics using connected observables
    compositeDisposable.add(
        searchAction.textSearchObservable()
            .doOnNext(query -> {
              if (query == null || TextUtils.isEmpty(query)) {
                view.showRefreshSpinner(false);
                view.showToast("Query is empty!");
              }
            })
            .filter(query -> query != null && !TextUtils.isEmpty(query))
            .doOnNext(v -> listInterface.clear())
            .doOnNext(v -> view.showRefreshSpinner(true))
            .observeOn(Schedulers.io())
            .flatMap(query -> apiController.getWebSearchList(query, 20, 1))
            .flatMap(webSearchResult -> Observable.fromIterable(webSearchResult.getItems()))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext -> {
                  view.showRefreshSpinner(false);
                  listInterface.add(onNext);
                }, onError -> {
                  view.showRefreshSpinner(false);
                  view.showToast("Error!");
                  if (BuildConfig.DEBUG) {
                    Log.e(TAG, "attachView() ", onError);
                  }
                }));

    compositeDisposable.add(
        view.onPullToRefreshGesture()
            .doOnNext(v -> listInterface.clear())
            .doOnNext(v -> view.showRefreshSpinner(true))
            .flatMap(v -> searchAction.getCurrentString())
            .doOnNext(query -> {
              if (query == null || TextUtils.isEmpty(query)) {
                view.showRefreshSpinner(false);
                view.showToast("Query is empty!");
              }
            })
            .filter(query -> query != null && !TextUtils.isEmpty(query))
            .observeOn(Schedulers.io())
            .flatMap(query -> apiController.getWebSearchList(query, 20, 1))
            .flatMap(webSearchResult -> Observable.fromIterable(webSearchResult.getItems()))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext -> {
              view.showRefreshSpinner(false);
              listInterface.add(onNext);
              }, onError -> {
                view.showRefreshSpinner(false);
                view.showToast("Error!");
                if (BuildConfig.DEBUG) {
                  Log.e(TAG, "attachView() ", onError);
                }
            }));
  }

  @Override
  public void detachView() {
    compositeDisposable.clear();
  }

}
