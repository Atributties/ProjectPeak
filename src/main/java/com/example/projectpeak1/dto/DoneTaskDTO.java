package com.example.projectpeak1.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DoneTaskDTO {

    private int taskId;
    private String taskName;
    private String taskDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskEndDate;
    private String status;
    private int projectId;
    private int daysToStart;
    private int daysSubtask;
    private int daysLeft;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskCompletedDate;
    private int taskExpectedDays;
    private int taskUsedDays;


    public DoneTaskDTO(int taskId, String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskEndDate, String status, int projectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.status = status;
        this.projectId = projectId;
    }

    public DoneTaskDTO() {
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(LocalDate taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public LocalDate getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(LocalDate taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getDaysToStart() {
        return daysToStart;
    }

    public void setDaysToStart(int daysToStart) {
        this.daysToStart = daysToStart;
    }

    public int getDaysSubtask() {
        return daysSubtask;
    }

    public void setDaysSubtask(int daysSubtask) {
        this.daysSubtask = daysSubtask;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public LocalDate getTaskCompletedDate() {
        return taskCompletedDate;
    }

    public void setTaskCompletedDate(LocalDate taskCompletedDate) {
        this.taskCompletedDate = taskCompletedDate;
    }

    public int getTaskExpectedDays() {
        return taskExpectedDays;
    }

    public void setTaskExpectedDays(int taskExpectedDays) {
        this.taskExpectedDays = taskExpectedDays;
    }

    public int getTaskUsedDays() {
        return taskUsedDays;
    }

    public void setTaskUsedDays(int taskUsedDays) {
        this.taskUsedDays = taskUsedDays;
    }
}
