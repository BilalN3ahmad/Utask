package com.utask.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.utask.app.AddTaskActivity;
import com.utask.app.EditTaskActivity;
import com.utask.app.R;
import com.utask.app.data.Task;
import com.utask.app.data.TaskDatabaseHelper;
import com.utask.app.adapters.TasksAdapter;
import java.util.List;

public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView tvEmptyView;
    private TasksAdapter adapter;
    private TaskDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_view);
        tvEmptyView = view.findViewById(R.id.tv_empty_view);
        dbHelper = new TaskDatabaseHelper(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TasksAdapter(getContext(), task -> {
            // Handle task click (mark as completed)
            task.setCompleted(true);
            dbHelper.updateTask(task);
            loadTasks();
        }, task -> {
            // Handle edit click
            Intent intent = new Intent(getContext(), EditTaskActivity.class);
            intent.putExtra("task", task);
            startActivityForResult(intent, 1);
        }, task -> {
            // Handle delete click
            dbHelper.deleteTask(task.getId());
            loadTasks();
        }, url -> {
            // Handle URL click
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
        
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        List<Task> tasks = dbHelper.getAllTasks(false);
        adapter.setTasks(tasks);
        
        if (tasks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
            tvEmptyView.setText(R.string.no_tasks);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }
    }
}