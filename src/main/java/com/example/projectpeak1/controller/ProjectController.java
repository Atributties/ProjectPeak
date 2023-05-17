package com.example.projectpeak1.controller;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.services.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.LoginException;
import java.util.List;

@Controller
@RequestMapping({""})
public class ProjectController {

    ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    private int getUserId(HttpSession session) {
        return (int) session.getAttribute("userId");
    }


    @GetMapping(value = {"/userFrontend"})
    public String index(HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        List<Project> list = projectService.getAllProjectById(userId);

        for (Project project : list ) {
            int days = projectService.getDaysToStart(project.getProjectId());
            project.setDaysToStart(days);
        }


        model.addAttribute("projects", list);
        return "userFrontend";
    }

    @GetMapping("/showProject/{id}")
    public String showProjectDetails(HttpSession session, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);

        List<TaskAndSubtaskDTO> listOfTaskAndSub = projectService.getTaskAndSubTask(projectId);
        model.addAttribute("listOfTaskAndSub", listOfTaskAndSub);

        return "project";
    }




    @GetMapping("/createProject")
    public String createProject(HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        model.addAttribute("project", new Project());
        return "createProject";
    }


    @PostMapping(value = {"/createProject"})
    public String processCreateProject(HttpSession session, @ModelAttribute Project project) {
        int userId = getUserId(session);

        projectService.createProject(project, userId);
        return "redirect:/userFrontend";
    }

    @GetMapping(value = {"/deleteProject/{id}"})
    public String deleteProject(HttpSession session, @PathVariable("id") int id) throws LoginException {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        projectService.deleteProject(id);
        return "redirect:/userFrontend";

        //TODO add access denied like the one from wishlist
    }



    @GetMapping("/editProject/{id}")
    public String editProject(HttpSession session, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);

        return "editProject";
    }
    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute Project project) {
        projectService.updateProject(project);
        return "redirect:/userFrontend";
    }






}
