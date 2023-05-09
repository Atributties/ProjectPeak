package com.example.projectpeak1.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SubTask {
    private int subTaskId;
    private double subtaskNumber;
    private String subTaskName;
    private String subTaskDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subTaskStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subTaskEndDate;
    private String status;
    private int taskId;

    public SubTask(int subTaskId, double subtaskNumber, String subTaskName, String subTaskDescription, LocalDate subTaskStartDate, LocalDate subTaskEndDate,String status, int taskId) {
        this.subTaskId = subTaskId;
        this.subtaskNumber = subtaskNumber;
        this.subTaskName = subTaskName;
        this.subTaskDescription = subTaskDescription;
        this.subTaskStartDate = subTaskStartDate;
        this.subTaskEndDate = subTaskEndDate;
        this.status = status;
        this.taskId = taskId;
    }
    public SubTask(double subtaskNumber, String subTaskName, String subTaskDescription, LocalDate subTaskStartDate, LocalDate subTaskEndDate,String status, int taskId) {
        this.subtaskNumber = subtaskNumber;
        this.subTaskName = subTaskName;
        this.subTaskDescription = subTaskDescription;
        this.subTaskStartDate = subTaskStartDate;
        this.subTaskEndDate = subTaskEndDate;
        this.status = status;
        this.taskId = taskId;
    }

    public SubTask() {
    }

    public double getSubtaskNumber() {
        return subtaskNumber;
    }

    public void setSubtaskNumber(double subtaskNumber) {
        this.subtaskNumber = subtaskNumber;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
