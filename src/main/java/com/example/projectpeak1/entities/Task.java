package com.example.projectpeak1.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {
    private int taskId;
    private String taskName;
    private String taskDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskEndDate;
    private int userId;



}
