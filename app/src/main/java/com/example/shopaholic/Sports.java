package com.example.shopaholic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sports extends Fragment {
    private FirebaseFirestore firebaseFirestore;

    private RecyclerView postV;
    private  sportAdaptor postRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageLoad=true;
    private List<productModelClass> sport_list;


    public Sports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sports, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        sport_list=new ArrayList<>();
        postV=view.findViewById(R.id.sportF);
        firebaseAuth=FirebaseAuth.getInstance();
        postRecyclerAdapter=new sportAdaptor(sport_list);
        postV.setLayoutManager(new LinearLayoutManager(getActivity()));
        postV.setAdapter(postRecyclerAdapter);


        Query querri;

        querri = firebaseFirestore.collection("Products")
                .whereEqualTo("category","Sports & Protection");

        querri.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    if (isFirstPageLoad) {
                        lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                        sport_list.clear();

                    }for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String postId = doc.getDocument().getId();
                            productModelClass postAdapter = doc.getDocument().toObject(productModelClass.class).withid(postId);
                            if (isFirstPageLoad) {

                                sport_list.add(postAdapter);
                            } else {
                                sport_list.add(0, postAdapter);
                            }
                            postRecyclerAdapter.notifyDataSetChanged();


                        }

                    }

                }
            }
        });




        return view;

           }

}
