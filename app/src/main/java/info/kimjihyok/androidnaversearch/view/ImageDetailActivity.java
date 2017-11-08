package info.kimjihyok.androidnaversearch.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.kimjihyok.androidnaversearch.R;
import info.kimjihyok.androidnaversearch.adapter.ImagePagerAdapter;
import info.kimjihyok.androidnaversearch.base.BaseActivity;

/**
 * Created by jkimab on 2017. 11. 8..
 */

public class ImageDetailActivity extends BaseActivity {
  private static final String TAG = "ImageDetailActivity";
  public static final String WEB_RESULT_KEY = "url_key";
  public static final String POSITION_KEY = "position";

  @BindView(R.id.image_pager) ViewPager viewPager;
  @BindView(R.id.right_arrow) ImageView rightArrow;
  @BindView(R.id.left_arrow) ImageView leftArrow;
  private ImagePagerAdapter imagePagerAdapter;
  private int currentPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_detail);
    ButterKnife.bind(this);

    currentPosition = getIntent().getIntExtra(POSITION_KEY, 0);
    imagePagerAdapter = new ImagePagerAdapter(this, getIntent().getParcelableArrayListExtra(WEB_RESULT_KEY));

    viewPager.setAdapter(imagePagerAdapter);
    viewPager.setCurrentItem(currentPosition);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //TODO: call API and add to adapter, sync data with presenter, etc
      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }
}
