package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.ExecutorEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Signle_Product extends AppCompatActivity {
    private TextView pro_name,pro_prize,pro_details,category,quant,AddCart;
    private ImageView prodpic,onback;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String post_key;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signle__product);
        storageReference= FirebaseStorage.getInstance().getReference();
        pro_name=findViewById(R.id.product_name);
        AddCart=findViewById(R.id.ButtonCart);
        quant=findViewById(R.id.quantity);
        category=findViewById(R.id.cat);
        pro_prize=findViewById(R.id.product_prize);
        pro_details=findViewById(R.id.product_details);
        prodpic=findViewById(R.id.product_image);
        firebaseFirestore=FirebaseFirestore.getInstance();
        onback=findViewById(R.id.button_back);
        mAuth=FirebaseAuth.getInstance();
        post_key=getIntent().getExtras().getString("postId");

        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartpost();
            }
        });


        firebaseFirestore.collection("Products").document(post_key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if(task.isSuccessful()){
                    String cat=task.getResult().getString("category");
                    category.setText(cat);
                    String productname = task.getResult().getString("product_name");
                    pro_name.setText(productname);
                    int prizing=task.getResult().getLong("product_prize").intValue();
                    String prizingstring = Integer.toString(prizing);
                    pro_prize.setText(prizingstring);
                    String details=task.getResult().getString("description");
                    pro_details.setText(details);
                    String image_product=task.getResult().getString("product_image");
                    RequestOptions placeholderRequest = new RequestOptions();
                    placeholderRequest.placeholder(R.drawable.hold);
                    Glide.with(Signle_Product.this).setDefaultRequestOptions(placeholderRequest).load(image_product).into(prodpic);


                }else{

                    String errorr=task.getException().getMessage();
                    Toast.makeText(Signle_Product.this,"Error:"+errorr,Toast.LENGTH_LONG);

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

    public void cartpost(){

        storageReference= FirebaseStorage.getInstance().getReference();
        final String currentUser= mAuth.getCurrentUser().getUid();
        final String title=pro_name.getText().toString().trim();
        final String prizing=pro_prize.getText().toString().trim();
        final int result = Integer.parseInt(prizing);
        Bitmap bm=((BitmapDrawable)prodpic.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageByte=stream.toByteArray();
        ByteArrayInputStream bis=new ByteArrayInputStream(imageByte);
        String randomname= UUID.randomUUID().toString();


        final StorageReference filePath= storageReference.child("product_images").child(randomname+".jpg");
        filePath.putStream(bis).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri download=uri;

                        Map<String,Object> cartmap=new HashMap<>();
                        cartmap.put("product_name",title);
                        cartmap.put("product_prize",result);
                        cartmap.put("product_id",post_key);
                        cartmap.put("user_id",currentUser);
                        cartmap.put("product_image",download.toString());
                        cartmap.put("timestamp", FieldValue.serverTimestamp());
                        firebaseFirestore.collection("MyCart").add(cartmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(Signle_Product.this, "Added to cart", Toast.LENGTH_SHORT).show();



                                }else{
                                    Toast.makeText(Signle_Product.this, "Failed Try Later ", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });




                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
