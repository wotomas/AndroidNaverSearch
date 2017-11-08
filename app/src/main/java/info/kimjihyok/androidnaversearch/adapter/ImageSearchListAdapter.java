package info.kimjihyok.androidnaversearch.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.kimjihyok.androidnaversearch.R;
import info.kimjihyok.androidnaversearch.controller.Util;
import info.kimjihyok.androidnaversearch.controller.model.ImageResult;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */


public class ImageSearchListAdapter extends RecyclerView.Adapter<ImageSearchListAdapter.ImageItemViewHolder> implements ListInterface<ImageResult> {
  private List<ImageResult> list;
  private Context context;

  public ImageSearchListAdapter(List<ImageResult> list, Context context) {
    this.list = list;
    this.context = context;
  }

  @Override
  public ImageItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View contactView = LayoutInflater.from(context).inflate(R.layout.view_holder_image_search, parent, false);
    return new ImageItemViewHolder(contactView);
  }

  @Override
  public void onBindViewHolder(ImageItemViewHolder holder, int position) {
    ImageResult searchResults = list.get(position);

    holder.imageSearchTitle.setText(Util.getFormattedSpannable(searchResults.getTitle()));
    Picasso.with(context).load(searchResults.getThumbnailURL())
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.load_error_image)
        .resize(Util.getScreenWidth((Activity) context), searchResults.getImageHeight())
        .centerInside()
        .into(holder.imageSearchThumbnail);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  @Override
  public void addAll(List<ImageResult> items) {
    int originalSize = this.list.size();
    this.list.addAll(items);
    notifyItemRangeInserted(originalSize, items.size());
  }

  @Override
  public void add(ImageResult WebResult) {
    int originalSize = this.list.size();
    this.list.add(WebResult);
    notifyItemInserted(originalSize + 1);
  }

  @Override
  public void remove(int position) {
    this.list.remove(position);
    notifyItemRemoved(position);
  }

  @Override
  public void clear() {
    this.list.clear();
    notifyDataSetChanged();
  }

  public class ImageItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_search_thumbnail) ImageView imageSearchThumbnail;
    @BindView(R.id.image_search_title) TextView imageSearchTitle;

    public ImageItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}