package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.example.liubiljett.TestAdapter;
import com.example.liubiljett.VolleyService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private ItemSelectedListener mainParent;
    private ArrayList<Post> rowItems;
    private VolleyService volleyService;
    private Gson gson;
    private ListView listView;
    private FragmentActivity fragmentActivity;
    public ListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        volleyService = new VolleyService(getContext());
        rowItems = new ArrayList<>();
        gson = new Gson();
        listView = root.findViewById(R.id.itemList);
        fragmentActivity = requireActivity();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainParent = (ItemSelectedListener) getActivity().getSupportFragmentManager().findFragmentByTag("mainpageFragment");
                mainParent.onItemSelected(rowItems.get(position));

            }

        });

        volleyService.getAllPosts(new VolleyService.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                List<Post> feedPosts = gson.fromJson(result, new TypeToken<List<Post>>() {
                }.getType());
                addRowItems(feedPosts);
                TestAdapter adapter = new TestAdapter(fragmentActivity, rowItems);
                listView.setAdapter(adapter);

            }

            @Override
            public void onError(String result) {
                Log.d("ERROR", result);
            }
        });
        return root;
    }

    /**
     * Denna ska senare bytas ut mot en JSON request
     */
    private void addRowItems(List<Post> currentFeed) {
        rowItems.addAll(currentFeed);
    }

    public interface ItemSelectedListener {
        void onItemSelected(Post listItem);
    }
}
