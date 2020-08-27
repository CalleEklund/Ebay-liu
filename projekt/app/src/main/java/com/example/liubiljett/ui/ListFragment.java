package com.example.liubiljett.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.example.liubiljett.TestAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private ItemSelectedListener mainParent;
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;
    private ArrayList<RowItem> rowItems;

    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        //textView = rootView.findViewById(R.id.idTextView);
        //listView = root.findViewById(R.id.itemList);
        rowItems = new ArrayList<>();
        addRowItems();

        ListView listView = root.findViewById(R.id.itemList);


        TestAdapter adapter = new TestAdapter(requireActivity(), rowItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String groupName = rowItems.get((int) id).toString();
                Log.d("tag", mainParent.toString());

                mainParent.onItemSelected(groupName);
            }

        });
        return root;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("tag", context.toString());
        if (context instanceof ItemSelectedListener) {
            mainParent = (ItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + "implement interface");
        }


    }





    private void addRowItems() {
        RowItem testItem = new RowItem("Black ingvars", "99kr", R.drawable.heart ,"TEST");
        RowItem testItem1 = new RowItem("Death by Stroganoff", "70kr", R.drawable.account,"TEST");
        RowItem testItem2 = new RowItem("Indian hackers", "420kr", R.drawable.comment,"TEST");
        RowItem testItem3 = new RowItem("Race against the Opponents", "20kr", R.drawable.search,"TEST");
        RowItem testItem4 = new RowItem("Ingalill med väninnor", "169kr", R.drawable.plus,"TEST");

        rowItems.add(testItem);
        rowItems.add(testItem1);
        rowItems.add(testItem2);
        rowItems.add(testItem3);
        rowItems.add(testItem4);

    }



    public interface ItemSelectedListener {
        void onItemSelected(String groupName);
    }

}
