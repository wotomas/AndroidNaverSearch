package info.kimjihyok.androidnaversearch.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import info.kimjihyok.androidnaversearch.adapter.ListInterface;
import info.kimjihyok.androidnaversearch.base.SearchAction;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.controller.model.ImageResult;
import info.kimjihyok.androidnaversearch.controller.model.SearchResult;
import info.kimjihyok.androidnaversearch.controller.model.WebResult;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

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
  PublishSubject<SearchResult<ImageResult>> validImageSearchObservable = PublishSubject.create();
  PublishSubject<SearchResult<ImageResult>> invalidImageSearchObservable = PublishSubject.create();
  PublishSubject<SearchResult<WebResult>> validWebSearchObservable = PublishSubject.create();
  PublishSubject<SearchResult<WebResult>> invalidWebSearchObservable = PublishSubject.create();
  PublishSubject<Boolean> pullToRefreshObservable = PublishSubject.create();
  PublishSubject<Integer> loadMoreObservable = PublishSubject.create();PublishSubject<String> queryTextObservable = PublishSubject.create();

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
    when(apiController.getImageSearchList(eq(VALID_QUERY), anyInt(), anyInt())).thenReturn(validImageSearchObservable);
    when(apiController.getImageSearchList(eq(INVALID_QUERY), anyInt(), anyInt())).thenReturn(invalidImageSearchObservable);
    when(apiController.getWebSearchList(eq(VALID_QUERY), anyInt(), anyInt())).thenReturn(validWebSearchObservable);
    when(apiController.getWebSearchList(eq(INVALID_QUERY), anyInt(), anyInt())).thenReturn(invalidWebSearchObservable);

    when(view.onPullToRefreshGesture()).thenReturn(pullToRefreshObservable);
    when(view.onLoadMore()).thenReturn(loadMoreObservable);

  }

  @After
  public void tearDown() throws Exception {
    presenter.detachView();
    presenter = null;
  }
}