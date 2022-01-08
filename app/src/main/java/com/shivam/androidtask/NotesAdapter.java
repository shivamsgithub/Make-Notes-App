package com.shivam.androidtask;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shivam.androidtask.activity.NotesDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesVH>{

    private List<NotesModel> list = new ArrayList<>();

    @NonNull
    @Override
    public NotesAdapter.notesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesAdapter.notesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.notesVH holder, int position) {


//        holder.itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(context, QuizResultActivity.class);
//                intent.putExtra("testID", data.getId());
//                intent.putExtra("title", data.getTopics());
//                context.startActivity(intent);
//
//        });

        holder.setData(list.get(position).getUrl(), list.get(position).getName(), list.get(position).getTopic());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class notesVH extends RecyclerView.ViewHolder {

        TextView tvHeading ;
        CircleImageView ivIcon;

        public notesVH(@NonNull View itemView) {
            super(itemView);

            tvHeading = itemView.findViewById(R.id.tv_category_heading);
            ivIcon = itemView.findViewById(R.id.iv_category_icon);
        }

        private void setData(String url,final String title, String topic){

            this.tvHeading.setText(title);

            itemView.setOnClickListener(view -> {
                Intent intentOne = new Intent(itemView.getContext(), NotesDetailsActivity.class);
                intentOne.putExtra("title", title);
                intentOne.putExtra("topic", topic);
                itemView.getContext().startActivity(intentOne);
            });
        }
    }
}
