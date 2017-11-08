package info.kimjihyok.androidnaversearch.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    textSearchSubject = PublishSubject.create();
    closeButtonSubject = PublishSubject.create();

    activityComponent = DaggerActivityComponent.builder()
        .applicationComponent(BaseApplication.getApplicationComponent())
        .activityModule(new ActivityModule(this))
        .build();
    activityComponent.inject(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    textSearchSubject = null;
    closeButtonSubject = null;
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

  @Override
  public Observable<String> textSearchObservable() {
    return textSearchSubject;
  }

  @Override
  public Observable<Boolean> clearSearchObservable() {
    return closeButtonSubject;
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
