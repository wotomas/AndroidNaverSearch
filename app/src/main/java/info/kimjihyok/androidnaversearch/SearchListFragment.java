package info.kimjihyok.androidnaversearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jkimab on 2017. 11. 7..
 */

public class SearchListFragment extends Fragment {
  private static final String ARG_PAGE_NUMBER = "page_number";

  public static SearchListFragment newInstance(int page) {
    SearchListFragment fragment = new SearchListFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_PAGE_NUMBER, page);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_search_list, container, false);

    TextView txt = (TextView) rootView.findViewById(R.id.page_count);
    int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
    txt.setText(String.format("Page %d", page));

    return rootView;
  }
}
