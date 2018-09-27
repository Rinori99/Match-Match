package com.example.asus.testmatch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.asus.testmatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImageAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private int layoutResource;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context context, int layoutResource, ArrayList<String> imgURLs) {
        super(context, layoutResource, imgURLs);
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.layoutResource = layoutResource;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder{
        SquareImageView image;
        ProgressBar mProgressBar;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;

        if(convertView ==null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.gridImageProgressbar);
            viewHolder.image = (SquareImageView)convertView.findViewById(R.id.gridImageView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        String imgURL = getItem(position);

        UniversalImageLoader.setImage(imgURL, viewHolder.image, viewHolder.mProgressBar, mContext);

        return convertView;
    }
}
