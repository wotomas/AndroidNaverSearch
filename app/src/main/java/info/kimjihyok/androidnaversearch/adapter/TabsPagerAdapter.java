package info.kimjihyok.androidnaversearch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.kimjihyok.androidnaversearch.Config;
import info.kimjihyok.androidnaversearch.view.SearchListFragment;


/**
 * Created by jkimab on 2017. 11. 7..
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
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
    return position == Config.WEB_SEARCH_TAB ? "WEB SEARCH" : "IMAGE SEARCH";
  }
}
