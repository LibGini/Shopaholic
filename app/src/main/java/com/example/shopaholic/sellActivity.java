package com.example.shopaholic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class sellActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private ImageView imageSource;
    private EditText product_name,product_desc,product_prize,product_stock;
    private TextView sellB;
    private Spinner product_cat;
    private String [] Category;
    private Uri imageUri;
    KProgressHUD progressBar;
    String user_id;
    ProgressDialog progressDialog;
    Handler updateBarHandler;
    private AsyncTask mMyTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_activity);
        updateBarHandler = new Handler();
        imageSource=findViewById(R.id.imageProduct);
        product_name=findViewById(R.id.product_name);
        product_cat=findViewById(R.id.product_cat);
        product_desc=findViewById(R.id.product_descri);
        product_stock=findViewById(R.id.product_stoke);
        product_prize=findViewById(R.id.product_prize);
        sellB=findViewById(R.id.btn_Seller);
        Category = new String[]{"Automobiles & Motorcycles", "Beauty & Health", "Cellphones & Telecommunication", "Computer & Office", "Consumer Electronics","Electronic Concepts & Supplies",
                "Furniture","Hair Extensions & Wigs","Home Appliances","Home Improvements","Jewelry & Accessories","Light & Lighting","luggage & Bags","Mother & Kids","Men's Fashion","Novelty & Special Use",
                "office & School","Security & Protection","Shoes","Sports & Protection","Tools","Toys & Hobbies","Underwear & Sleepwear","Watches","Wedding & Events","Women's Fashion"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_cat.setAdapter(aa);






        sellB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                sellP();
            }
        });
        imageSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(800,512)
                        .start(sellActivity.this);
            }
        });

    }


    public void sellP(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Shopaholic");
        progressDialog.setMessage("Product Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.getProgress();
        progressDialog.incrementProgressBy(2);
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String pname=product_name.getText().toString().trim();
        final String pcat=product_cat.getSelectedItem().toString().trim();
        final String pdesc=product_desc.getText().toString().trim();
        final String pstock=product_stock.getText().toString().trim();
        final int stocks = Integer.parseInt(pstock);
        final String prize=product_prize.getText().toString().trim();
        final int result = Integer.parseInt(prize);
        String randomname= UUID.randomUUID().toString();

        if(!TextUtils.isEmpty(pname)&&!TextUtils.isEmpty(pcat)&&!TextUtils.isEmpty(pdesc)&&!TextUtils.isEmpty(pstock)){
            final StorageReference filePath=storageReference.child("product_images").child(randomname+".jpg");
            filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri download=uri;

                            Map<String,Object> productMap=new HashMap<>();
                            productMap.put("product_name",pname);
                            productMap.put("category",pcat);
                            productMap.put("description",pdesc);
                            productMap.put("stock",stocks);
                            productMap.put("product_prize",result);
                            productMap.put("user_id",user_id);
                            productMap.put("product_image",download.toString());
                            productMap.put("timestamp",FieldValue.serverTimestamp());


                            firebaseFirestore.collection("Products").add(productMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();

                                        Toast.makeText(sellActivity.this,"product posted ",Toast.LENGTH_LONG).show();
                                        Intent mainA=new Intent(sellActivity.this,MainActivity.class);
                                        startActivity(mainA);
                                        finish();

                                    }else{
                                        String Error=task.getException().getMessage();
                                        Toast.makeText(sellActivity.this,"Error:"+Error,Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                            progressDialog.dismiss();
                        }

                    });
                }

            });


        }else{

            Toast.makeText(sellActivity.this,"Enter All Details",Toast.LENGTH_SHORT).show();
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imageSource.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
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
}
