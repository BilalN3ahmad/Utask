package com.utask.app.fragments;

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
import com.utask.app.R;
import com.utask.app.adapters.TasksAdapter;
import com.utask.app.data.Task;
import com.utask.app.data.TaskDatabaseHelper;
import java.util.List;

public class CompletedTasksFragment extends Fragment {
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
        adapter = new TasksAdapter(getContext(), null, null, null, null);
        recyclerView.setAdapter(adapter);
        
        loadCompletedTasks();
        return view;
    }

    private void loadCompletedTasks() {
        List<Task> tasks = dbHelper.getAllTasks(true);
        adapter.setTasks(tasks);
        
        if (tasks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
            tvEmptyView.setText(R.string.no_completed_tasks);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }
    }
}