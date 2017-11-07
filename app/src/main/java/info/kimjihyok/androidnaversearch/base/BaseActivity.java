package info.kimjihyok.androidnaversearch.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.kimjihyok.androidnaversearch.R;

public abstract class BaseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      onScreenChangeToLandscape();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      onScreenChangeToPortrait();
    }
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
        searchViewAndroidActionBar.clearFocus();
        onTextSearch(query);
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

  protected abstract void onScreenChangeToLandscape();

  protected abstract void onScreenChangeToPortrait();

  protected abstract void onTextSearch(String query);
}
