package info.kimjihyok.androidnaversearch.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jkimab on 2017. 11. 6..
 */


public class NavigationController {
  private Activity activity;

  public NavigationController(Activity activity) {
    this.activity = activity;
  }

  public void openUrl(@NonNull String url) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    activity.startActivity(browserIntent);
  }

  public void openImageDetailScreen(Activity activity, String imageKey) {
    throw new IllegalStateException("Method not implemented!");
  }
}
