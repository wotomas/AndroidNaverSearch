package info.kimjihyok.androidnaversearch.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import info.kimjihyok.androidnaversearch.R;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.controller.NavigationController;
import info.kimjihyok.androidnaversearch.dagger.component.ActivityComponent;
import info.kimjihyok.androidnaversearch.dagger.component.DaggerActivityComponent;
import info.kimjihyok.androidnaversearch.dagger.module.ActivityModule;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseActivity extends AppCompatActivity implements SearchAction {
  @Inject ApiController apiController;
  @Inject NavigationController navigationController;

  private ActivityComponent activityComponent;
  private SearchView searchViewAndroidActionBar;
  private PublishSubject<String> textSearchSubject;
  private PublishSubject<Boolean> closeButtonSubject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activityComponent = DaggerActivityComponent.builder()
        .applicationComponent(BaseApplication.getApplicationComponent())
        .activityModule(new ActivityModule(this))
        .build();
    activityComponent.inject(this);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      onScreenChangeToLandscape();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      onScreenChangeToPortrait();
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    textSearchSubject = PublishSubject.create();
    closeButtonSubject = PublishSubject.create();
  }

  @Override
  protected void onStop() {
    super.onStop();
    textSearchSubject = null;
    closeButtonSubject = null;
  }

  @Override
  public Observable<String> textSearchObservable() {
    return textSearchSubject;
  }

  @Override
  public Observable<Boolean> clearSearchObservable() {
    return closeButtonSubject;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_view_menu, menu);
    MenuItem searchViewItem = menu.findItem(R.id.action_search);

    searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();
    searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        searchViewAndroidActionBar.clearFocus();
        textSearchSubject.onNext(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    searchViewAndroidActionBar.setOnClickListener(v -> {
      if (v.getId() == R.id.search_close_btn) {
        closeButtonSubject.onNext(true);
      }
    });

    return super.onCreateOptionsMenu(menu);
  }

  protected abstract void onScreenChangeToLandscape();

  protected abstract void onScreenChangeToPortrait();

  @Override
  public Observable<String> getCurrentString() {
    return Observable.defer(() -> Observable.just(searchViewAndroidActionBar.getQuery().toString()));
  }

  public SearchAction getSearchAction() {
    return this;
  }

  public NavigationController getNavigationController() {
    return navigationController;
  }

  public ApiController getApiController() {
    return apiController;
  }
}
