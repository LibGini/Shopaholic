package com.example.shopaholic;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageLoad=true;
    private List<productModelClass> search_list;
    private RecyclerView postV;
    private  searchAdapter postRecyclerAdapter;
    SearchView searchViewb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        search_list=new ArrayList<>();
        postV=findViewById(R.id.searchlist);
        firebaseAuth=FirebaseAuth.getInstance();
        postRecyclerAdapter=new searchAdapter(search_list);
        postV.setLayoutManager(new LinearLayoutManager(this));
        postV.setAdapter(postRecyclerAdapter);

         getUsers();


        searchViewb=findViewById(R.id.search);
        searchViewb.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, "SEARCH " + query, Toast.LENGTH_LONG).show();
                searchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(MainActivity.this, "SEARCH " + newText, Toast.LENGTH_LONG).show();
                searchUsers(newText);
                return false;
            }
        });


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    private void getUsers() {
        firebaseFirestore.collection("Products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                        search_list = new ArrayList<productModelClass>();

                        for (DocumentSnapshot doc : snapshots) {
                            productModelClass search = doc.toObject(productModelClass.class);
                            search_list.add(search);
                        }
                        updateListUsers((ArrayList<productModelClass>) search_list);
                    }
                });
    }



    private void searchUsers(String recherche) {
        if (recherche.length() > 0)
            recherche = recherche.substring(0, 1).toUpperCase() + recherche.substring(1).toLowerCase();

        ArrayList<productModelClass> results = new ArrayList<>();
        for(productModelClass search : search_list){
            if(search.getProduct_name() != null && search.getProduct_name().contains(recherche)){
                results.add(search);
            }
        }
        updateListUsers(results);
    }



    private void updateListUsers(ArrayList<productModelClass> listUsers) {

        // Sort the list by date
        Collections.sort(listUsers, new Comparator<productModelClass>() {
            @Override
            public int compare(productModelClass o1, productModelClass o2) {
                int res = -1;
                if (o1.getProduct_prize() > (o2.getProduct_prize())) {
                    res = 1;
                }
                return res;
            }
        });

        postRecyclerAdapter = new searchAdapter(listUsers);
        postV.setNestedScrollingEnabled(false);
        postV.setAdapter(postRecyclerAdapter);
        postV.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int myColor = Color.parseColor("#000000");
            window.setStatusBarColor(myColor);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }
}
