package com.example.labb2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Lägg in en listview som håller listan samt ett fragment som håller details
 */
public class ListFragment extends Fragment {

    String[] items = {"Japan", "Sverige", "Kina", "Norge", "Dannmark"};
    ItemSelectedListener mainParent;
    ListView listView;


    public ListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        listView = rootView.findViewById(R.id.idListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                items
        );
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainParent.onItemSelected(listView, view, position, id);


            }
        });

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ItemSelectedListener) {
            mainParent = (ItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + "implemtn interface");
        }


    }

    public interface ItemSelectedListener {
        void onItemSelected(ListView l, View v, int position, long id);
    }

}
