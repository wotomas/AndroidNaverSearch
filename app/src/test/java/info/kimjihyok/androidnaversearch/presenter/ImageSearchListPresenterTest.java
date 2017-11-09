package info.kimjihyok.androidnaversearch.presenter;

import org.junit.Before;
import org.junit.Test;

import info.kimjihyok.androidnaversearch.Config;
import info.kimjihyok.androidnaversearch.controller.model.ImageResult;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jkimab on 2017. 11. 9..
 */

public class ImageSearchListPresenterTest extends BaseSearchListPresenterTest<ImageResult> {
  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    presenter = new SearchListPresenter(listInterface, apiController, searchAction, Config.IMAGE_SEARCH_TAB);
  }

  @Test
  public void givenValidSearchText_whenUserClicksSearch_shouldClearListOnce() throws Exception {
    presenter.attachView(view);
    textSearchObservable.onNext(VALID_QUERY);

    verify(listInterface, times(1)).clear();
  }

  @Test
  public void givenInValidSearchText_whenUserClicksSearch_shouldClearListOnce() throws Exception {
    presenter.attachView(view);
    textSearchObservable.onNext(INVALID_QUERY);

    verify(listInterface, times(1)).clear();
  }


  @Test
  public void whenUserPullsToRefresh_shouldClearListOnce() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    pullToRefreshObservable.onNext(true);

    verify(listInterface, times(1)).clear();
  }

  @Test
  public void givenQueryTextIsEmpty_whenUserPullsToRefresh_shouldNotShowSpinner() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(""));
    presenter.attachView(view);

    pullToRefreshObservable.onNext(true);

    verify(view, times(1)).showRefreshSpinner(false);
  }

  @Test
  public void whenUserScrollReachesEnd_shouldCallMoreApiAndUpdate() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    loadMoreObservable.onNext(1);

    verify(apiController, times(1)).getImageSearchList(anyString(), anyInt(), anyInt());
    verify(listInterface, atLeastOnce()).add(any(ImageResult.class));
  }

  @Test
  public void givenValidQueryText_whenUserPullsToRefresh_shouldCallImageSearchAPI() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    pullToRefreshObservable.onNext(true);

    verify(apiController, times(1)).getImageSearchList(anyString(), anyInt(), anyInt());
  }

  @Test
  public void givenInValidQueryText_whenUserPullsToRefresh_shouldStopSpinnerAndShowErrorToast() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    pullToRefreshObservable.onNext(true);

    verify(listInterface, atLeastOnce()).add(any(ImageResult.class));
  }

}
