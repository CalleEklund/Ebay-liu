package com.example.liubiljett;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestAdapter extends BaseAdapter {

    private ArrayList<RowItem> singleRow;
    private LayoutInflater mInflater;

    public TestAdapter(Context context, ArrayList<RowItem> mRow){
        this.singleRow = mRow;
        mInflater = (LayoutInflater.from(context));
    }

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
            ImageView image = convertView.findViewById(R.id.image_holderID); //skulle kunna vara image_holderID också, men testar med imageview först

            RowItem currentRow = (RowItem) getItem(position);

            headline.setText(currentRow.getHeadline());
            price.setText(currentRow.getPrice());
            image.setImageResource(currentRow.getImageID());
        }
        return convertView;
    }
}
