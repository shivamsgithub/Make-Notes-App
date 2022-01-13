package com.shivam.androidtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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


//        holder.itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(context, QuizResultActivity.class);
//                intent.putExtra("testID", data.getId());
//                intent.putExtra("title", data.getTopics());
//                context.startActivity(intent);
//
//        });

        MyDBHandler db = new MyDBHandler(context);
        List<Notes> allNotes = db.getAllNotes();

        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvDescription.setText(list.get(position).getDescription());
        ImagesAdapter imagesAdapter = new ImagesAdapter();
        holder.rvImagesList.setAdapter(imagesAdapter);
//         imagesAdapter.addData(allNotes);


//        holder.itemView.setOnClickListener(view -> {
//            Intent intentOne = new Intent(holder.itemView.getContext(), AddNotesActivity.class);
//            intentOne.putExtra("id", list.get(position).getId());
//            holder.itemView.getContext().startActivity(intentOne);
//        });

        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.delete_dialog, holder.viewGroup, false);

            holder.tvDelete = dialogView.findViewById(R.id.tv_delete);
            holder.tvCancel = dialogView.findViewById(R.id.tv_cancel);

            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            holder.tvDelete.setOnClickListener(view1 -> {

                db.deleteNotesById(list.get(holder.getAdapterPosition()).getId());

                // These two lines added for remove data from adapter also.
                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                alertDialog.dismiss();

//                list.remove(list.get(position).getId());
//                db.deleteNotesById(list.get(position).getId());
////                notifyDataSetChanged();
//                alertDialog.dismiss();
            });

            holder.tvCancel.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> {
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

        TextView tvTitle, tvDescription;
        RecyclerView rvImagesList;
        ViewGroup viewGroup;
        TextView tvDelete, tvCancel;

        public notesVH(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            rvImagesList = itemView.findViewById(R.id.rv_images_in_notes);
            viewGroup = itemView.findViewById(android.R.id.content);
        }
    }
}