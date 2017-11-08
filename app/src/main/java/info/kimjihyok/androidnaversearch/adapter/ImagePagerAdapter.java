package info.kimjihyok.androidnaversearch.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.kimjihyok.androidnaversearch.R;
import info.kimjihyok.androidnaversearch.controller.model.ImageResult;

/**
 * Created by jkimab on 2017. 11. 8..
 */

public class ImagePagerAdapter extends PagerAdapter implements ListInterface<ImageResult>{
  private static final String TAG = "ImagePagerAdapter";
  private Context context;
  private List<ImageResult> urls;

  public ImagePagerAdapter(Context context, List<ImageResult> urls) {
    this.context = context;
    this.urls = urls;
  }

  @Override
  public int getCount() {
    return urls.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup collection, int position) {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.view_image_frame, collection, false);
    ImageView imagePanel = convertView.findViewById(R.id.image_panel);

    Picasso.with(context).load(urls.get(position).getThumbnailURL())
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.load_error_image)
        .fit()
        .centerCrop()
        .into(imagePanel);

    collection.addView(convertView);
    return convertView;
  }

  @Override
  public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  @Override
  public void addAll(List<ImageResult> items) {
    this.urls.addAll(items);
    notifyDataSetChanged();
  }

  @Override
  public void add(ImageResult WebResult) {
    this.urls.add(WebResult);
    notifyDataSetChanged();
  }

  public void addToFront(ImageResult WebResult) {
    this.urls.add(0, WebResult);
    notifyDataSetChanged();
  }

  @Override
  public void remove(int position) {
    this.urls.remove(position);
    notifyDataSetChanged();
  }

  @Override
  public void clear() {
    this.urls.clear();
    notifyDataSetChanged();
  }
}
