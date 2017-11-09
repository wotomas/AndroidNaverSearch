package info.kimjihyok.androidnaversearch.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import info.kimjihyok.androidnaversearch.BuildConfig;
import info.kimjihyok.androidnaversearch.dagger.component.ApplicationComponent;
import info.kimjihyok.androidnaversearch.dagger.component.DaggerApplicationComponent;
import info.kimjihyok.androidnaversearch.dagger.module.DataModule;
import info.kimjihyok.androidnaversearch.dagger.module.NetworkModule;

/**
 * Created by jkimab on 2017. 11. 6..
 */

public class BaseApplication extends Application {
  private static final String TAG = "BaseApplication";

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    if(BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }

    applicationComponent = DaggerApplicationComponent
        .builder()
        .networkModule(new NetworkModule())
        .dataModule(new DataModule(this))
        .build();

    applicationComponent.inject(this);
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

}
