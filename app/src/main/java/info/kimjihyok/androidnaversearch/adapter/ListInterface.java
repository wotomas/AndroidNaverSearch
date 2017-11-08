package info.kimjihyok.androidnaversearch.adapter;

import java.util.List;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

public interface ListInterface<T> {
  void addAll(List<T> t);
  void add(T t);
  void remove(int position);
  void clear();

  //TODO: add more when necessary
}
