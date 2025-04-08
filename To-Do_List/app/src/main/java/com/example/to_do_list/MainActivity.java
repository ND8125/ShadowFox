package com.example.to_do_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private Database db;
    RecyclerView recyclerView;
    TaskAdapter adapter;
    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recyclerView);
        db=new Database(this);

        loadTasks();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, Addtask.class),1);
            }
        });

    }

    private void loadTasks() {
        taskList = db.getAllTasks();
        sortTasksByPriority();
        adapter = new TaskAdapter(taskList, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void sortTasksByPriority(){
        taskList.sort((a, b) -> {
            return getPriorityValue(a.getPriority()) - getPriorityValue(b.getPriority());
        });
    }

    private int getPriorityValue(String priority){
        switch (priority) {
            case "High": return 1;
            case "Medium": return 2;
            default: return 3;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadTasks();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}