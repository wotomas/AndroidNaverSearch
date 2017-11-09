package info.kimjihyok.androidnaversearch.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import info.kimjihyok.androidnaversearch.view.ImageDetailActivity;
import info.kimjihyok.androidnaversearch.model.ImageResult;

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

  public void openImageDetailScreen(int position, ArrayList<ImageResult> currentList) {
    if (currentList == null || currentList.isEmpty()) {
      throw new IllegalStateException("detail screen navigation should never get null as parameters!");
    }

    Intent intent = new Intent(activity, ImageDetailActivity.class);
    intent.putExtra(ImageDetailActivity.POSITION_KEY, position);
    intent.putParcelableArrayListExtra(ImageDetailActivity.WEB_RESULT_KEY, currentList);
    activity.startActivity(intent);
  }
}
