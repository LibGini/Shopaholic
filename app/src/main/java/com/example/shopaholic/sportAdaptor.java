package com.example.shopaholic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class sportAdaptor extends RecyclerView.Adapter<sportAdaptor.ViewHolder>{
    public List<productModelClass> list_post;
    public Context context;
    private FirebaseFirestore firebaseFirestore;

    public sportAdaptor() {
    }

    public sportAdaptor(List<productModelClass> list_post) {
        this.list_post = list_post;
    }

    @NonNull
    @Override
    public sportAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.picked_card,viewGroup,false);
        context=viewGroup.getContext();
        firebaseFirestore=FirebaseFirestore.getInstance();

        return new sportAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sportAdaptor.ViewHolder viewHolder, int i) {
        String setImage=list_post.get(i).getProduct_image();
        viewHolder.setImage(setImage);
        String setDetails=list_post.get(i).getDescription();
        viewHolder.setDetails(setDetails);
        Integer setPrizee=list_post.get(i).getProduct_prize();
        viewHolder.setprize(setPrizee);

        final String postId=list_post.get(i).postID;


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent singlepost= new Intent(context,Signle_Product.class);
                singlepost.putExtra("postId",postId);
                context.startActivity(singlepost);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list_post.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View Mview;
        private ImageView postImage;
        private TextView Uname,reviewP,product_pick,details,price,Ttittle;
        private ImageView menuI,delB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Mview=itemView;
        }

        public void setImage(String product_image){

            postImage=Mview.findViewById(R.id.postIma);
            Glide.with(context).load(product_image).into(postImage);


        }

        public void setDetails(String product_details){
            details=Mview.findViewById(R.id.detailsp);
            details.setText(product_details+"...");

        }

        public void setprize(Integer  product_prize){
            price=Mview.findViewById(R.id.txtpize);
            price.setText("KES "+product_prize);
        }


        public void setReview(String product_review){
            reviewP=Mview.findViewById(R.id.potivereview);
            reviewP.setText("product review "+product_review);

        }
        public void setPickofday(String product_pick_day){
            product_pick=Mview.findViewById(R.id.pickofday);
            product_pick.setText(product_pick_day);
        }
    }
}
