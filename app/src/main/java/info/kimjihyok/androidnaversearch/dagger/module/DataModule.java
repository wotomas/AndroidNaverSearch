package info.kimjihyok.androidnaversearch.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.kimjihyok.androidnaversearch.BuildConfig;
import info.kimjihyok.androidnaversearch.model.Models;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Created by jkimab on 2017. 11. 9..
 */


@Module
public class DataModule {
  Application application;

  public DataModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  ReactiveEntityStore<Persistable> providesRequeryController() {
    // override onUpgrade to handle migrating to a new version
    DatabaseSource source = new DatabaseSource(application, Models.DEFAULT, 1);
    if (BuildConfig.DEBUG) source.setTableCreationMode(TableCreationMode.DROP_CREATE);

    Configuration configuration = source.getConfiguration();
    return ReactiveSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));
  }
}
