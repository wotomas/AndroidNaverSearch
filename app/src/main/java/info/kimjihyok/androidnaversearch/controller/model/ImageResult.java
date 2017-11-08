package info.kimjihyok.androidnaversearch.controller.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public class ImageResult implements Parcelable {
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

  public int getImageHeight() {
    return Integer.parseInt(imageHeight);
  }

  public void setImageHeight(int imageHeight) {
    this.imageHeight = String.valueOf(imageHeight);
  }

  public int getImageWidth() {
    return Integer.parseInt(imageWidth);
  }

  public void setImageWidth(int imageWidth) {
    this.imageWidth = String.valueOf(imageWidth);
  }

  // Parcelling part
  public ImageResult(Parcel in){
    String[] data = new String[5];

    in.readStringArray(data);
    // the order needs to be the same as in writeToParcel() method
    this.title = data[0];
    this.link = data[1];
    this.thumbnailURL = data[2];
    this.imageHeight = data[3];
    this.imageWidth = data[4];
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeStringArray(new String[] {
        this.title,
        this.link,
        this.thumbnailURL,
        this.imageHeight,
        this.imageWidth
    });
  }

  /** Static field used to regenerate object, individually or as arrays */
  public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
    public ImageResult createFromParcel(Parcel pc) {
      return new ImageResult(pc);
    }
    public ImageResult[] newArray(int size) {
      return new ImageResult[size];
    }
  };
}
