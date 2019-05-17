package com.example.shopaholic;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LipaNaMpesa extends AppCompatActivity implements AuthListener, MpesaListener  {
    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String CONSUMER_KEY = "enuktAQsXXELth60o5q4ytDxyliufCbS";
    public static final String CONSUMER_SECRET = "0k8uJGAEv96AHHUt";
    public static final String CALLBACK_URL = "YOUR_CALLBACK_URL";

    public static final String  NOTIFICATION = "PushNotification";
    public static final String SHARED_PREFERENCES = "com.example.shopaholic";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;


    TextView pay;
    ProgressDialog dialog;
    EditText phone;
    TextView amount;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Integer mycart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipa_na_mpesa);
        pay=findViewById(R.id.btnpay);
        phone=findViewById(R.id.Txtphone);
        Intent myint=getIntent();
        mycart=myint.getIntExtra("you_pay",0);
        firebaseAuth=FirebaseAuth.getInstance();
        amount=findViewById(R.id.Txtamount);
        String strI = String.valueOf(mycart);
        amount.setText(strI);
        //String  phone_number=  firebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        //phone.setText(phone_number);
        Mpesa.with(this, CONSUMER_KEY, CONSUMER_SECRET);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing");
        dialog.setIndeterminate(true);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = phone.getText().toString();

                if(!TextUtils.isEmpty(phone_number)){
                    int pay_amout = Integer.valueOf(amount.getText().toString());
                    lipa(phone_number, pay_amout);

                }else{

                    Toast.makeText(LipaNaMpesa.this,"Enter phonenumber",Toast.LENGTH_LONG);
                }



            }
        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(NOTIFICATION)) {
                    String title = intent.getStringExtra("title");
                    String message = intent.getStringExtra("message");
                    int code = intent.getIntExtra("code", 0);
                    showDialog(title, message, code);

                }
            }
        };


    }

    private void lipa(String phone, int amount) {
        dialog.show();
        STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, amount,BUSINESS_SHORT_CODE, phone);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String token = sharedPreferences.getString("InstanceID", "");

        builder.setFirebaseRegID(token);
        STKPush push = builder.build();
        Mpesa.getInstance().pay(this, push);


    }


    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
    }

    @Override
    public void onAuthSuccess() {
        pay.setEnabled(true);
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        dialog.hide();
        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        dialog.hide();
        Toast.makeText(this, CustomerMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void showDialog(String title, String message,int code){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.dialog_success, true)
                .positiveText("OK")
                .cancelable(false)
                .widgetColorRes(R.color.colorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
        View view=dialog.getCustomView();
        TextView messageText = view.findViewById(R.id.message);
        ImageView imageView = view.findViewById(R.id.success);
        if (code != 0){
            imageView.setVisibility(View.GONE);
        }
        messageText.setText(message);
        dialog.show();
    }
}
