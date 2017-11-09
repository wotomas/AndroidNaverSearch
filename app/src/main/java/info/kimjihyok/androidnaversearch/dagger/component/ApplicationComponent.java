package info.kimjihyok.androidnaversearch.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import info.kimjihyok.androidnaversearch.base.BaseApplication;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.dagger.module.DataModule;
import info.kimjihyok.androidnaversearch.dagger.module.NetworkModule;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */


@Singleton
@Component(modules = {
    NetworkModule.class, DataModule.class
})
public interface ApplicationComponent {
  void inject(BaseApplication baseApplication);

  ApiController getApiController();
  ReactiveEntityStore<Persistable> getRequeryController();
}
