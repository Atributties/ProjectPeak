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
}
