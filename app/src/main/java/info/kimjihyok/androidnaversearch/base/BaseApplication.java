package info.kimjihyok.androidnaversearch.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import info.kimjihyok.androidnaversearch.BuildConfig;

/**
 * Created by jkimab on 2017. 11. 6..
 */

public class BaseApplication extends Application {
  private static final String TAG = "BaseApplication";

  @Override
  public void onCreate() {
    super.onCreate();

    if(BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }
  }
}
