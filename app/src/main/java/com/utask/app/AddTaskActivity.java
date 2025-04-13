package com.utask.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.utask.app.data.Task;
import com.utask.app.data.TaskDatabaseHelper;

public class AddTaskActivity extends AppCompatActivity {
    private TextInputEditText etTaskTitle, etTaskDescription, etTaskUrl;
    private Button btnSave, btnCancel;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new TaskDatabaseHelper(this);

        etTaskTitle = findViewById(R.id.et_task_title);
        etTaskDescription = findViewById(R.id.et_task_description);
        etTaskUrl = findViewById(R.id.et_task_url);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(v -> saveTask());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveTask() {
        String title = etTaskTitle.getText().toString().trim();
        String description = etTaskDescription.getText().toString().trim();
        String url = etTaskUrl.getText().toString().trim();

        if (title.isEmpty()) {
            etTaskTitle.setError("Title is required");
            return;
        }

        Task task = new Task(title, description, url);
        dbHelper.addTask(task);

        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}