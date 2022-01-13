package com.shivam.androidtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shivam.androidtask.NotesAdapter;
import com.shivam.androidtask.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    NotesAdapter adapter;
    Button btnAddNote ;
    RecyclerView rvAllNotes;
    ArrayList<Notes> list = new ArrayList<>() ;
    TextView tvNotesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvAllNotes = findViewById(R.id.rv_notes);
        btnAddNote = findViewById(R.id.btn_add_note);
        RecyclerView rvNotes = findViewById(R.id.rv_notes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvNotes.setLayoutManager(linearLayoutManager);
        tvNotesCount = findViewById(R.id.tv_notes_count);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        byte[] image = intent.getByteArrayExtra("image");

        MyDBHandler db = new MyDBHandler(HomeActivity.this);


        List<Notes> allNotes = db.getAllNotes();
//        Collections.reverse(allNotes);
//        for (Notes notes : allNotes) {
//
            Notes newNote = new Notes();
            newNote.setTitle(title);
            newNote.setDescription(description);
            newNote.setImages(image);
            db.addNotes(newNote);

//            Log.d("dbShivam", title);
            allNotes.add(newNote);
            Collections.reverse(allNotes);
//            int count =  1 + allNotes.size();
//            String totalCount = String.valueOf(count);
            tvNotesCount.setText("Total : " + allNotes.size());
////        }

        adapter = new NotesAdapter(list, HomeActivity.this);
        rvNotes.setAdapter(adapter);
        adapter.addData(allNotes);

        btnAddNote.setOnClickListener(view -> {
            // Going from MainActivity to NotesEditorActivity
            Intent intent1 = new Intent(getApplicationContext(), AddNotesActivity.class);
            startActivity(intent1);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}