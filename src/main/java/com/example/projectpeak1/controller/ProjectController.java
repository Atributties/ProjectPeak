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
import java.util.ArrayList;
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

        for (Project project : list ) {
            int daysToStart = projectService.getDaysToStartProject(project.getProjectId());
            project.setDaysToStart(daysToStart);
            int daysForProject = projectService.getDaysForProject(project.getProjectId());
            project.setDaysForProject(daysForProject);
            int daysLeft = projectService.getDaysLeftProject(project.getProjectId());
            project.setDaysLeft(daysLeft);
        }
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
        for (TaskAndSubtaskDTO taskAndSubtaskDTO : listOfTaskAndSub) {
            int daysToStartTask = projectService.getDaysToStartTask(taskAndSubtaskDTO.getId());
            taskAndSubtaskDTO.setDaysToStart(daysToStartTask);
            int daysForTask = projectService.getDaysForTask(taskAndSubtaskDTO.getId());
            taskAndSubtaskDTO.setDaysTask(daysForTask);
            int daysLeft = projectService.getDaysLeftTask(taskAndSubtaskDTO.getId());
            taskAndSubtaskDTO.setDaysLeft(daysLeft);
        }

        List<String> allEmailsOnAProject = projectService.getAllEmailsOnProject(projectId);
        model.addAttribute("allEmails", allEmailsOnAProject);

        model.addAttribute("listOfTaskAndSub", listOfTaskAndSub);

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
        return "redirect:/project_HTML/frontendWithProjects";
    }

    @GetMapping(value = {"/deleteProject/{id}"})
    public String deleteProject(HttpSession session, @PathVariable("id") int id) throws LoginException {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, id)){
            return "error_HTML/accessDenied";
        }

        projectService.deleteProject(id);
        return "redirect:/project_HTML/frontendWithProjects";

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
        Project originalProject = projectService.getProjectById(project.getProjectId());

        // Check if the start date or end date has changed
        if (!originalProject.getProjectStartDate().equals(project.getProjectStartDate())
                || !originalProject.getProjectEndDate().equals(project.getProjectEndDate())) {

            // Update the project
            projectService.updateProject(project);

            // Update task and subtask dates
            projectService.updateTaskAndSubtaskDates(project, originalProject);
        } else {
            // Only update the project without updating task and subtask dates
            projectService.updateProject(project);
        }

        return "redirect:/project_HTML/frontendWithProjects";
    }


    @GetMapping(value = {"/doneProject/{id}"})
    public String doneProject(HttpSession session, @PathVariable("id") int id, Model model) throws LoginException {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, id)){
            return "error_HTML/accessDenied";
        }

        projectService.doneProject(id);


        return "redirect:/project_HTML/frontendWithProjects";

    }

    @GetMapping(value = {"/showAllDoneProjects"})
    public String seeAllDoneProjects(HttpSession session, Model model) throws LoginException {
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
    public String showGanttChart(HttpSession session, Model model, @PathVariable int projectId) throws LoginException {
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

        List<Project> list = projectService.getAllProjectById(userId);

        for (Project project1 : list) {
            int daysToStart = projectService.getDaysToStartProject(project1.getProjectId());
            project1.setDaysToStart(daysToStart);
            int daysForProject = projectService.getDaysForProject(project1.getProjectId());
            project1.setDaysForProject(daysForProject);
            int daysLeft = projectService.getDaysLeftProject(project1.getProjectId());
            project1.setDaysLeft(daysLeft);
        }
        model.addAttribute("projects", list);

        List<TaskAndSubtaskDTO> listOfTaskAndSub = projectService.getTaskAndSubTask(projectId);
        List<List<Object>> chartData = new ArrayList<>();
        for (TaskAndSubtaskDTO taskAndSubtaskDTO : listOfTaskAndSub) {
            List<Object> row = new ArrayList<>();
            row.add(taskAndSubtaskDTO.getName());
            row.add(taskAndSubtaskDTO.getName());
            row.add(taskAndSubtaskDTO.getStartDate());
            row.add(taskAndSubtaskDTO.getEndDate());
            row.add(null);
            row.add(0);
            row.add(null);
            chartData.add(row);
        }
        model.addAttribute("taskAndSubtask", listOfTaskAndSub);


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


        // Check if the email exists
        int memberUserId = projectService.getUserIdByEmail(email);
        if (memberUserId != 0) {
            projectService.addMemberToProject(projectId, memberUserId);
        } else {
            // Handle the case where the user with the given email doesn't exist
            // Display an error message or take appropriate action
        }

        return "redirect:/showProject/" + projectId;
    }



















}
