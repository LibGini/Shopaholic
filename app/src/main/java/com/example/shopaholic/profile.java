package com.example.shopaholic;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    private EditText username,email,birthdate;
    private RadioButton male,female;
    private TextView saveData;
    private CircleImageView profpic;
    Bitmap myBitmap;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private StorageReference  storageReference;
    private FirebaseStorage storage;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "SelectImageActivity";
    FirebaseFirestore db;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    public Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        username=findViewById(R.id.edusername);
        email=findViewById(R.id.edemail);
        birthdate=findViewById(R.id.edbirthdate);
        saveData=findViewById(R.id.btnsaveprof);
        profpic=findViewById(R.id.circleImageView);
        handlePermission();


        firebaseAuth=FirebaseAuth.getInstance();

        profpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();


            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveprofile();
            }
        });

        }

    private void saveprofile() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Shopaholic");
        progressDialog.setMessage("Saving User Details...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.getProgress();
        progressDialog.incrementProgressBy(2);
        progressDialog.setCancelable(false);
        progressDialog.show();



        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        final CharSequence gender= radioSexButton.getText();
        final String user_name=username.getText().toString().trim();
        final String user_email=email.getText().toString().trim();
        final String user_birth=birthdate.getText().toString().trim();
        storageReference= FirebaseStorage.getInstance().getReference();


        if(!TextUtils.isEmpty(user_name)&&!TextUtils.isEmpty(user_birth)&&!TextUtils.isEmpty(user_email) && imageUri!=null &&gender!=null){



        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        final StorageReference img_path=storageReference.child("profile_image").child(uid +".jpg");
        img_path.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                img_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri download=uri;
                        Map<String,String>userMap=new HashMap<>();
                        userMap.put("username",user_name);
                        userMap.put("email",user_email);
                        userMap.put("DOB",user_birth);
                        userMap.put("user_id",uid);
                        userMap.put("Gender", (String) gender);
                        userMap.put("profile_pic",download.toString());

                        db.collection("profile").document(uid).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(profile.this,"profile set",Toast.LENGTH_SHORT).show();
                                   Tohome();

                                }else{
                                    progressDialog.dismiss();
                                    String errorr=task.getException().getMessage();
                                    Toast.makeText(profile.this,"Error:"+errorr,Toast.LENGTH_LONG);
                                }
                            }
                        });
                    }
                });

            }
        });
        }else{
            progressDialog.dismiss();
            Toast.makeText(profile.this,"Enter all details",Toast.LENGTH_LONG);

        }

    }

    private void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_PICTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PICTURE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /* Choose an image from Gallery */
    void openImageChooser() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(profile.this);
    }


    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(profile.this);
                    }
                });
        alertDialog.show();
    }

    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public void Tohome(){
        Intent homep=new Intent(profile.this,MainActivity.class);
        startActivity(homep);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                profpic.setImageURI(imageUri);

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

