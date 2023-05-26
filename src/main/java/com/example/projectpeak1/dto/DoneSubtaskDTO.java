package com.example.projectpeak1.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DoneSubtaskDTO {

    private int subTaskId;
    private String subTaskName;
    private String subTaskDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subTaskStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subTaskEndDate;
    private String status;
    private int taskId;
    private int daysToStart;
    private int daysSubtask;
    private int daysLeft;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subtaskCompletedDate;
    private int subtaskExpectedDays;
    private int subtaskUsedDays;

    public DoneSubtaskDTO(int subTaskId, String subTaskName, String subTaskDescription, LocalDate subTaskStartDate, LocalDate subTaskEndDate, String status, int taskId, int daysToStart, int daysSubtask, int daysLeft, LocalDate subtaskCompletedDate, int subtaskExpectedDays, int subtaskUsedDays) {
        this.subTaskId = subTaskId;
        this.subTaskName = subTaskName;
        this.subTaskDescription = subTaskDescription;
        this.subTaskStartDate = subTaskStartDate;
        this.subTaskEndDate = subTaskEndDate;
        this.status = status;
        this.taskId = taskId;
        this.daysToStart = daysToStart;
        this.daysSubtask = daysSubtask;
        this.daysLeft = daysLeft;
        this.subtaskCompletedDate = subtaskCompletedDate;
        this.subtaskExpectedDays = subtaskExpectedDays;
        this.subtaskUsedDays = subtaskUsedDays;
    }

    public DoneSubtaskDTO(int subTaskId, String subTaskName, String subTaskDescription, LocalDate subTaskStartDate, LocalDate subTaskEndDate, String status, int taskId) {
        this.subTaskId = subTaskId;
        this.subTaskName = subTaskName;
        this.subTaskDescription = subTaskDescription;
        this.subTaskStartDate = subTaskStartDate;
        this.subTaskEndDate = subTaskEndDate;
        this.status = status;
        this.taskId = taskId;
    }

    public DoneSubtaskDTO() {
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

    public LocalDate getSubtaskCompletedDate() {
        return subtaskCompletedDate;
    }

    public void setSubtaskCompletedDate(LocalDate subtaskCompletedDate) {
        this.subtaskCompletedDate = subtaskCompletedDate;
    }

    public int getSubtaskExpectedDays() {
        return subtaskExpectedDays;
    }

    public void setSubtaskExpectedDays(int subtaskExpectedDays) {
        this.subtaskExpectedDays = subtaskExpectedDays;
    }

    public int getSubtaskUsedDays() {
        return subtaskUsedDays;
    }

    public void setSubtaskUsedDays(int subtaskUsedDays) {
        this.subtaskUsedDays = subtaskUsedDays;
    }
}
