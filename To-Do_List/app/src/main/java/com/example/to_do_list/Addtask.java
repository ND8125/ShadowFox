package com.example.to_do_list;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Addtask extends AppCompatActivity {
    private Database db;
    EditText taskTitleInput;
    RadioGroup priorityGroup;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new Database(this);
        taskTitleInput = findViewById(R.id.text);
        priorityGroup = findViewById(R.id.priorityGroup);
        saveBtn = findViewById(R.id.add);

        saveBtn.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString().trim();
            String priority = getSelectedPriority();

            if (title.isEmpty() || priority == null) {
                Toast.makeText(this, "Please enter title and select priority", Toast.LENGTH_SHORT).show();
                return;
            }

            Task Task = new Task(title, priority, false);
            db.insertTask(Task);
            setResult(RESULT_OK);
            finish();
        });
    }

    private String getSelectedPriority() {
        int id = priorityGroup.getCheckedRadioButtonId();
        if (id == R.id.High) return "High";
        if (id == R.id.Medium) return "Medium";
        if (id == R.id.Low) return "Low";
        return null;
    }
}

