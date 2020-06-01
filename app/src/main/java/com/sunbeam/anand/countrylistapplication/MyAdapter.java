package com.sunbeam.anand.countrylistapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ahmadrosid.svgloader.SvgLoader;

import java.util.List;


public class MyAdapter extends ArrayAdapter<Country> {

    private List<Country> countryList;
    private Bitmap bitmap;
    private Context mCtx;

    public MyAdapter(@NonNull Context context, @NonNull List<Country> countryList) {
        super(context, R.layout.list_item, countryList);
        this.countryList = countryList;
        this.mCtx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        convertView = inflater.inflate(R.layout.list_item, null, true);
        holder = new ViewHolder();
        //getting text views
        holder.textViewName = convertView.findViewById(R.id.textViewName);
        holder.textCapital = convertView.findViewById(R.id.textViewCapital);
        holder.imageView = convertView.findViewById(R.id.imageView);
        holder.textBorders = convertView.findViewById(R.id.textViewBorder);

        convertView.setTag(holder);
        //Getting the Country for the specified position
        Country country = countryList.get(position);
        String imageUrl = country.getImageUrl();
        String capital = country.getCapital();
        String countryName = country.getName();

        holder.textViewName.setText(countryName);
        holder.textCapital.setText(capital);
        holder.textBorders.setText(country.getBorders());
        SvgLoader.pluck()
                .with((Activity) mCtx).setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(imageUrl, holder.imageView);
        return convertView;

    }


    static class ViewHolder {
        TextView textViewName;
        TextView textCapital;
        TextView textBorders;
        ImageView imageView;
    }
}
