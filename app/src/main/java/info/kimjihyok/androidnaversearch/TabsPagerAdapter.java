package info.kimjihyok.androidnaversearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static info.kimjihyok.androidnaversearch.SearchListFragment.WEB_SEARCH_TAB;

/**
 * Created by jkimab on 2017. 11. 7..
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
  // TODO: move constants to seperate config class
  private static final int TOTAL_TABS = 2;


  public TabsPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return SearchListFragment.newInstance(position);
  }

  @Override
  public int getCount() {
    return TOTAL_TABS;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return position == WEB_SEARCH_TAB ? "WEB SEARCH" : "IMAGE SEARCH";
  }
}
