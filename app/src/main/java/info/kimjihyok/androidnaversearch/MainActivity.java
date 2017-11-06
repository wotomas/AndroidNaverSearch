package info.kimjihyok.androidnaversearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jkimab on 2017. 11. 6..
 */

public class MainActivity extends AppCompatActivity {
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
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_view_menu, menu);
    MenuItem searchViewItem = menu.findItem(R.id.action_search);

    final SearchView searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();
    searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit(): " + query);
        searchViewAndroidActionBar.clearFocus();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        // do something, like changing view state to searching...
        return false;
      }
    });

    return super.onCreateOptionsMenu(menu);
  }


}
