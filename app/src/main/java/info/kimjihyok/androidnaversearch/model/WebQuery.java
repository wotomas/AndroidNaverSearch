package info.kimjihyok.androidnaversearch.model;

import java.util.List;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;

/**
 * Created by jkimab on 2017. 11. 9..
 */
@Entity
public class WebQuery implements Persistable {
  @Key
  @Generated
  long id;

  String queryText;

  @OneToMany(mappedBy = "id", cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
  List<WebResult> webEntities;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getQueryText() {
    return queryText;
  }

  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }

  public List<WebResult> getWebEntities() {
    return webEntities;
  }

  public void setWebEntities(List<WebResult> webEntities) {
    this.webEntities = webEntities;
  }
}
