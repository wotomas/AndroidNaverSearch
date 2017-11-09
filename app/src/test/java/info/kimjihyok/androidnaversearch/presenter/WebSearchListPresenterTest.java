package info.kimjihyok.androidnaversearch.presenter;

import org.junit.Before;
import org.junit.Test;

import info.kimjihyok.androidnaversearch.Config;
import info.kimjihyok.androidnaversearch.controller.model.ImageResult;
import info.kimjihyok.androidnaversearch.controller.model.WebResult;
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

public class WebSearchListPresenterTest extends BaseSearchListPresenterTest<ImageResult> {
  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    presenter = new SearchListPresenter(listInterface, apiController, searchAction, Config.WEB_SEARCH_TAB);
  }

  @Test
  public void whenUserScrollReachesEnd_shouldCallMoreApiAndUpdate() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    loadMoreObservable.onNext(1);

    verify(apiController, times(1)).getWebSearchList(anyString(), anyInt(), anyInt());
    verify(listInterface, atLeastOnce()).add(any(WebResult.class));
  }

  @Test
  public void givenValidQueryText_whenUserPullsToRefresh_shouldCallImageSearchAPI() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    pullToRefreshObservable.onNext(true);

    verify(apiController, times(1)).getWebSearchList(anyString(), anyInt(), anyInt());
  }

  @Test
  public void givenInValidQueryText_whenUserPullsToRefresh_shouldStopSpinnerAndShowErrorToast() throws Exception {
    when(view.getQueryText()).thenReturn(Observable.just(VALID_QUERY));
    presenter.attachView(view);

    pullToRefreshObservable.onNext(true);

    verify(listInterface, atLeastOnce()).add(any(WebResult.class));
  }

}
