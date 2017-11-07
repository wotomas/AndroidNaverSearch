package info.kimjihyok.androidnaversearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.kimjihyok.androidnaversearch.base.BaseActivity;

/**
 * Created by jkimab on 2017. 11. 6..
 */

public class MainActivity extends BaseActivity {
  private static final String TAG = "MainActivity";

  @BindView(R.id.pager) ViewPager viewPager;
  @BindView(R.id.tabs) TabLayout tabs;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
    tabs.setupWithViewPager(viewPager);
  }

  @Override
  protected void onScreenChangeToLandscape() {

  }

  @Override
  protected void onScreenChangeToPortrait() {

  }

  @Override
  protected void onTextSearch(String query) {

  }
}
