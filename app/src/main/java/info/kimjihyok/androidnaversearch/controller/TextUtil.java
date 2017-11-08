package info.kimjihyok.androidnaversearch.controller;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public class TextUtil {

  public static Spanned getFormattedSpannable(String originalText) {
    Spanned spanned;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      spanned = Html.fromHtml(originalText, Html.FROM_HTML_MODE_LEGACY);
    } else {
      spanned = Html.fromHtml(originalText);
    }

    return spanned;
  }
}
