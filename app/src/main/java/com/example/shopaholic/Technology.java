package com.example.shopaholic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
public class Technology extends Fragment {
    private FirebaseFirestore firebaseFirestore;

    private RecyclerView postV;
    private  techAdaptor postRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageLoad=true;
    private List<productModelClass> tech_list;


    public Technology() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_technology, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        tech_list=new ArrayList<>();
        postV=view.findViewById(R.id.TechnologyF);
        firebaseAuth=FirebaseAuth.getInstance();
        postRecyclerAdapter=new techAdaptor(tech_list);
        postV.setLayoutManager(new LinearLayoutManager(getActivity()));
        postV.setAdapter(postRecyclerAdapter);


           Query querri;

           querri = firebaseFirestore.collection("Products")
                   .whereEqualTo("category","Consumer Electronics");

        querri.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    if (isFirstPageLoad) {
                        lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                        tech_list.clear();

                    }for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String postId = doc.getDocument().getId();
                            productModelClass postAdapter = doc.getDocument().toObject(productModelClass.class).withid(postId);
                            if (isFirstPageLoad) {

                                tech_list.add(postAdapter);
                            } else {
                                tech_list.add(0, postAdapter);
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
