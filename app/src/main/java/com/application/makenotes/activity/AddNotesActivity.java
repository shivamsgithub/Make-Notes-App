package com.application.makenotes.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.application.makenotes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.richeditor.RichEditor;

public class AddNotesActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView ivImage;  // imageview
    private int GALLERY = 1, CAMERA = 2;
    RecyclerView rvImageList;
    EditText etTitle, etDescription;
    ImageView ivUndo, ivRedo, ivBold, ivUnderline;
    Button btnSaveNote;
    ViewGroup viewGroup;
    private final int REQ_CODE = 100;
    public RichEditor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        ivImage = findViewById(R.id.profile_image);
        rvImageList = findViewById(R.id.rv_image_list);
        ivUndo = findViewById(R.id.iv_undo);
        ivRedo = findViewById(R.id.iv_redo);
        ivBold = findViewById(R.id.iv_bold);
        ivUnderline = findViewById(R.id.iv_underline);
        mEditor = findViewById(R.id.editor);

        mEditor.setEditorFontSize(20);

        btnSaveNote = findViewById(R.id.btn_save_note);
//        etTitle = findViewById(R.id.etTitle);
//        etDescription = findViewById(R.id.etDescription);

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("HH:mm:ss  dd MMM yyyy")
                .format(new java.util.Date());

//        UndoRedoHelper undoRedoHelper = new UndoRedoHelper (etTitle);
        ivRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo(); // perform redo
            }
        });

        ivUndo.setOnClickListener(view -> {
            mEditor.undo();  // perform undo
        });

//        ivBold.setOnClickListener(view -> {
//            mEditor.setBold();
//        });
//
//        ivUnderline.setOnClickListener(view -> {
//            mEditor.setUnderline();
//        });

        Intent intentEdit = getIntent();
        boolean fromEdit = intentEdit.getBooleanExtra("edit", true);
        int idEdit = intentEdit.getIntExtra("id", 0);
        String titleEdit = intentEdit.getStringExtra("title");
//        String descriptionEdit = intentEdit.getStringExtra("description");
        String timeStampEdit = intentEdit.getStringExtra("timeStamp");
//        etTitle.setText(titleEdit);
//        etDescription.setText(descriptionEdit);
        mEditor.setHtml(titleEdit);

        btnSaveNote.setOnClickListener(view -> {

//            String title = etTitle.getText().toString();
//            String description = etDescription.getText().toString();
            Intent intent = new Intent(AddNotesActivity.this, HomeActivity.class);
            MyDBHandler db = new MyDBHandler(AddNotesActivity.this);
            String title = mEditor.getHtml().replaceAll("&nbsp;","").replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
//            String titleNew = title.replaceAll( "<br>", " \n ");
            Notes newNote = new Notes(title, timeStamp);
            if (title.isEmpty()){
                startActivity(intent);
                finish();
            } else {
                newNote.setTitle(title);

//            newNote.setDescription(mEditor.getHtml());
                newNote.setTimeStamp(timeStamp);
//            newNote.setImages(image);
                db.addNotes(newNote);
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Make Notes");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);// your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNotesActivity.this);
//        builder.setTitle("Save Or Not");
        builder.setMessage("Do you want to save this? ");

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("HH:mm:ss  dd MMM yyyy").format(new Date());


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AddNotesActivity.this, HomeActivity.class);
//                intent.putExtra("title", etTitle.getText().toString());
//                intent.putExtra("description", etDescription.getText().toString());
//                intent.putExtra("timeStamp", timeStamp);
//        intent.putExtra("image", image);

                MyDBHandler db = new MyDBHandler(AddNotesActivity.this);
                Notes newNote = new Notes();
                String title1 = mEditor.getHtml().replaceAll("&nbsp;","").replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
//                String titleee = title1.replaceAll("<br>","\n");
                if (title1.isEmpty()){
                    finish();
                }else{
                    newNote.setTitle(title1);
//            newNote.setDescription(mEditor.getHtml());
                    newNote.setTimeStamp(timeStamp);
//            newNote.setImages(image);
                    db.addNotes(newNote);
                    startActivity(intent);
                    finish();
                }
                AddNotesActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent1 = new Intent(AddNotesActivity.this, HomeActivity.class);
                startActivity(intent1);
                AddNotesActivity.super.onBackPressed();
            }
        });
        builder.show();
    }
}