package com.groupon.vgudla.imagesearch.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupon.vgudla.imagesearch.R;
import com.groupon.vgudla.imagesearch.model.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Image> {
    public ImageAdapter(Context context, List<Image> images) {
        super(context, R.layout.item_image, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        Picasso.with(getContext()).load(image.getThumbNailUrl()).into(imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.tvTitle);
        textView.setText(Html.fromHtml(image.getTitle()));
        return convertView;
    }
}
