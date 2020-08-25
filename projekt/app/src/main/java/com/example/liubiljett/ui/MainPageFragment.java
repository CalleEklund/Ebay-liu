package com.example.liubiljett.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.example.liubiljett.TestAdapter;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {

    ArrayList<RowItem> rowItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        rowItems = new ArrayList<>();
        addRowItems();




        ListView listView = root.findViewById(R.id.itemList);

        //addRowItems();
        //System.out.println(rowItems.toString());

        //listView = (ListView) findViewById(R.id.listViewID);


        TestAdapter adapter = new TestAdapter(requireActivity(), rowItems);
        listView.setAdapter(adapter);

        //final TextView textView = root.findViewById(R.id.text_dashboard);
        //textView.setText("Post");


        return root;
    }

    private void addRowItems() {
        RowItem testItem = new RowItem("Black ingvars", "99kr", R.drawable.heart);
        RowItem testItem1 = new RowItem("Death by Stroganoff", "70kr", R.drawable.account);
        RowItem testItem2 = new RowItem("Indian hackers", "420kr", R.drawable.comment);
        RowItem testItem3 = new RowItem("Race against the Opponents", "20kr", R.drawable.search);
        RowItem testItem4 = new RowItem("Ingalill med väninnor", "169kr", R.drawable.plus);

        rowItems.add(testItem);
        rowItems.add(testItem1);
        rowItems.add(testItem2);
        rowItems.add(testItem3);
        rowItems.add(testItem4);

    }


}