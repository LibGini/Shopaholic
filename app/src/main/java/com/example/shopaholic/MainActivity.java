package com.example.shopaholic;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private  FirebaseAuth mAuth;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;


    private int[] myImageList = new int[]{R.drawable.lg, R.drawable.make,
            R.drawable.ab,R.drawable.man
            ,R.drawable.all,R.drawable.sam};

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ImageView option;
    private ImageView flash1,flash2,flash3,flash4,tops1,top2,new1,new2,store1,store2,tech1,mens1,veh1,shoe1,toy1,kit1,jew1,sport1;
    private TextView txtflash1,txtflash2,txtflash3,txtflash4,cartCount;
    private TextView pflash1,pflash2,pflash3,ptflash4;
    private ImageView categoryI,topselection,tocart,searchB;
    private VideoView mVideoView;
    private Uri videouri;
    private ImageView Shoes,Tech,Mens,vehicle,Sports,Toys,Kitchens,Jewerly,flashdeal1,flashdeal2,flashdeal3,flashdeal4;
    private ImageView Topselection1,Topselection2,New1,New2,Store1,Store2,Freebies,Coins,Flashdeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intstantiateUser();
        tocart=findViewById(R.id.mcart);
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        categoryI=findViewById(R.id.category);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Shoes=findViewById(R.id.shoe1);
        Tech=findViewById(R.id.tech1);
        Mens=findViewById(R.id.men1);
        vehicle=findViewById(R.id.vehicle1);
        Sports=findViewById(R.id.sport1);
        Toys=findViewById(R.id.Toys1);
        Kitchens=findViewById(R.id.kit1);
        Jewerly=findViewById(R.id.jewerly1);
        searchB=findViewById(R.id.button_search);
        flashdeal1=findViewById(R.id.flash1);
        flashdeal2=findViewById(R.id.flash2);
        flashdeal3=findViewById(R.id.flash3);
        flashdeal4=findViewById(R.id.flash4);
        Topselection1=findViewById(R.id.top1);
        Topselection2=findViewById(R.id.top2);
        Store1=findViewById(R.id.store1);
        Store2=findViewById(R.id.store2);

        New1=findViewById(R.id.for1);
        New2=findViewById(R.id.for2);

        Freebies=findViewById(R.id.freebies);
        Coins=findViewById(R.id.coinsandcoup);
        Flashdeals=findViewById(R.id.flashdeals);

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mysearch=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(mysearch);
            }
        });

        Shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Shoes";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });
        Tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Consumer Electronics";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });
        Mens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Men's Fashion";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Automobiles & Motorcycles";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        Sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Sports & Protection";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });
        Toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Toys & Hobbies";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });
        Kitchens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Home Appliances";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        Jewerly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Jewelry & Accessories";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });



        tocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent catInt=new Intent(MainActivity.this,cart.class);
                startActivity(catInt);
            }
        });
        categoryI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent catInt=new Intent(MainActivity.this,CategoryActivity.class);
                startActivity(catInt);
            }
        });

        flashdeal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="flash Deal";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        flashdeal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="flash Deal";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        flashdeal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="flash Deal";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        flashdeal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="flash Deal";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        Topselection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Top Selection";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        Topselection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="Top Selection";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

        New1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="New Products";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });
        New2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category="New Products";
                Intent myint= new Intent(MainActivity.this,All_cartegory.class);
                myint.putExtra("My_Category",category);
                startActivity(myint);
            }
        });

Store1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String category="Best Stores";
        Intent myint= new Intent(MainActivity.this,All_cartegory.class);
        myint.putExtra("My_Category",category);
        startActivity(myint);
    }
});

Store2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String category="Best Stores";
        Intent myint= new Intent(MainActivity.this,All_cartegory.class);
        myint.putExtra("My_Category",category);
        startActivity(myint);
    }
});


Flashdeals.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String category="Flash Deal";
        Intent myint= new Intent(MainActivity.this,All_cartegory.class);
        myint.putExtra("My_Category",category);
        startActivity(myint);
    }
});
Freebies.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String category="Freebies";
        Intent myint= new Intent(MainActivity.this,All_cartegory.class);
        myint.putExtra("My_Category",category);
        startActivity(myint);
    }
});

Coins.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String category="Coins & Coupons";
        Intent myint= new Intent(MainActivity.this,All_cartegory.class);
        myint.putExtra("My_Category",category);
        startActivity(myint);
    }
});

        init();
        flashDeal1();
        flashDeal2();
        flashDeal3();
        flashDeal4();
        V_play();
         Topsales1();
        topsale2();
        newselection1();
        newaelection2();
        storelove1();
        storelove2();
        tech();
        menstore();
        shoes1();
        vehichle1();
        toy1();
        jewelry();
        sports();
        kitch1();
        //toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        option=findViewById(R.id.category_share);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, option);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.main_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.account:
                                accoutnpage();

                                return true;
                            case R.id.logout:
                                SignOut();
                                return true;

                            case R.id.sell_product:
                                Intent sellI=new Intent(MainActivity.this,sellActivity.class);
                                startActivity(sellI);
                                return true;
                            default:
                                return true;
                        }


                    }
                });


                popup.show();

            }
        });
        topselection=findViewById(R.id.tops);
        topselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topIntent=new Intent(MainActivity.this,Topselection.class);
                startActivity(topIntent);
            }
        });

        cartCount=findViewById(R.id.cart_num);
        cartCount.setVisibility(View.INVISIBLE);



    }

    private void kitch1() {
        kit1=findViewById(R.id.kit1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("Kitchens").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(kit1);

                    }
                }
            }
        });


    }

    private void sports() {
        sport1=findViewById(R.id.sport1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("sports").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(sport1);

                    }
                }
            }
        });

    }

    private void jewelry() {
        jew1=findViewById(R.id.jewerly1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("Jewelry").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(jew1);

                    }
                }
            }
        });
    }



    private void toy1() {
        toy1=findViewById(R.id.Toys1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("toys").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(toy1);

                    }
                }
            }
        });

    }

    private void vehichle1() {
        veh1=findViewById(R.id.vehicle1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("vehicles").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(veh1);

                    }
                }
            }
        });

    }

    private void shoes1() {
        shoe1=findViewById(R.id.shoe1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("shoes").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(shoe1);

                    }
                }
            }
        });

    }

    private void menstore() {
        mens1=findViewById(R.id.men1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("cloths").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(mens1);

                    }
                }
            }
        });


    }

    private void tech() {
        tech1=findViewById(R.id.tech1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("tech").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(tech1);

                    }
                }
            }
        });


    }

    private void storelove2() {
        store2=findViewById(R.id.store2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("storeselection").whereEqualTo("product_number",2);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(store2);

                    }
                }
            }
        });

    }

    private void storelove1() {
store1=findViewById(R.id.store1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("storeselection").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(store1);

                    }
                }
            }
        });

    }

    private void newaelection2() {
        new2=findViewById(R.id.for2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("newselection").whereEqualTo("product_number",2);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(new2);

                    }
                }
            }
        });

    }

    private void newselection1() {

        new1=findViewById(R.id.for1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("newselection").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(new1);

                    }
                }
            }
        });

    }

    private void flashDeal4() {
        flash4=findViewById(R.id.flash4);
        txtflash4=findViewById(R.id.txtflash4);
        ptflash4=findViewById(R.id.txtprize4);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("fashashdeals").whereEqualTo("productid",4);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        String disc=document.getString("discount");
                        String prix=document.getString("product_prize");
                        Glide.with(MainActivity.this).load(image).into(flash4);
                        txtflash4.setText(disc);
                        ptflash4.setText("KES "+prix);



                    }
                }
            }
        });





    }

    private void flashDeal3() {
        flash3=findViewById(R.id.flash3);
        txtflash3=findViewById(R.id.txtflash3);
        pflash3=findViewById(R.id.txtprize3);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("fashashdeals").whereEqualTo("productid",3);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        String disc=document.getString("discount");
                        String prix=document.getString("product_prize");
                        Glide.with(MainActivity.this).load(image).into(flash3);
                        txtflash3.setText(disc);
                        pflash3.setText("KES "+prix);



                    }
                }
            }
        });
    }

    private void flashDeal2() {
        flash2=findViewById(R.id.flash2);
        txtflash2=findViewById(R.id.txtflash2);
        pflash2=findViewById(R.id.txtprize2);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("fashashdeals").whereEqualTo("productid",2);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        String disc=document.getString("discount");
                        String prix=document.getString("product_prize");
                        Glide.with(MainActivity.this).load(image).into(flash2);
                        txtflash2.setText(disc);
                        pflash2.setText("KES "+prix);



                    }
                }
            }
        });

    }

    private void intstantiateUser(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

    }


    private boolean isUserSignedIn(){
        if (mFirebaseUser == null){
            Intent pp=new Intent(MainActivity.this,Signup.class);
            startActivity(pp);
            finish();

            return false;
        }else{
            cartcount();
            return true;
        }
    }

    @Override
    protected void onStart() {
        isUserSignedIn();
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int myColor = Color.parseColor("#000000");
            window.setStatusBarColor(myColor);
        }
        V_play();

    }
    public void  SignOut(){
        mFirebaseAuth.getInstance().signOut();
        Intent sign=new Intent(MainActivity.this,Signup.class);
        startActivity(sign);
        finish();
    }
    public void accoutnpage(){
        Intent accountp=new Intent(MainActivity.this,Account.class);
        startActivity(accountp);

    }
    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    public void flashDeal1(){
        flash1=findViewById(R.id.flash1);
        txtflash1=findViewById(R.id.txtflash1);
        pflash1=findViewById(R.id.txtprize1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("fashashdeals").whereEqualTo("productid",1);

       query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   for (QueryDocumentSnapshot document : task.getResult()) {
                      String id=document.getId();
                      String image=document.getString("product_image");
                      String disc=document.getString("discount");
                      String prix=document.getString("product_prize");
                       Glide.with(MainActivity.this).load(image).into(flash1);
                       txtflash1.setText(disc);
                       pflash1.setText("KES "+prix);



                   }
               }
           }
       });






    }
    public void V_play(){
        VideoView mVideoView = (VideoView) findViewById(R.id.fvideo);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.addv;
        mVideoView.setVideoURI(Uri.parse(path));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.resume();


        mVideoView.setMediaController(null);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setVolume(0, 0);
                mp.setLooping(true);
            }
        });
    }


    public void Topsales1(){


        tops1=findViewById(R.id.top1);
        top2=findViewById(R.id.top2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("topselection").whereEqualTo("product_number",1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(tops1);



                    }
                }
            }
        });

    }

    public  void topsale2(){
        top2=findViewById(R.id.top2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("topselection").whereEqualTo("product_number",2);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id=document.getId();
                        String image=document.getString("product_image");
                        Glide.with(MainActivity.this).load(image).into(top2);

                    }
                }
            }
        });

    }




    public void cartcount() {


        String current_user;
        current_user = mFirebaseAuth.getCurrentUser().getUid();

        Query query=firebaseFirestore.collection("MyCart").whereEqualTo("user_id",current_user);
        query.addSnapshotListener(MainActivity.this,new EventListener<QuerySnapshot>() {
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

}
