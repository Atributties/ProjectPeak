package com.example.projectpeak1.controller;

import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.services.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping({""})
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private int getUserId(HttpSession session) {
        return (int) session.getAttribute("userId");
    }

    @GetMapping("/createTask/{id}")
    public String createTask(@PathVariable("id") int id, HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }

        model.addAttribute("projectId", id);
        model.addAttribute("Task", new Task());
        return "createTask";
    }


    @PostMapping(value = {"/createTask/{id}"})
    public String createTask(@PathVariable("id") int id, @ModelAttribute Task task) {

        taskService.createTask(task, id);
        return "redirect:/showProject/" + id;
    }



    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable("taskId") int taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("taskID", task.getProjectId());
        return "editTask";
    }

    @PostMapping("/editTask/{id}")
    public String updateTask(@PathVariable("id") int projectId, @ModelAttribute Task task) {
        task.setProjectId(projectId);
        taskService.editTask(task);
        return "redirect:/showProject/" + projectId;
    }


}
