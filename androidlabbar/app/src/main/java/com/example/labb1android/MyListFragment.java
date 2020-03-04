package com.example.labb1android;

;

import android.app.ListFragment;
import android.os.Bundle;
//import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MyListFragment extends ListFragment {

    String[] titles = new String[]{"test1","test2","test3"};
    String[] details = new String[]{"d1","d2","d3"};
    public MyListFragment() {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        labb2.onItemSelected(l,v,position,id);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(),R.layout.activity_labb2,titles);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface ItemSelectedListener{
        void onItemSelected(ListView l, View v, int position, long id);
    }
}


