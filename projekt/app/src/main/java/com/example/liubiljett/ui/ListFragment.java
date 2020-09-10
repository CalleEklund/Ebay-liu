package com.example.liubiljett.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.example.liubiljett.TestAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private ItemSelectedListener mainParent;
    private ArrayList<RowItem> rowItems;

    public ListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        rowItems = new ArrayList<>();
        addRowItems();
        ListView listView = root.findViewById(R.id.itemList);
        TestAdapter adapter = new TestAdapter(requireActivity(), rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainParent = (ItemSelectedListener) getActivity().getSupportFragmentManager().findFragmentByTag("mainpageFragment");
                mainParent.onItemSelected(rowItems.get(position));

            }

        });
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch showPost = root.findViewById(R.id.showPostsSwitch);
        showPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    showPost.setText(R.string.showFollowing);
                    //filtrera så att man bara lägger till de rowitems som är gjorda av de man följer
                } else {
                    showPost.setText(R.string.showAll);
                    //visa alla poster som vanligt
                }
            }
        });




        return root;
    }

    /**
     * Denna ska senare bytas ut mot en JSON request
     */
    private void addRowItems() {
        RowItem testItem = new RowItem("Black ingvars", "99kr", R.drawable.heart, "TEST");
        RowItem testItem1 = new RowItem("Death by Stroganoff", "70kr", R.drawable.account, "TEST");
        RowItem testItem2 = new RowItem("Indian hackers", "420kr", R.drawable.comment, "TEST");
        RowItem testItem3 = new RowItem("Race against the Opponents", "20kr", R.drawable.search, "TEST");
        RowItem testItem4 = new RowItem("Ingalill med väninnor", "169kr", R.drawable.plus, "TEST");

        rowItems.add(testItem);
        rowItems.add(testItem1);
        rowItems.add(testItem2);
        rowItems.add(testItem3);
        rowItems.add(testItem4);

    }

    public interface ItemSelectedListener {
        void onItemSelected(RowItem listItem);
    }
}
