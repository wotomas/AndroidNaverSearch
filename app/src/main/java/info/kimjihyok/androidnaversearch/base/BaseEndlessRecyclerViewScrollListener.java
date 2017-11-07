package info.kimjihyok.androidnaversearch.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jkimab on 2017. 11. 8..
 */

public abstract class BaseEndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
  private final int VISIBLE_THRESHOLD;

  private int currentPage = 0;
  private int previousTotalItemCount = 0;
  private boolean loading = true;
  private int startingPageIndex = 0;

  RecyclerView.LayoutManager mLayoutManager;

  public BaseEndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager, int threshold) {
    this.mLayoutManager = layoutManager;
    if (mLayoutManager instanceof LinearLayoutManager) {
      VISIBLE_THRESHOLD = threshold;
    } else if (mLayoutManager instanceof GridLayoutManager) {
      VISIBLE_THRESHOLD = threshold * ((GridLayoutManager) mLayoutManager).getSpanCount();
    } else {
      throw new IllegalStateException("Unsupported type of layout manager!");
    }
  }

  public int getLastVisibleItem(int[] lastVisibleItemPositions) {
    int maxSize = 0;
    for (int i = 0; i < lastVisibleItemPositions.length; i++) {
      if (i == 0) {
        maxSize = lastVisibleItemPositions[i];
      } else if (lastVisibleItemPositions[i] > maxSize) {
        maxSize = lastVisibleItemPositions[i];
      }
    }
    return maxSize;
  }

  @Override
  public void onScrolled(RecyclerView view, int dx, int dy) {
    int lastVisibleItemPosition = 0;
    int totalItemCount = mLayoutManager.getItemCount();

    if (mLayoutManager instanceof GridLayoutManager) {
      lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    } else if (mLayoutManager instanceof LinearLayoutManager) {
      lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    } else {
      throw new IllegalStateException("Unsupported type of layout manager!");
    }

    if (totalItemCount < previousTotalItemCount) {
      this.currentPage = this.startingPageIndex;
      this.previousTotalItemCount = totalItemCount;
      if (totalItemCount == 0) {
        this.loading = true;
      }
    }

    if (loading && (totalItemCount > previousTotalItemCount)) {
      loading = false;
      previousTotalItemCount = totalItemCount;
    }

    if (!loading && (lastVisibleItemPosition + VISIBLE_THRESHOLD) > totalItemCount) {
      currentPage++;
      onLoadMore(currentPage, totalItemCount, view);
      loading = true;
    }
  }


  public void resetState() {
    this.currentPage = this.startingPageIndex;
    this.previousTotalItemCount = 0;
    this.loading = true;
  }

  // Defines the process for actually loading more data based on page
  public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
