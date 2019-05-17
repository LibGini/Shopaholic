package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.List;

import javax.annotation.Nullable;

public class cart extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;

    private RecyclerView postV;
    private  cartAdaptor postRecyclerAdapter;
    private List<CartModel> cartlist;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;
    String Current_user;
    private Boolean isFirstPageLoad=true;
    LinearLayout empty ;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private TextView cartCount;
    private ImageView backpress,deleteB;
    TextView Total,checkOut;
    Integer me_pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        empty=findViewById(R.id.emptylayout);
        cartCount=findViewById(R.id.cart_num);
        firebaseFirestore = FirebaseFirestore.getInstance();
        cartlist=new ArrayList<>();
        postV=findViewById(R.id.cartrecycler);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        backpress=findViewById(R.id.button_search);
        firebaseAuth= FirebaseAuth.getInstance();
        postRecyclerAdapter = new cartAdaptor (cartlist);
        postV.setLayoutManager(new LinearLayoutManager(this));
        postV.setAdapter(postRecyclerAdapter);
        cartcount();
        deleteB=findViewById(R.id.delteCart);
        Total=findViewById(R.id.txtTotal);
        Current_user=firebaseAuth.getCurrentUser().getUid();
        checkOut=findViewById(R.id.btncheckout);

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(cart.this,LipaNaMpesa.class);
                myIntent.putExtra("you_pay",me_pay);
                startActivity(myIntent);
            }
        });




backpress.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});


        Query query=firebaseFirestore.collection("MyCart").whereEqualTo("user_id",Current_user);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(!queryDocumentSnapshots.isEmpty()){


                        empty.setVisibility(View.GONE);
                        postV.setVisibility(View.VISIBLE);


                     if(isFirstPageLoad){
                         lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                         cartlist.clear();

                     }for (DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String postId = doc.getDocument().getId();
                            CartModel postAdapter=doc.getDocument().toObject(CartModel.class).withid(postId);
                            if(isFirstPageLoad){

                                cartlist.add(postAdapter);
                            }else{

                                cartlist.add(0, postAdapter);
                            }
                            postRecyclerAdapter.notifyDataSetChanged();


                        }

                    }

                }else{

                    empty.setVisibility(View.VISIBLE);
                    postV.setVisibility(View.GONE);
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

    public void cartcount() {
        String current_user;
        current_user = mFirebaseAuth.getCurrentUser().getUid();
        Query query=firebaseFirestore.collection("MyCart").whereEqualTo("user_id",current_user);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void total(){
        int total_sum=0;
       for(int i=0;i<cartlist.size();i++){
           CartModel carts_items=cartlist.get(i);
           int price=carts_items.getProduct_prize();
           total_sum+=price;

       }
       Total.setText(total_sum +"");
       me_pay=total_sum;
    }

    public  void deteteCartData(){



    }
}
