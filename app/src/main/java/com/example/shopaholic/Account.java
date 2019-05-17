package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Account extends AppCompatActivity {
    private ImageView option;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    String CurrentUser_id;
    private ImageView profpic;
    private TextView Emailp,Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser_id = mAuth.getCurrentUser().getUid();
        profpic=findViewById(R.id.imageView);
        Emailp=findViewById(R.id.email_address);
        Username=findViewById(R.id.username);


        //profile user info
        firebaseFirestore.collection("profile").document(CurrentUser_id).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String imagep = documentSnapshot.getString("profile_pic");
                Glide.with(Account.this).load(imagep).into(profpic);
                String username = documentSnapshot.getString("username");
                String email = documentSnapshot.getString("email");
                Emailp.setText(email);
                Username.setText(username);



            }
        });



        option=findViewById(R.id.category_share);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Account.this, option);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.account_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.delac:
                           Intent nn =new Intent(Account.this,profile.class);
                           startActivity(nn);

                                return true;
                            case R.id.setupa:
                                  setupaccount();
                                return true;
                            default:
                                return true;
                        }


                    }
                });


                popup.show();

            }
        });

    }

    private void setupaccount() {
        Intent setupA=new Intent(Account.this,setup_account.class);
        startActivity(setupA);
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
