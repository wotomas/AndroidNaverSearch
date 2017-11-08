package info.kimjihyok.androidnaversearch.controller.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public class ImageResult {
  @SerializedName("title")
  private String title;

  @SerializedName("link")
  private String link;

  @SerializedName("thumbnail")
  private String thumbnailURL;

  @SerializedName("sizeheight")
  private String imageHeight;

  @SerializedName("sizewidth")
  private String imageWidth;

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

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
  }

  public String getImageHeight() {
    return imageHeight;
  }

  public void setImageHeight(String imageHeight) {
    this.imageHeight = imageHeight;
  }

  public String getImageWidth() {
    return imageWidth;
  }

  public void setImageWidth(String imageWidth) {
    this.imageWidth = imageWidth;
  }
}
