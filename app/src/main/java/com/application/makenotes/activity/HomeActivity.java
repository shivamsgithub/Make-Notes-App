package com.application.makenotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.makenotes.NotesAdapter;
import com.application.makenotes.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    NotesAdapter adapter;
    Button btnAddNote ;
    RecyclerView rvAllNotes;
    ArrayList<Notes> list = new ArrayList<>() ;
    TextView tvNotesCount, notesMsg;
    boolean noteDefault = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        toolbar.setTitle("Make Notes");

        rvAllNotes = findViewById(R.id.rv_notes);
        btnAddNote = findViewById(R.id.btn_add_note);
        RecyclerView rvNotes = findViewById(R.id.rv_notes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvNotes.setLayoutManager(linearLayoutManager);
        tvNotesCount = findViewById(R.id.tv_notes_count);

        MyDBHandler db = new MyDBHandler(HomeActivity.this);
        List<Notes> allNotes = db.getAllNotes();

        Collections.reverse(allNotes);
        adapter = new NotesAdapter(list, HomeActivity.this);
        rvNotes.setAdapter(adapter);
        adapter.addData(allNotes);

        notesMsg = findViewById(R.id.notes_msg);
        if (allNotes.size() == 0){
            notesMsg.setVisibility(View.VISIBLE);
        } else {
            notesMsg.setVisibility(View.GONE);
        }

//        tvNotesCount.setText( "Total Notes : " + allNotes.size());

        btnAddNote.setOnClickListener(view -> {
            // Going from MainActivity to NotesEditorActivity
            Intent intent1 = new Intent(HomeActivity.this, AddNotesActivity.class);
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