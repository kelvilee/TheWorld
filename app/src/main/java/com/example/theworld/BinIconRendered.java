package com.example.theworld;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class BinIconRendered extends DefaultClusterRenderer<Bin> {
    public BinIconRendered(Context context, GoogleMap map, ClusterManager<Bin> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Bin bin, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.garbage));
        markerOptions.title(bin.getTitle());
        markerOptions.snippet(bin.getSnippet());
        super.onBeforeClusterItemRendered(bin, markerOptions);
    }
}
