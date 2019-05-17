package com.example.shopaholic;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    int[] icon;
    ArrayList<model> arrayList = new ArrayList<model>();
    private android.support.v7.widget.SearchView searchViewHolder;
     private ImageView searchimg;
     private ImageView backpress;
    List<model> rowItems;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        title = new String[]{"Automobiles & Motorcycles", "Beauty & Health", "Cellphones & Telecommunication", "Computer & Office", "Consumer Electronics","Electronic Concepts & Supplies",
                "Furniture","Hair Extensions & Wigs","Home Appliances","Home Improvements","Jewelry & Accessories","Light & Lighting","luggage & Bags","Mother & Kids","Novelty & Special Use",
        "office & School","Security & Protection","Shoes","Sports & Protection","Tools","Toys & Hobbies","Underwear & Sleepwear","Watches","Wedding & Events"};
       icon = new int[]{R.drawable.automobile, R.drawable.beauty, R.drawable.cellphone, R.drawable.comptele,R.drawable.radio,
               R.drawable.electronics,R.drawable.armchair,R.drawable.wig,R.drawable.iron,R.drawable.sink,R.drawable.ring,R.drawable.bulb
       ,R.drawable.briefcase,R.drawable.mother,R.drawable.bunny,R.drawable.blackboard,R.drawable.hand,R.drawable.alls,R.drawable.bike,
               R.drawable.driller,R.drawable.bear,R.drawable.underwear,R.drawable.watches,R.drawable.wedding};


        listView = findViewById(R.id.mylist);
        for (int i =0; i<title.length; i++){
            model model = new model(title[i],icon[i]);
            //bind all strings in an array
            arrayList.add(model);
        }

        //pass results to listViewAdapter class
        adapter = new ListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

        backpress=findViewById(R.id.button_back);

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        listView.setOnItemClickListener(this);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id== R.id.action_settings){
            //do your functionality here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cat_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent back=new Intent(CategoryActivity.this,MainActivity.class);
        startActivity(back);
        finish();
    }


    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
       model itemclick=arrayList.get(position);
        Intent myint= new Intent(CategoryActivity.this,All_cartegory.class);
        myint.putExtra("My_Category",itemclick.getTitle());
        startActivity(myint);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
