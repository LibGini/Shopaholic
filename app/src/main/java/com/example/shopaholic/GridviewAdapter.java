package com.example.shopaholic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GridviewAdapter extends BaseAdapter{
    private ImageView imageView;
    private Context context;
    private LayoutInflater inflater;
    private TextView title,prizing;
    private ArrayList<productModelClass> list;

    public GridviewAdapter(Context context, ArrayList<productModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
     return    list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_card, parent, false);
            imageView = convertView.findViewById(R.id.grid_pro_image);
            title=convertView.findViewById(R.id.grid_pro_title);
            prizing=convertView.findViewById(R.id.prizingTxt);



        }

        Glide.with(context).load(list.get(position).getProduct_image()).apply(RequestOptions.skipMemoryCacheOf(true).centerCrop()).into(imageView);
        String details=list.get(position).getProduct_name();
        title.setText(details);
        Integer setPrizee=list.get(position).getProduct_prize();
        String prizingAsString = Integer.toString(setPrizee);
        prizing.setText(prizingAsString);
        return convertView;
    }
}
