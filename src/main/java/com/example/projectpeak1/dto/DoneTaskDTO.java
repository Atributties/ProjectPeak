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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectCompletedDate;
    private int projectExpectedDays;
    private int projectUsedDays;
}
