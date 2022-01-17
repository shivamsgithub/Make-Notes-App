package com.shivam.androidtask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.shivam.androidtask.activity.AddNotesActivity;
import com.shivam.androidtask.activity.MyDBHandler;
import com.shivam.androidtask.activity.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesVH> {

    private List<Notes> list;
    private Context context;

    public NotesAdapter(List<Notes> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesAdapter.notesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesAdapter.notesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.notesVH holder, int position) {

        MyDBHandler db = new MyDBHandler(context);

        if (!((list.get(position).getTitle()).isEmpty())) {
            holder.tvTitle.setText(list.get(position).getTitle());
        }

//        if (!((list.get(position).getDescription()).isEmpty())){
//            holder.tvDescription.setVisibility(View.VISIBLE);
//            holder.tvDescription.setText(list.get(position).getDescription());
//        }else {
//            holder.tvDescription.setVisibility(View.GONE);
//        }
        holder.tvTimeStamp.setText(list.get(position).getTimeStamp());

        holder.itemView.setOnClickListener(v -> {
            Intent intentEdit = new Intent(context, AddNotesActivity.class);
            intentEdit.putExtra("edit", true);
            intentEdit.putExtra("id", list.get(position).getId());
            intentEdit.putExtra("title", list.get(position).getTitle());
//            intentEdit.putExtra("description", list.get(position).getDescription());
            intentEdit.putExtra("timeStamp", list.get(position).getTimeStamp());
            context.startActivity(intentEdit);
        });


//        ImagesAdapter imagesAdapter = new ImagesAdapter();
//        holder.rvImagesList.setAdapter(imagesAdapter);

//         imagesAdapter.addData(allNotes);

        holder.ivMore.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.all_options_dialog, holder.viewGroup, false);

            holder.tvDelete = dialogView.findViewById(R.id.tv_delete);
            holder.tvCancel = dialogView.findViewById(R.id.tv_cancel);
            holder.tvShare = dialogView.findViewById(R.id.tv_share);
            holder.tvEdit = dialogView.findViewById(R.id.tv_edit);

            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            holder.tvEdit.setOnClickListener(view1 -> {
                Intent intentEdit = new Intent(context, AddNotesActivity.class);
                intentEdit.putExtra("edit", true);
                intentEdit.putExtra("id", list.get(position).getId());
                intentEdit.putExtra("title", list.get(position).getTitle());
//                intentEdit.putExtra("description", list.get(position).getDescription());
                intentEdit.putExtra("timeStamp", list.get(position).getTimeStamp());
                context.startActivity(intentEdit);
            });

            holder.tvShare.setOnClickListener(v-> {
                Intent shareIntent =  new Intent(android.content.Intent.ACTION_SEND);
//set type
                shareIntent.setType("text/plain");
//add what a subject you want
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"add what a subject you want");
//message
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "" + list.get(position).getTitle() + "\n" +  list.get(position).getDescription() + "");
//start sharing via
                context.startActivity(Intent.createChooser(shareIntent,"Share via"));
            });

            holder.tvDelete.setOnClickListener(view2 -> {
                alertDialog.dismiss();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                View dialogView1 = LayoutInflater.from(view.getContext())
                        .inflate(R.layout.delete_dialog, holder.viewGroup, false);

                builder1.setView(dialogView1);
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();

                holder.tvDelete1 = dialogView1.findViewById(R.id.tv_delete_del);
                holder.tvCancel1 = dialogView1.findViewById(R.id.tv_cancel_del);


                holder.tvDelete1.setOnClickListener(v1 ->{
                    db.deleteNotesById(list.get(holder.getAdapterPosition()).getId());

                    // These two lines added for remove data from adapter also.
                    list.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                    alertDialog1.dismiss();

//                list.remove(list.get(position).getId());
//                db.deleteNotesById(list.get(position).getId());
////                notifyDataSetChanged();
//                alertDialog.dismiss();
                });

                holder.tvCancel1.setOnClickListener(view3 -> {
                    alertDialog.dismiss();
                });

            });

            holder.tvCancel.findViewById(R.id.tv_cancel).setOnClickListener(view4 -> {
                alertDialog.dismiss();
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<Notes> items) {
//        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

//    public void clearData() {
//        list.clear();
//        notifyDataSetChanged();
//    }

    public class notesVH extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvTimeStamp;
        RecyclerView rvImagesList;
        ViewGroup viewGroup;
        TextView tvDelete, tvCancel, tvShare, tvEdit, tvDelete1, tvCancel1;
        ImageView ivMore;

        public notesVH(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvDescription = itemView.findViewById(R.id.tv_description);
//            rvImagesList = itemView.findViewById(R.id.rv_images_in_notes);
            viewGroup = itemView.findViewById(android.R.id.content);
            ivMore = itemView.findViewById(R.id.iv_more);
            tvTimeStamp = itemView.findViewById(R.id.tv_timeStamp);
        }
    }
}