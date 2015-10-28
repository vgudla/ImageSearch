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
    static class ImageViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public ImageAdapter(Context context, List<Image> images) {
        super(context, R.layout.item_image, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = getItem(position);
        ImageViewHolder imageViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
            imageViewHolder = new ImageViewHolder();
            imageViewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivImage);
            imageViewHolder.textView = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(imageViewHolder);
        } else {
            imageViewHolder = (ImageViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(image.getThumbNailUrl()).into(imageViewHolder.imageView);
        imageViewHolder.textView.setText(Html.fromHtml(image.getTitle()));
        return convertView;
    }
}
