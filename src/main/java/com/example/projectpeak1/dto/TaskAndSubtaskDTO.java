package com.example.projectpeak1.dto;

import com.example.projectpeak1.entities.Subtask;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class TaskAndSubtaskDTO {

    private int id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String status;
    private int projectId;
    private int daysToStart;
    private int daysTask;
    private int daysLeft;
    private List<Subtask> subTaskList;


    public TaskAndSubtaskDTO(int id, String name, String description, LocalDate startDate, LocalDate endDate, String status, int projectId, List<Subtask> subTaskList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectId = projectId;
        this.subTaskList = subTaskList;
    }

    public TaskAndSubtaskDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public List<Subtask> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<Subtask> subTaskList) {
        this.subTaskList = subTaskList;
    }

    public int getDaysToStart() {
        return daysToStart;
    }

    public void setDaysToStart(int daysToStart) {
        this.daysToStart = daysToStart;
    }

    public int getDaysTask() {
        return daysTask;
    }

    public void setDaysTask(int daysTask) {
        this.daysTask = daysTask;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }
}
