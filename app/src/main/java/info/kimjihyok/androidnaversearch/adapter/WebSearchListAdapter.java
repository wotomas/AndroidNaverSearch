package info.kimjihyok.androidnaversearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.kimjihyok.androidnaversearch.R;
import info.kimjihyok.androidnaversearch.controller.TextUtil;
import info.kimjihyok.androidnaversearch.controller.model.WebResult;

/**
 * Created by jkimab on 2017. 11. 8..
 */

public class WebSearchListAdapter extends RecyclerView.Adapter<WebSearchListAdapter.WebItemViewHolder> implements ListInterface<WebResult> {
  private List<WebResult> list;
  private Context context;

  public WebSearchListAdapter(List<WebResult> list, Context context) {
    this.list = list;
    this.context = context;
  }

  @Override
  public WebItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View contactView = LayoutInflater.from(context).inflate(R.layout.view_holder_web_search, parent, false);
    return new WebItemViewHolder(contactView);
  }

  @Override
  public void onBindViewHolder(WebItemViewHolder holder, int position) {
    WebResult searchResults = list.get(position);

    holder.searchTitleTextView.setText(TextUtil.getFormattedSpannable(searchResults.getTitle()));
    holder.searchDetailTextView.setText(TextUtil.getFormattedSpannable(searchResults.getDescription()));
    holder.searchURLTextView.setText(searchResults.getLink());
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
