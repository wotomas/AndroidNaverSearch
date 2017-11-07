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
import info.kimjihyok.androidnaversearch.model.WebSearch;

/**
 * Created by jkimab on 2017. 11. 8..
 */

public class WebSearchListAdapter extends RecyclerView.Adapter<WebSearchListAdapter.WebItemViewHolder> {
  private List<WebSearch> list;
  private Context context;

  public WebSearchListAdapter(List<WebSearch> list, Context context) {
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
    WebSearch searchResults = list.get(position);

    holder.searchTitleTextView.setText("temp 1");
    holder.searchDetailTextView.setText("temp 2");
    holder.searchURLTextView.setText("temp 3");
  }

  public void addItems(List<WebSearch> items) {
    int originalSize = this.list.size();
    this.list.addAll(items);
    notifyItemRangeInserted(originalSize, items.size());
  }

  @Override
  public int getItemCount() {
    return list.size();
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
