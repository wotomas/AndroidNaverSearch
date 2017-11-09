package info.kimjihyok.androidnaversearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public class SearchResult<T> {
  @SerializedName("lastBuildDate")
  private String lastBuildDate;

  @SerializedName("total")
  private Integer totalPageCount;

  @SerializedName("start")
  private Integer startPageCount;

  @SerializedName("display")
  private Integer displayCount;

  @SerializedName("items")
  private List<T> items = null;

  public String getLastBuildDate() {
    return lastBuildDate;
  }

  public void setLastBuildDate(String lastBuildDate) {
    this.lastBuildDate = lastBuildDate;
  }

  public Integer getTotalPageCount() {
    return totalPageCount;
  }

  public void setTotalPageCount(Integer totalPageCount) {
    this.totalPageCount = totalPageCount;
  }

  public Integer getStartPageCount() {
    return startPageCount;
  }

  public void setStartPageCount(Integer startPageCount) {
    this.startPageCount = startPageCount;
  }

  public Integer getDisplayCount() {
    return displayCount;
  }

  public void setDisplayCount(Integer displayCount) {
    this.displayCount = displayCount;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }
}
