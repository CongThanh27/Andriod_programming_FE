package com.marwaeltayeb.souq.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.squareup.picasso.Picasso;

public class NCCAdapter extends RecyclerView.Adapter<NCCAdapter.ViewHolder> {



    private String[] mImageUrls;

    public NCCAdapter(String[] imageUrls) {
        mImageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(mImageUrls[position])
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mImageUrls.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView1);
        }
    }
}