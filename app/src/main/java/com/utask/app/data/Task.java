package com.utask.app.data;

public class Task {
    private int id;
    private String title;
    private String description;
    private String url;
    private boolean isCompleted;
    private long createdAt;

    public Task() {}

    public Task(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.isCompleted = false;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}