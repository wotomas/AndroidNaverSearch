package info.kimjihyok.androidnaversearch.dagger.component;

import dagger.Component;
import info.kimjihyok.androidnaversearch.base.BaseActivity;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.dagger.scope.ActivityScope;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

@ActivityScope
@Component(
    dependencies = {
        ApplicationComponent.class,
    }
//    , modules = {
//    }
)
public interface ActivityComponent {
  void inject(BaseActivity baseActivity);
}
