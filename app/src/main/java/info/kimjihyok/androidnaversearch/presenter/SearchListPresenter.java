package info.kimjihyok.androidnaversearch.presenter;

import android.util.Log;

import info.kimjihyok.androidnaversearch.BuildConfig;
import info.kimjihyok.androidnaversearch.adapter.ListInterface;
import info.kimjihyok.androidnaversearch.base.BasePresenter;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */
public class SearchListPresenter implements BasePresenter<SearchListPresenter.View> {
  private static final String TAG = "SearchListPresenter";

  private CompositeDisposable compositeDisposable;
  private ListInterface listInterface;
  private ApiController apiController;


  public interface View {
    Observable<Void> onSwipeGesture();
    Observable<Void> onLoadMore();

    void showRefreshSpinner(boolean shouldShow);
  }

  public SearchListPresenter(ListInterface listInterface, ApiController apiController) {
    this.listInterface = listInterface;
    this.apiController = apiController;
  }

  @Override
  public void attachView(View view) {
    compositeDisposable = new CompositeDisposable();

    compositeDisposable.add(
        apiController.getWebSearchList("query", 20, 1)
            .flatMap(webSearchResult -> Observable.fromIterable(webSearchResult.getItems()))   // insert to DB or smthing
                .subscribe(onNext -> {
                  listInterface.add(onNext);
                }, onError -> {
                  if (BuildConfig.DEBUG) {
                    Log.e(TAG, "attachView() ", onError);
                  }
                })
    );
  }

  @Override
  public void detachView() {
    compositeDisposable.clear();
  }

}
