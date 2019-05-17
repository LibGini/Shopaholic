package com.example.shopaholic;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
public class ourpicfragment extends Fragment {
    ViewPager viewPager;
    private FirebaseFirestore firebaseFirestore;

    private RecyclerView postV;
    private  postAdapter postRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageLoad=true;
    private List<productModelClass> topList;



    public ourpicfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ourpicfragment,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        topList=new ArrayList<>();
        postV=view.findViewById(R.id.picked_u);
        firebaseAuth=FirebaseAuth.getInstance();
        postRecyclerAdapter=new postAdapter(topList);
        postV.setLayoutManager(new LinearLayoutManager(getActivity()));
        postV.setAdapter(postRecyclerAdapter);



        postV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Boolean BottomReached = !recyclerView.canScrollVertically(1);
                if (BottomReached) {
                }
            }
        });




        Query querri = firebaseFirestore.collection("Products");
       querri.addSnapshotListener(new EventListener<QuerySnapshot>() {
    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        if (!queryDocumentSnapshots.isEmpty()) {
            if (isFirstPageLoad) {
                lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                topList.clear();

            }for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                if (doc.getType() == DocumentChange.Type.ADDED) {
                    String postId = doc.getDocument().getId();
                   productModelClass postAdapter = doc.getDocument().toObject(productModelClass.class).withid(postId);
                    if (isFirstPageLoad) {

                        topList.add(postAdapter);
                    } else {
                        topList.add(0, postAdapter);
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
