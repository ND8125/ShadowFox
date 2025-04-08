package com.example.to_do_list;

public class Task {
    private int id;
    private String tittle;
    private String priority;
    private boolean isCompleted;

    public Task(int id, String title, String priority, boolean isCompleted) {
        this.id = id;
        this.tittle = title;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public Task(String title, String priority, boolean isCompleted) {
        this.tittle = title;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public int getId() { return id; }
    public String getTitle() { return tittle; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
