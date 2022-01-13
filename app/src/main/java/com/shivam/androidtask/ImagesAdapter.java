package com.shivam.androidtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shivam.androidtask.activity.Notes;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.imagesVH> {

    private List<Notes> list;
    private Context context;

    @NonNull
    @Override
    public ImagesAdapter.imagesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagesAdapter.imagesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_imageview_60dp, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.imagesVH holder, int position) {

//        Glide.with(context).load(R.drawable.logo).into(holder.ivImage);
        holder.ivImage.setImageResource(R.drawable.logo);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

//    public void addData(List<Notes> items) {
//        list.addAll(items);
//        notifyDataSetChanged();
//    }

    public class imagesVH extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public imagesVH(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image_view);
        }
    }
}
