package info.kimjihyok.androidnaversearch.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import info.kimjihyok.androidnaversearch.BuildConfig;
import info.kimjihyok.androidnaversearch.Config;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.controller.retrofit.NaverSearchAPI;
import info.kimjihyok.androidnaversearch.dagger.component.ApplicationComponent;
import info.kimjihyok.androidnaversearch.dagger.component.DaggerApplicationComponent;
import info.kimjihyok.androidnaversearch.dagger.module.NetworkModule;
import retrofit2.Retrofit;

/**
 * Created by jkimab on 2017. 11. 6..
 */

public class BaseApplication extends Application {
  private static final String TAG = "BaseApplication";

  private static ApplicationComponent applicationComponent;
  // inject DB related stuff here

  @Override
  public void onCreate() {
    super.onCreate();

    if(BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }

    applicationComponent = DaggerApplicationComponent
        .builder().networkModule(new NetworkModule())
        .build();

    applicationComponent.inject(this);
  }

  public static ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
