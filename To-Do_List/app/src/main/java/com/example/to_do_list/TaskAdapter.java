package com.example.to_do_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final ArrayList<Task> tasks;
    private final Database db;

    public TaskAdapter(ArrayList<Task> tasks, Database db) {
        this.tasks = tasks;
        this.db = db;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView title, priority;
        Button deleteBtn;

        public TaskViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            title = itemView.findViewById(R.id.taskTitle);
            priority = itemView.findViewById(R.id.taskPriority);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.priority.setText("Priority: " + task.getPriority());
        holder.checkBox.setChecked(task.isCompleted());

        holder.deleteBtn.setOnClickListener(v -> {
            db.deletetask(task.getId());
            tasks.remove(position);
            notifyItemRemoved(position);
        });
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }
}

