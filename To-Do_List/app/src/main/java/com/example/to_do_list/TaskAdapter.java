package com.example.to_do_list;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

        holder.checkBox.setOnCheckedChangeListener(null);

        holder.title.setText(task.getTitle());
        holder.priority.setText("Priority: " + task.getPriority());
        holder.checkBox.setChecked(task.isCompleted());


        if (task.isCompleted()) {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }


        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            db.updateTask(task);
            notifyItemChanged(position);
        });


        holder.deleteBtn.setOnClickListener(v -> {
            if (task.isCompleted()) {
                db.deletetask(task.getId());
                tasks.remove(position);
                notifyItemRemoved(position);
            } else {
                Toast.makeText(v.getContext(), "Please complete the task before deleting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}
