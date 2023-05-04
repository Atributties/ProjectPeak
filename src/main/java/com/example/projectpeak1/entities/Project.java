package com.example.projectpeak1.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Project {

    private int projectId;
    private String projectName;
    private String projectDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectEndDate;
    private int userId;


    public Project() {
    }

    public Project(int projectId, String projectName, String projectDescription, LocalDate projectStartDate, LocalDate projectEndDate, int userId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.userId = userId;
    }


    public Project(String projectName, String projectDescription, LocalDate projectStartDate, LocalDate projectEndDate, int userId) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate newProjectStartDate) {
        this.projectStartDate = newProjectStartDate;
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
