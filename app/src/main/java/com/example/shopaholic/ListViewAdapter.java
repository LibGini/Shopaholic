package com.example.shopaholic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    //variables
    Context mContext;
    LayoutInflater inflater;
    List<model> modellist;
    ArrayList<model> arrayList;

    //constructor
    public ListViewAdapter(Context context, List<model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<model>();
        this.arrayList.addAll(modellist);
    }
    public class ViewHolder{
        TextView DetailCat;
        ImageView IconCat;
    }
    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return modellist.indexOf(getItem(i));

    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.cat_layout, null);

            //locate the views in row.xml
            holder.IconCat = view.findViewById(R.id.catIm);
            holder.DetailCat = view.findViewById(R.id.cattext);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.DetailCat.setText(modellist.get(postition).getTitle());
        //set the result in imageview
        holder.IconCat.setImageResource(modellist.get(postition).getIcon());

        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code later


            }
        });

            view.setClickable(false);
        return view;
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }
        else {
            for (model model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


}
