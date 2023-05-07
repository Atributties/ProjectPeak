package com.example.projectpeak1.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SubTask {
    private int subTaskId;
    private String SubTaskName;
    private String subTaskDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subTaskStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subTaskEndDate;
    private int userId;

    public SubTask(int subTaskId, String subTaskName, String subTaskDescription, LocalDate subTaskStartDate, LocalDate subTaskEndDate, int userId) {
        this.subTaskId = subTaskId;
        SubTaskName = subTaskName;
        this.subTaskDescription = subTaskDescription;
        this.subTaskStartDate = subTaskStartDate;
        this.subTaskEndDate = subTaskEndDate;
        this.userId = userId;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public String getSubTaskName() {
        return SubTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        SubTaskName = subTaskName;
    }

    public String getSubTaskDescription() {
        return subTaskDescription;
    }

    public void setSubTaskDescription(String subTaskDescription) {
        this.subTaskDescription = subTaskDescription;
    }

    public LocalDate getSubTaskStartDate() {
        return subTaskStartDate;
    }

    public void setSubTaskStartDate(LocalDate subTaskStartDate) {
        this.subTaskStartDate = subTaskStartDate;
    }

    public LocalDate getSubTaskEndDate() {
        return subTaskEndDate;
    }

    public void setSubTaskEndDate(LocalDate subTaskEndDate) {
        this.subTaskEndDate = subTaskEndDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
