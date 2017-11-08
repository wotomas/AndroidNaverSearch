package info.kimjihyok.androidnaversearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.kimjihyok.androidnaversearch.adapter.ImageSearchListAdapter;
import info.kimjihyok.androidnaversearch.adapter.ListInterface;
import info.kimjihyok.androidnaversearch.adapter.WebSearchListAdapter;
import info.kimjihyok.androidnaversearch.base.BaseActivity;
import info.kimjihyok.androidnaversearch.base.BaseEndlessRecyclerViewScrollListener;
import info.kimjihyok.androidnaversearch.presenter.SearchListPresenter;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jkimab on 2017. 11. 7..
 */

public class SearchListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchListPresenter.View {
  private static final String SEARCH_TYPE_KEY = "search_type";

  @BindView(R.id.search_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_container) SwipeRefreshLayout swipeRefreshLayout;

  private RecyclerView.LayoutManager layoutManager;
  private int searchViewType;
  private Unbinder unbinder;
  private ListInterface adapter;
  private SearchListPresenter presenter;

  private PublishSubject<Boolean> refreshSubject;
  private PublishSubject<Integer> loadMoreSubject;

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
      searchViewType = getArguments().getInt(SEARCH_TYPE_KEY, Config.WEB_SEARCH_TAB);
    }

    if (searchViewType == Config.WEB_SEARCH_TAB) {
      adapter = new WebSearchListAdapter(new ArrayList<>(), getContext());
    } else {
      adapter = new ImageSearchListAdapter(new ArrayList<>(), getContext());
    }

    presenter = new SearchListPresenter(adapter
        , ((BaseActivity) getActivity()).getApiController()
        , ((BaseActivity) getActivity()).getSearchAction()
        , searchViewType);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_search_list, container, false);
    unbinder = ButterKnife.bind(this, rootView);
    refreshSubject = PublishSubject.create();
    loadMoreSubject = PublishSubject.create();
    layoutManager = searchViewType == Config.WEB_SEARCH_TAB ? new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 2);

    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter((RecyclerView.Adapter) adapter);
    recyclerView.addOnScrollListener(new BaseEndlessRecyclerViewScrollListener(layoutManager, 5) {
      @Override
      public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        // do load more! hide pull to refresh and add more fake data
        loadMoreSubject.onNext(page);
      }
    });

    if (layoutManager instanceof LinearLayoutManager) {
      recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation()));
    }

    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

    presenter.attachView(this);
    return rootView;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    presenter.detachView();
    refreshSubject = null;
    loadMoreSubject = null;
  }

  @Override
  public void onRefresh() {
    refreshSubject.onNext(true);
  }

  @Override
  public Observable<Boolean> onPullToRefreshGesture() {
    return refreshSubject;
  }

  @Override
  public Observable<Integer> onLoadMore() {
    return loadMoreSubject;
  }

  @Override
  public void showRefreshSpinner(boolean shouldShow) {
    swipeRefreshLayout.setRefreshing(shouldShow);
  }

  @Override
  public void showToast(String text) {
    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
  }
}
