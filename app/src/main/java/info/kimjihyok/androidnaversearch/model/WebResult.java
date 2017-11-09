package info.kimjihyok.androidnaversearch.model;

import com.google.gson.annotations.SerializedName;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

@Entity
public class WebResult implements Persistable {
  @Key
  @Generated
  long id;

  @SerializedName("title")
  String title;

  @SerializedName("link")
  String link;

  @SerializedName("description")
  String description;

  @ManyToOne
  WebQuery queryList;

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
