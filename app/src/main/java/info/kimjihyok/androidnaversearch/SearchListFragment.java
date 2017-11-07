package info.kimjihyok.androidnaversearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.kimjihyok.androidnaversearch.adapter.WebSearchListAdapter;
import info.kimjihyok.androidnaversearch.base.BaseEndlessRecyclerViewScrollListener;
import info.kimjihyok.androidnaversearch.model.WebSearch;

/**
 * Created by jkimab on 2017. 11. 7..
 */

public class SearchListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
  private static final String SEARCH_TYPE_KEY = "search_type";

  // move
  public static final int WEB_SEARCH_TAB = 0;
  public static final int IMAGE_SEARCH_TAB = 1;

  @BindView(R.id.search_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_container) SwipeRefreshLayout swipeRefreshLayout;

  private RecyclerView.LayoutManager layoutManager;
  private int searchViewType;
  private Unbinder unbinder;
  private RecyclerView.Adapter adapter;

  public static SearchListFragment newInstance(int page) {
    SearchListFragment fragment = new SearchListFragment();
    Bundle args = new Bundle();
    args.putInt(SEARCH_TYPE_KEY, page);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      searchViewType = getArguments().getInt(SEARCH_TYPE_KEY, WEB_SEARCH_TAB);
    }

    // TODO: check view type and set adapter accordingly
    adapter = new WebSearchListAdapter(getFakeData(), getContext());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_search_list, container, false);
    unbinder = ButterKnife.bind(this, rootView);

    layoutManager = searchViewType == WEB_SEARCH_TAB ? new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 2);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addOnScrollListener(new BaseEndlessRecyclerViewScrollListener(layoutManager, 5) {
      @Override
      public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        // do load more! hide pull to refresh and add more fake data
        swipeRefreshLayout.setRefreshing(false);
        ((WebSearchListAdapter) adapter).addItems(getFakeData());\
      }
    });

    recyclerView.setAdapter(adapter);

    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

    swipeRefreshLayout.post(new Runnable() {
      @Override
      public void run() {
        swipeRefreshLayout.setRefreshing(true); // remove this method when linking to presenter
      }
    });

    return rootView;
  }


  private List<WebSearch> getFakeData() {
    List<WebSearch> fakeData = new ArrayList<>();
    for(int i = 0; i < 20; i++) {
      fakeData.add(new WebSearch());
    }

    return fakeData;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onRefresh() {
    // trigger event in presenter
  }
}
