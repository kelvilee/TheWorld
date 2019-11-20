package com.example.theworld;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BinListAdapter  extends ArrayAdapter<TrashCanRating> {
    private Activity context;
    private List<TrashCanRating> binList;

    public BinListAdapter(Activity context, List<TrashCanRating> binList) {
        super(context, R.layout.list_layout, binList);
        this.context = context;
        this.binList = binList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvId = listViewItem.findViewById(R.id.tvTitle);
        TextView tvFacId = listViewItem.findViewById(R.id.tvSnippet);
        TextView tvRating = listViewItem.findViewById(R.id.tvRating);


        TrashCanRating reading = binList.get(position);
        tvId.setText(reading.getLocation());
        tvFacId.setText(reading.getFacilityid());
        tvRating.setText("" + reading.getRating());

        return listViewItem;
    }
}
