package info.kimjihyok.androidnaversearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.kimjihyok.androidnaversearch.R;
import info.kimjihyok.androidnaversearch.controller.NavigationController;
import info.kimjihyok.androidnaversearch.Util;
import info.kimjihyok.androidnaversearch.model.WebResult;

/**
 * Created by jkimab on 2017. 11. 8..
 */

public class WebSearchListAdapter extends RecyclerView.Adapter<WebSearchListAdapter.WebItemViewHolder> implements ListInterface<WebResult> {
  private List<WebResult> list;
  private Context context;
  private NavigationController navigationController;

  public WebSearchListAdapter(List<WebResult> list, Context context, NavigationController navigationController) {
    this.list = list;
    this.context = context;
    this.navigationController = navigationController;
  }

  @Override
  public WebItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View contactView = LayoutInflater.from(context).inflate(R.layout.view_holder_web_search, parent, false);
    return new WebItemViewHolder(contactView);
  }

  @Override
  public void onBindViewHolder(WebItemViewHolder holder, int position) {
    WebResult searchResults = list.get(position);

    holder.searchTitleTextView.setText(Util.getFormattedSpannable(searchResults.getTitle()));
    holder.searchDetailTextView.setText(Util.getFormattedSpannable(searchResults.getDescription()));
    holder.searchURLTextView.setText(searchResults.getLink());
    holder.searchURLTextView.setOnClickListener(v -> navigationController.openUrl(searchResults.getLink()));
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  @Override
  public void addAll(List<WebResult> items) {
    int originalSize = this.list.size();
    this.list.addAll(items);
    notifyItemRangeInserted(originalSize, items.size());
  }

  @Override
  public void add(WebResult WebResult) {
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

  public class WebItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.search_result_title) TextView searchTitleTextView;
    @BindView(R.id.search_result_detail) TextView searchDetailTextView;
    @BindView(R.id.search_result_url) TextView searchURLTextView;

    public WebItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
