package com.utask.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.utask.app.data.Task;
import com.utask.app.data.TaskDatabaseHelper;

public class EditTaskActivity extends AppCompatActivity {
    private TextInputEditText etTaskTitle, etTaskDescription, etTaskUrl;
    private Button btnSave, btnCancel;
    private TaskDatabaseHelper dbHelper;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new TaskDatabaseHelper(this);
        task = (Task) getIntent().getSerializableExtra("task");

        etTaskTitle = findViewById(R.id.et_task_title);
        etTaskDescription = findViewById(R.id.et_task_description);
        etTaskUrl = findViewById(R.id.et_task_url);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        // Set existing values
        etTaskTitle.setText(task.getTitle());
        etTaskDescription.setText(task.getDescription());
        etTaskUrl.setText(task.getUrl());
        btnSave.setText(R.string.save);

        btnSave.setOnClickListener(v -> updateTask());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void updateTask() {
        String title = etTaskTitle.getText().toString().trim();
        String description = etTaskDescription.getText().toString().trim();
        String url = etTaskUrl.getText().toString().trim();

        if (title.isEmpty()) {
            etTaskTitle.setError("Title is required");
            return;
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setUrl(url);
        dbHelper.updateTask(task);

        Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}