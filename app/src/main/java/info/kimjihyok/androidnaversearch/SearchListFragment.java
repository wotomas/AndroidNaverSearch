package info.kimjihyok.androidnaversearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
  private static final String TAG = "SearchListFragment";
  private static final String SEARCH_TYPE_KEY = "search_type";
  @BindView(R.id.search_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.swipe_container) SwipeRefreshLayout swipeRefreshLayout;
  private int searchViewType;

  private RecyclerView.LayoutManager layoutManager;
  private SearchListPresenter presenter;
  private ListInterface adapter;
  private Unbinder unbinder;

  private String currentQueryString = "";

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
    setRetainInstance(true);

    if (getArguments() != null) {
      searchViewType = getArguments().getInt(SEARCH_TYPE_KEY, Config.WEB_SEARCH_TAB);
    }

    if (searchViewType == Config.WEB_SEARCH_TAB) {
      adapter = new WebSearchListAdapter(new ArrayList<>(), getContext(), ((BaseActivity) getActivity()).getNavigationController());
    } else {
      adapter = new ImageSearchListAdapter(new ArrayList<>(), getContext());
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_search_list, container, false);
    unbinder = ButterKnife.bind(this, rootView);
    refreshSubject = PublishSubject.create();
    loadMoreSubject = PublishSubject.create();
    layoutManager = searchViewType == Config.WEB_SEARCH_TAB ? new LinearLayoutManager(getContext()) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addOnScrollListener(new BaseEndlessRecyclerViewScrollListener(layoutManager, 5) {
      @Override
      public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        int nextStartItem;
        if (searchViewType == Config.WEB_SEARCH_TAB) {
          nextStartItem = page * 20 + 1;
        } else {
          nextStartItem = page * 40 + 1;
        }

        loadMoreSubject.onNext(nextStartItem);
      }
    });
    recyclerView.setAdapter((RecyclerView.Adapter) adapter);

    if (layoutManager instanceof LinearLayoutManager) {
      recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation()));
    }

    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

    return rootView;
  }

  @Override
  public void onStart() {
    super.onStart();

    if (presenter == null) {  // this null check and presenter creation is due to rotation handling
      presenter = new SearchListPresenter(adapter
          , ((BaseActivity) getActivity()).getApiController()
          , ((BaseActivity) getActivity()).getSearchAction()
          , searchViewType);
    }

    presenter.attachView(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.detachView();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    refreshSubject = null;
    loadMoreSubject = null;
    presenter = null;
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
  public Observable<String> getQueryText() {
    return Observable.defer(() -> Observable.just(currentQueryString));
  }

  @Override
  public void storeLastQueryTextInMemory(String text) {
    this.currentQueryString = text;
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
