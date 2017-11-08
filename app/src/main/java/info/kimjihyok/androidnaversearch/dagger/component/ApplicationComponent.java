package info.kimjihyok.androidnaversearch.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import info.kimjihyok.androidnaversearch.base.BaseApplication;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.dagger.module.NetworkModule;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */


@Singleton
@Component(modules = {
    NetworkModule.class
})
public interface ApplicationComponent {
  void inject(BaseApplication baseApplication);

  ApiController getApiController();
}
