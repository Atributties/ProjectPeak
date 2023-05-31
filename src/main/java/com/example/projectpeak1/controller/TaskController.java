package com.example.projectpeak1.controller;

import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.services.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.List;


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
    private boolean isLoggedIn(HttpSession session){
        return session.getAttribute("userId") != null;
    }
    public boolean isUserAuthorized(HttpSession session, int id) {
        int userId = getUserId(session);
        return taskService.isUserAuthorized(userId, id);
    }

    @GetMapping("/addTask/{id}")
    public String createTask(HttpSession session, @PathVariable("id") int projectId, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        else if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }
        else {
            int userId = getUserId(session);
            User user = taskService.getUserFromId(userId);
            Project project = taskService.getProjectFromId(projectId);
            model.addAttribute("user", user);
            model.addAttribute("task", new Task());
            model.addAttribute("project", project);
            return "task_HTML/addTask";
        }
    }


    @PostMapping("/addTask/{projectId}")
    public String processCreateTask(@ModelAttribute Task task, @PathVariable("projectId") int projectId) {
        taskService.createTask(task, projectId);
        return "redirect:/showProject/" + projectId;
    }




    @GetMapping("/editTask/{id}")
    public String editTask(HttpSession session, @PathVariable("id") int taskId, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        if(!isUserAuthorized(session, task.getProjectId())){
            return "error_HTML/accessDenied";
        }
        else {
            int userId = getUserId(session);
            User user = taskService.getUserFromId(userId);
            model.addAttribute("user", user);
            Project project = taskService.getProjectFromId(task.getProjectId());
            model.addAttribute("project", project);
            return "task_HTML/editTask";
        }
    }

    @PostMapping("/editTask")
    public String updateTask(@ModelAttribute Task task, @RequestParam("projectId") int projectId) {
        taskService.updateTask(task);
        return "redirect:/showProject/" + projectId;
    }



    @GetMapping(value = {"/deleteTask/{id}"})
    public String deleteTask(HttpSession session, @PathVariable("id") int taskId) throws LoginException {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        int projectId = taskService.getProjectIdFromTaskId(taskId);
        if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }
        else {
            taskService.deleteTask(taskId);
            return "redirect:/showProject/" + projectId;
        }
    }

    @GetMapping(value = {"/doneTask/{id}"})
    public String doneTask(HttpSession session, @PathVariable("id") int id) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        int projectId = taskService.getProjectIdFromTaskId(id);
        if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }
        else {
            taskService.doneTask(id);
            return "redirect:/showProject/" + projectId;
        }
    }

    @GetMapping(value = {"/showAllTaskProjects/{id}"})
    public String seeAllDoneTask(HttpSession session, Model model, @PathVariable int id) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        else {
            int userId = getUserId(session);
            User user = taskService.getUserFromId(userId);
            model.addAttribute("user", user);

            List<DoneTaskDTO> doneTaskDTOS = taskService.seeAllDoneTask(id);
            model.addAttribute("seeDoneTask", doneTaskDTOS);

            return "task_HTML/showAllDoneTask";
        }
    }



}
