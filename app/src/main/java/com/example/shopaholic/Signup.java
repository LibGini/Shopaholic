package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity {

    private TextView buttverify;
    private EditText phoneNumber;
    private CountryCodePicker mycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_up_activity);
        buttverify=findViewById(R.id.verifyButt);
        phoneNumber=findViewById(R.id.phone_number);
        mycode=findViewById(R.id.countryIndex);




        buttverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = phoneNumber.getText().toString().trim();
                String codeC=mycode.getSelectedCountryCode();
                String mynumber=(codeC + mobile);

                if(mobile.isEmpty() || mobile.length() < 10){
                    phoneNumber.setError("Enter a valid mobile");
                    phoneNumber.requestFocus();
                    return;
                }

                Intent pp=new Intent(Signup.this,otp.class);
                pp.putExtra("mobile",mynumber);
                startActivity(pp);
                Toast.makeText(Signup.this," we have sent a code to:"+mynumber,Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
