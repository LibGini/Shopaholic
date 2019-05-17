package com.example.shopaholic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class cartAdaptor extends RecyclerView.Adapter<cartAdaptor.ViewHolder> {
    public List<CartModel> list_post;
    public Context context;
    private FirebaseFirestore firebaseFirestore;
    float sum=0;
    String postId;


    public cartAdaptor() {
    }

    public cartAdaptor(List<CartModel> list_post) {
        this.list_post = list_post;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_card,viewGroup,false);
        context=viewGroup.getContext();
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new cartAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int ptotal=0;
        String setImage=list_post.get(i).getProduct_image();
        viewHolder.SetProduct_image(setImage);
        String setTitle=list_post.get(i).getProduct_name();
        viewHolder.setProduct_name(setTitle);
        Integer setPrize=list_post.get(i).getProduct_prize();
        viewHolder.SetProduct_prize(setPrize);
        ptotal+=list_post.get(i).getProduct_prize();
        postId=list_post.get(i).postID;


        if(i==list_post.size()-1){
            ((cart)context).total();

        }








    }

    @Override
    public int getItemCount() {
        return list_post.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View Mview;
        private ImageView postImage;
        private TextView Uname,reviewP,product_name,details,price,Ttittle;
        private ImageView menuI,delB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Mview=itemView;



            delB=Mview.findViewById(R.id.delteCart);
            delB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
DeteteFromCart();

                }
            });


        }

        public void SetProduct_image(String product_image){
            postImage=Mview.findViewById(R.id.product_image);
            Glide.with(context).load(product_image).into(postImage);

        }
        public void setProduct_name(String product_title){
            product_name=Mview.findViewById(R.id.titlec);
            product_name.setText(product_title);
        }

        public void SetProduct_prize(Integer product_prizing){
            price=Mview.findViewById(R.id.prizec);
            String prizingstring = Integer.toString(product_prizing);
            price.setText(prizingstring);

        }
    }
    public void DeteteFromCart(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Shopaholic");
        progressDialog.setMessage("Removing From  Cart ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.getProgress();
        progressDialog.incrementProgressBy(2);
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseFirestore.collection("MyCart").document(postId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(context,"Deleted!",Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context,"Failed To Delete",Toast.LENGTH_SHORT).show();

            }

        });

    }


}
