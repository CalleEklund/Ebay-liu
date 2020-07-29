package com.example.liubiljett;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class MyAdapter extends ArrayAdapter {

    private final Activity context;

    private final Integer[] imageArray; //om vi vill visa bilder i flödet

    private final String[] headLineArray; //rubriken

    private final String[] priceArray; //priset, ta den i string för då kan vi ha "100kr"

    // context sparar vilken activity listviewen är på, de andra sparar själva datan som visas.

    public MyAdapter(Activity context, String[] headLineArrayParam, String[] priceArrayParam, Integer[] imageArrayParam) {
        super(context, R.layout.listview_row, headLineArrayParam);

        this.context = context;
        this.imageArray = imageArrayParam;
        this.headLineArray = headLineArrayParam;
        this.priceArray = priceArrayParam;
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.listview_row, null, true);

        //dessa skapar variabler från xml-filen
        TextView headline = rowView.findViewById(R.id.headlineID);
        TextView price = rowView.findViewById(R.id.priceID);
        ImageView ticketImage = rowView.findViewById(R.id.image_holderID);

        headline.setText(headLineArray[position]);
        price.setText(priceArray[position]);
        ticketImage.setImageResource(imageArray[position]);

        return rowView;
    }
}