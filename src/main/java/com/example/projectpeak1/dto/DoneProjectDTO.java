package com.example.projectpeak1.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DoneProjectDTO {

    private int projectId;
    private String projectName;
    private String projectDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectEndDate;
    private int userId;
    private int daysToStart;
    private int daysForProject;
    private int daysLeft;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectCompletedDate;
    private int projectExpectedDays;
    private int projectUsedDays;


    public DoneProjectDTO() {
    }

    public DoneProjectDTO(int projectId, String projectName, String projectDescription, LocalDate projectStartDate, LocalDate projectEndDate, int userId) {
        this.projectId = projectId;
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

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
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

    public int getDaysToStart() {
        return daysToStart;
    }

    public void setDaysToStart(int daysToStart) {
        this.daysToStart = daysToStart;
    }

    public int getDaysForProject() {
        return daysForProject;
    }

    public void setDaysForProject(int daysForProject) {
        this.daysForProject = daysForProject;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public LocalDate getProjectCompletedDate() {
        return projectCompletedDate;
    }

    public void setProjectCompletedDate(LocalDate projectCompletedDate) {
        this.projectCompletedDate = projectCompletedDate;
    }

    public int getProjectExpectedDays() {
        return projectExpectedDays;
    }

    public void setProjectExpectedDays(int projectExpectedDays) {
        this.projectExpectedDays = projectExpectedDays;
    }

    public int getProjectUsedDays() {
        return projectUsedDays;
    }

    public void setProjectUsedDays(int projectUsedDays) {
        this.projectUsedDays = projectUsedDays;
    }
}
