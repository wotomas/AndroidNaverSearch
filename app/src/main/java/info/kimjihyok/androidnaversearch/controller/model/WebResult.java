package info.kimjihyok.androidnaversearch.controller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public class WebResult {
  @SerializedName("title")
  private String title;

  @SerializedName("link")
  private String link;

  @SerializedName("description")
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
