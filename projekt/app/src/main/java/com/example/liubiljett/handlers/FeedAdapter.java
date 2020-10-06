package com.example.liubiljett.handlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liubiljett.classes.Post;
import com.example.liubiljett.R;

import java.util.ArrayList;

/**
 * A custom adapter class used for creating the main feed, followed users feed and like posts
 */
public class FeedAdapter extends BaseAdapter {

    private ArrayList<Post> singleRow;
    private LayoutInflater mInflater;

    public FeedAdapter(Context context, ArrayList<Post> mRow){
        this.singleRow = mRow;
        mInflater = (LayoutInflater.from(context));
    }

    /**
     * Getters
     */
    @Override
    public int getCount() {
        return singleRow.size();
    }

    @Override
    public Object getItem(int position) {
        return singleRow.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.listview_row, parent, false);

            TextView headline = convertView.findViewById(R.id.headlineID);
            TextView price = convertView.findViewById(R.id.priceID);
            TextView description = convertView.findViewById(R.id.descID);

            Post currentRow = (Post) getItem(position);

            headline.setText(currentRow.getTitle());
            price.setText(currentRow.getPrice());
            description.setText(currentRow.getDesc());
        }
        return convertView;
    }
}
