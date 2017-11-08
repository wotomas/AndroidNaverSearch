package info.kimjihyok.androidnaversearch.dagger.module;

import dagger.Module;
import dagger.Provides;
import info.kimjihyok.androidnaversearch.base.BaseActivity;
import info.kimjihyok.androidnaversearch.controller.NavigationController;
import info.kimjihyok.androidnaversearch.dagger.scope.ActivityScope;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

@Module
public class ActivityModule {
  BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides
  @ActivityScope
  NavigationController providesNavigationHelper() {
    return new NavigationController(activity);
  }
}
