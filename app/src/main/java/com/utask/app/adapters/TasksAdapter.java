package com.utask.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.utask.app.R;
import com.utask.app.data.Task;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> tasks;
    private OnTaskClickListener onTaskClickListener;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private OnUrlClickListener onUrlClickListener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public interface OnEditClickListener {
        void onEditClick(Task task);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Task task);
    }

    public interface OnUrlClickListener {
        void onUrlClick(String url);
    }

    public TasksAdapter(Context context, 
                       OnTaskClickListener onTaskClickListener,
                       OnEditClickListener onEditClickListener,
                       OnDeleteClickListener onDeleteClickListener,
                       OnUrlClickListener onUrlClickListener) {
        this.context = context;
        this.onTaskClickListener = onTaskClickListener;
        this.onEditClickListener = onEditClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onUrlClickListener = onUrlClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.tvTaskDescription.setText(task.getDescription());

        if (task.getUrl() != null && !task.getUrl().isEmpty()) {
            holder.btnOpenUrl.setVisibility(View.VISIBLE);
            holder.btnOpenUrl.setOnClickListener(v -> {
                if (onUrlClickListener != null) {
                    onUrlClickListener.onUrlClick(task.getUrl());
                }
            });
        } else {
            holder.btnOpenUrl.setVisibility(View.GONE);
        }

        if (onTaskClickListener != null) {
            holder.itemView.setOnClickListener(v -> onTaskClickListener.onTaskClick(task));
        }

        if (onEditClickListener != null) {
            holder.btnEdit.setOnClickListener(v -> onEditClickListener.onEditClick(task));
        } else {
            holder.btnEdit.setVisibility(View.GONE);
        }

        if (onDeleteClickListener != null) {
            holder.btnDelete.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(task));
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle, tvTaskDescription;
        Button btnOpenUrl, btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            tvTaskDescription = itemView.findViewById(R.id.tv_task_description);
            btnOpenUrl = itemView.findViewById(R.id.btn_open_url);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}