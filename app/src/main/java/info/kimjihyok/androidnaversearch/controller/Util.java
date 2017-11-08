package info.kimjihyok.androidnaversearch.controller;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

//TODO: split this class when it becomes unmaintainable
public class Util {

  public static Spanned getFormattedSpannable(String originalText) {
    Spanned spanned;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      spanned = Html.fromHtml(originalText, Html.FROM_HTML_MODE_LEGACY);
    } else {
      spanned = Html.fromHtml(originalText);
    }

    return spanned;
  }

  public static boolean isEmpty(@Nullable String string) {
    return string == null || string.length() == 0;
  }

  public static int getScreenWidth(Activity activity) {
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm.widthPixels;
  }
}
