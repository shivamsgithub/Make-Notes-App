package com.shivam.androidtask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.shivam.androidtask.NotesAdapter;
import com.shivam.androidtask.NotesModel;
import com.shivam.androidtask.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    NotesAdapter adapter;
    private List<NotesModel> list = new ArrayList<>();
    Button btnAddNote ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rvCategory = findViewById(R.id.rv_notes);
        btnAddNote = findViewById(R.id.btn_add_note);
        btnAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddNotesActivity.class);
            startActivity(intent);
        });

        adapter = new NotesAdapter();
        rvCategory.setAdapter(adapter);
    }
}