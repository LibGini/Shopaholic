package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class All_cartegory extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private FirebaseFirestore db;
    private ArrayList<productModelClass> arrayList;
    private GridviewAdapter gridviewAdapter;
    private GridView gridView;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageLoad=true;
    String Category;
    TextView toolbar,cartCount;
    ImageView  bBAck,shareB;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mFirebaseUser;
    LinearLayout empty ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cartegory);
        bBAck=findViewById(R.id.button_search);
        shareB=findViewById(R.id.category_share);
        firebaseFirestore = FirebaseFirestore.getInstance();
        gridView = findViewById(R.id.gridView);
        db = FirebaseFirestore.getInstance();
        cartCount=findViewById(R.id.cart_nu);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        Category = intent.getExtras().getString("My_Category");
        getData();
       toolbar=findViewById(R.id.cat);
       toolbar.setText(Category);
        empty=findViewById(R.id.emptylayout);
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = firebaseAuth.getCurrentUser();
        gridviewAdapter = new GridviewAdapter(All_cartegory.this, arrayList);
        gridView.setAdapter(gridviewAdapter);


        bBAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCart=new Intent(All_cartegory.this,cart.class);
                startActivity(toCart);
            }
        });


        shareB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShareIntent();
            }
        });
        cartCount=findViewById(R.id.cart_nu);
        cartCount.setVisibility(View.INVISIBLE);

        cartcount();
        gridView.setOnItemClickListener(this);





    }


    public void getData(){
        gridView.setAdapter(null);
        Query querri = db.collection("Products").whereEqualTo("category",Category);
       querri.addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

               if(!queryDocumentSnapshots.isEmpty()){
                   empty.setVisibility(View.GONE);
                   gridView.setVisibility(View.VISIBLE);



                   for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()){
                       if (document.getType() == DocumentChange.Type.ADDED) {
                           String postId = document.getDocument().getId();
                           productModelClass postAdapter = document.getDocument().toObject(productModelClass.class).withid(postId);
                           if (isFirstPageLoad) {

                               if(document.getDocument()==null){}


                               arrayList.add(postAdapter);
                           } else {
                               arrayList.add(0, postAdapter);
                           }

                           gridviewAdapter.notifyDataSetChanged();

                       }

                   }

               }else{

                   empty.setVisibility(View.VISIBLE);
                   gridView.setVisibility(View.GONE);
               }


           }
       });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "http://www.shopaholic-app.com";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void cartcount() {

        String current_user;
        current_user = mFirebaseUser.getUid();
        Query query=firebaseFirestore.collection("MyCart").whereEqualTo("user_id",current_user);
        query.addSnapshotListener(All_cartegory.this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(!queryDocumentSnapshots.isEmpty()){

                    int count = queryDocumentSnapshots.size();
                    if(count!=0){
                        cartCount.setVisibility(View.VISIBLE);
                        String strI = String.valueOf(count);
                        cartCount.setText(strI);

                    }else{

                    }


                }else{


                }
            }
        });

    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        productModelClass itemclick=arrayList.get(position);
        Intent myint= new Intent(All_cartegory.this,Signle_Product.class);
        myint.putExtra("postId",itemclick.postID);
        startActivity(myint);


    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
