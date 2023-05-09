package com.example.projectpeak1.controller;

import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.SubTask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.List;

@Controller
@RequestMapping({""})
public class UserController {

    IRepository repository;

    public UserController(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }

    private int getUserId(HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute("userId");
        return userId;
    }

    @GetMapping(value = {"/userFrontend"})
    public String index(HttpServletRequest request, Model model) {
        int userId = getUserId(request);
        if (userId == 0) {
            return "login";
        }
        User user = repository.getUserFromId(userId);
        model.addAttribute("user", user);

        List<Project> list = repository.getAllProjectById(userId);
        model.addAttribute("projects", list);
        return "userFrontend";
    }

    @GetMapping("/createProject")
    public String createProject(HttpServletRequest request, Model model) {
        int userId = getUserId(request);
        if (userId == 0) {
            return "login";
        }
        User user = repository.getUserFromId(userId);
        model.addAttribute("user", user);

        model.addAttribute("project", new Project());
        return "createProject";
    }


    @PostMapping(value = {"/createProject"})
    public String processCreateProject(HttpServletRequest request, @ModelAttribute Project project) {
        int userId = getUserId(request);

        repository.createProject(project, userId);
        return "redirect:/userFrontend";
    }

    @GetMapping(value = {"/deleteProject/{id}"})
    public String deleteProject(HttpServletRequest request, @PathVariable("id") int id) throws LoginException {
        int userId = getUserId(request);
        if (userId == 0) {
            return "login";
        }
            repository.deleteProject(id);
            return "redirect:/userFrontend";

            //TODO add access denied like the one from wishlist
    }



    @GetMapping("/editProject/{id}")
    public String editProject(HttpServletRequest request, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(request);
        if (userId == 0) {
            return "login";
        }
        User user = repository.getUserFromId(userId);
        model.addAttribute("user", user);

        Project project = repository.getProjectById(projectId);
        model.addAttribute("project", project);

        return "editProject";
    }
    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute Project project) {
        repository.updateProject(project);
        return "redirect:/userFrontend";
    }

    @GetMapping("/showProject/{id}")
    public String showProjectDetails(HttpServletRequest request, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(request);
        if (userId == 0) {
            return "login";
        }
        User user = repository.getUserFromId(userId);
        model.addAttribute("user", user);
        List<TaskAndSubtaskDTO> task = repository.getTaskAndSubTask(projectId);
        Project project = repository.getProjectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("listOfTask", task);

        return "project";
    }

    @GetMapping("/createTask/{id}")
    public String createTask(@PathVariable("id") int id, HttpServletRequest request, Model model) {
        int userId = getUserId(request);
        if (userId == 0) {
            return "login";
        }

        model.addAttribute("projectId", id);
        model.addAttribute("Task", new Task());
        return "createTask";
    }


    @PostMapping(value = {"/createTask/{id}"})
    public String createTask(@PathVariable("id") int id, HttpServletRequest request, @ModelAttribute Task task) {

        repository.createTask(task, id);
        return "redirect:/showProject/" + id;
    }



    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable("taskId") int taskId, Model model) {
        Task task = repository.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("taskID", task.getProjectId());
        return "editTask";
    }

    @PostMapping("/editTask/{id}")
    public String updateTask(@PathVariable("id") int projectId, @ModelAttribute Task task) {
        task.setProjectId(projectId);
        repository.editTask(task);
        return "redirect:/showProject/" + projectId;
    }







}

