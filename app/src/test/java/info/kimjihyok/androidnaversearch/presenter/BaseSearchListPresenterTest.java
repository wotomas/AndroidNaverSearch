package info.kimjihyok.androidnaversearch.presenter;

import android.support.annotation.VisibleForTesting;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.kimjihyok.androidnaversearch.adapter.ListInterface;
import info.kimjihyok.androidnaversearch.base.SearchAction;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.controller.model.ImageResult;
import info.kimjihyok.androidnaversearch.controller.model.SearchResult;
import info.kimjihyok.androidnaversearch.controller.model.WebResult;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by jkimab on 2017. 11. 9..
 */
public abstract class BaseSearchListPresenterTest<T> {
  SearchListPresenter presenter;
  @Mock SearchListPresenter.View view;
  @Mock ListInterface listInterface;
  @Mock ApiController apiController;
  @Mock SearchAction searchAction;

  private T typeObject;
  private int currentSearchMode;

  PublishSubject<String> textSearchObservable = PublishSubject.create();
  PublishSubject<Boolean> clearSearchObservable = PublishSubject.create();
  PublishSubject<Boolean> pullToRefreshObservable = PublishSubject.create();
  PublishSubject<Integer> loadMoreObservable = PublishSubject.create();
  PublishSubject<String> queryTextObservable = PublishSubject.create();

  public static final String VALID_QUERY = "valid_query";
  public static final String INVALID_QUERY = "invalid_query";


  @BeforeClass
  public static void setUpRxSchedulers() {
    Scheduler immediate = new Scheduler() {
      @Override
      public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
        // this prevents StackOverflowErrors when scheduling with a delay
        return super.scheduleDirect(run, 0, unit);
      }

      @Override
      public Worker createWorker() {
        return new ExecutorScheduler.ExecutorWorker(Runnable::run);
      }
    };

    RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(searchAction.textSearchObservable()).thenReturn(textSearchObservable);
    when(searchAction.clearSearchObservable()).thenReturn(clearSearchObservable);

    //TODO: change to dummy data
    when(apiController.getImageSearchList(eq(VALID_QUERY), anyInt(), anyInt())).thenReturn(getValidImageSearchObservable());
    when(apiController.getImageSearchList(eq(INVALID_QUERY), anyInt(), anyInt())).thenReturn(null);
    when(apiController.getWebSearchList(eq(VALID_QUERY), anyInt(), anyInt())).thenReturn(getValidWebSearchObservable());
    when(apiController.getWebSearchList(eq(INVALID_QUERY), anyInt(), anyInt())).thenReturn(null);

    when(view.onPullToRefreshGesture()).thenReturn(pullToRefreshObservable);
    when(view.onLoadMore()).thenReturn(loadMoreObservable);

  }

  @VisibleForTesting
  private Observable<SearchResult<WebResult>> getValidWebSearchObservable() {
    SearchResult<WebResult> webSearchResult = new SearchResult<>();
    webSearchResult.setDisplayCount(10);
    webSearchResult.setLastBuildDate("");
    webSearchResult.setTotalPageCount(100);
    webSearchResult.setStartPageCount(1);
    List<WebResult> fakeWebResultList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      fakeWebResultList.add(new WebResult());
    }
    webSearchResult.setItems(fakeWebResultList);

    return Observable.just(webSearchResult);
  }

  @VisibleForTesting
  private Observable<SearchResult<ImageResult>> getValidImageSearchObservable() {
    SearchResult<ImageResult> imageSearchResult = new SearchResult<>();
    imageSearchResult.setDisplayCount(10);
    imageSearchResult.setLastBuildDate("");
    imageSearchResult.setTotalPageCount(100);
    imageSearchResult.setStartPageCount(1);
    List<ImageResult> fakeImageResultList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      fakeImageResultList.add(new ImageResult());
    }
    imageSearchResult.setItems(fakeImageResultList);

    return Observable.just(imageSearchResult);
  }

  @After
  public void tearDown() throws Exception {
    presenter.detachView();
    presenter = null;
  }
}