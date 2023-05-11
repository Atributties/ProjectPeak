package com.example.projectpeak1.controller;

import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.services.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;


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
    public String editTask(HttpSession session,@PathVariable("taskId") int taskId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = taskService.getUserFromId(userId);
        model.addAttribute("user", user);
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


    @GetMapping(value = {"/deleteTask/{id}"})
    public String deleteTask(HttpSession session, @PathVariable("id") int taskId) throws LoginException {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        taskService.deleteTask(taskId);
        return "redirect:/userFrontend";

        //TODO add access denied like the one from wishlist
    }

    @GetMapping("/addTask/{id}")
    public String createTask(HttpSession session, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = taskService.getUserFromId(userId);
        Project project = taskService.getProjectFromId(projectId);
        model.addAttribute("user", user);
        model.addAttribute("task", new Task());
        model.addAttribute("project", project);
        return "addTask";
    }


    @PostMapping("/addTask/{projectId}")
    public String processCreateTask(HttpSession session, @ModelAttribute Task task, @PathVariable("projectId") int projectId) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        taskService.createTask(task, projectId);
        return "redirect:/showProject/" + projectId;
    }

}
