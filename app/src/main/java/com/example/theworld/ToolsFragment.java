package com.example.theworld;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ToolsFragment extends Fragment {
    private DatabaseReference ratingsDatabase;
    private ListView lvBins;
    private ArrayList<TrashCanRating> trashCanRatings = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        ratingsDatabase = FirebaseDatabase.getInstance().getReference("trashcans");
        lvBins = view.findViewById(R.id.lvTrashCans);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ratingsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trashCanRatings.clear();
                for(DataSnapshot ratingsSnapshot : dataSnapshot.getChildren()) {
                    TrashCanRating ratingObject = ratingsSnapshot.getValue(TrashCanRating.class);
                    trashCanRatings.add(ratingObject);
                }

                Collections.sort(trashCanRatings, new Comparator<TrashCanRating>() {
                    @Override
                    public int compare(TrashCanRating data1, TrashCanRating data2) {
                        if( data1.getRating() > data2.getRating() )
                            return -1;
                        else if (data1.getRating() < data2.getRating())
                            return 1;
                        else
                            return 0;
                    }
                });


                if(getActivity() != null) {
                    BinListAdapter adapter = new BinListAdapter(getActivity(), trashCanRatings);
                    lvBins.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
