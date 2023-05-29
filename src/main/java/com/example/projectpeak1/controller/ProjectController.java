package com.example.projectpeak1.controller;


import com.example.projectpeak1.dto.DoneProjectDTO;
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

    public boolean isUserAuthorized(HttpSession session, int id) {
        int userId = getUserId(session);
        return projectService.isUserAuthorized(userId, id);
    }


    @GetMapping(value = {"/frontendWithProjects"})
    public String index(HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        List<Project> list = projectService.getAllProjectById(userId);

        model.addAttribute("projects", list);
        return "project_HTML/frontendWithProjects";
    }

    @GetMapping("/showProject/{id}")
    public String showProjectDetails(HttpSession session, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);

        List<TaskAndSubtaskDTO> listOfTaskAndSub = projectService.getTaskAndSubTask(projectId);
        model.addAttribute("listOfTaskAndSub", listOfTaskAndSub);

        List<String> allEmailsOnAProject = projectService.getAllEmailsOnProject(projectId);
        model.addAttribute("allEmails", allEmailsOnAProject);

        return "project_HTML/project";
    }






    @GetMapping("/createProject")
    public String createProject(HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        model.addAttribute("project", new Project());
        return "project_HTML/createProject";
    }


    @PostMapping(value = {"/createProject"})
    public String processCreateProject(HttpSession session, @ModelAttribute Project project) {
        int userId = getUserId(session);
        projectService.createProject(project, userId);
        return "redirect:/frontendWithProjects";
    }

    @PostMapping(value = {"/deleteProject/{id}"})
    public String deleteProject(HttpSession session, @PathVariable("id") int id) throws LoginException {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, id)){
            return "error_HTML/accessDenied";
        }
        projectService.deleteProject(id);
        return "redirect:/frontendWithProjects";

    }



    @GetMapping("/editProject/{id}")
    public String editProject(HttpSession session, @PathVariable("id") int projectId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }


        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);

        return "project_HTML/editProject";
    }
    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute Project project) {
        projectService.updateProject(project);
        return "redirect:/frontendWithProjects";
    }


    @PostMapping(value = {"/doneProject/{id}"})
    public String doneProject(HttpSession session, @PathVariable("id") int id){
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, id)){
            return "error_HTML/accessDenied";
        }
        projectService.doneProject(id);
        return "redirect:/frontendWithProjects";

    }

    @GetMapping(value = {"/showAllDoneProjects"})
    public String seeAllDoneProjects(HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        List<DoneProjectDTO> doneProjectDTO = projectService.seeAllDoneProjects(userId);
        model.addAttribute("seeDoneProject", doneProjectDTO);

        return "project_HTML/showAllDoneProjects";

    }

    @GetMapping(value = {"/ganttChartProject/{projectId}"})
    public String showGanttChart(HttpSession session, Model model, @PathVariable int projectId){
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }

        if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }
        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        List<List<Object>> chartData = projectService.getTaskAndSubtaskDisplayGantt(projectId);

        model.addAttribute("chartData", chartData);

        return "project_HTML/ganttChartProject";
    }

    @PostMapping(value = {"/addMemberToProject/{projectId}"})
    public String addMemberToProject(HttpSession session, @PathVariable int projectId, @ModelAttribute("email") String email, Model model) throws LoginException {

        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, projectId)){
            return "error_HTML/accessDenied";
        }

        User user = projectService.getUserFromId(userId);
        model.addAttribute("user", user);

        int memberUserId = projectService.getUserIdByEmail(email);
        projectService.addMemberToProject(projectId, memberUserId);

        return "redirect:/showProject/" + projectId;
    }



















}
