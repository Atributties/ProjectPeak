package com.example.projectpeak1.controller;


import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.services.SubtaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.List;

@Controller
@RequestMapping({""})
public class SubtaskController {

    SubtaskService subtaskService;
    public SubtaskController(SubtaskService subtaskService){
        this.subtaskService = subtaskService;
    }

    public boolean isUserAuthorized(HttpSession session, int id) {
        int userId = getUserId(session);
        return subtaskService.isUserAuthorized(userId, id);
    }



    //Get the userID by the Httpsession, so we can control if the user session is logged out.
    private int getUserId(HttpSession session) {
        return (int) session.getAttribute("userId");
    }

    @GetMapping("/createSubtask/{id}")
    public String createTask(@PathVariable("id") int taskId, HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {               // Check that the session with userid is logged in.
            return "login_HTML/login";
        }
        TaskAndSubtaskDTO taskAndSubtask = subtaskService.getTaskAndSubTaskById(taskId);
        model.addAttribute("task", taskAndSubtask);

        if(!isUserAuthorized(session, taskAndSubtask.getProjectId())){
            return "error_HTML/accessDenied";
        }

        User user = subtaskService.getUserFromId(userId);
        model.addAttribute("user", user);
        model.addAttribute("taskId", taskId);
        model.addAttribute("subtask", new Subtask());
        return "subtask_HTML/addSubtask";
    }

    @PostMapping(value = {"/createSubtask/{id}"})
    public String createTask(@PathVariable("id") int taskId, @ModelAttribute Subtask subtask) {

        subtaskService.createSubtask(subtask, taskId);
        return "redirect:/showSubtask/" + taskId;

    }

    @GetMapping("/showSubtask/{taskId}")
    public String showSubtaskDetails(HttpSession session, @PathVariable("taskId") int taskId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        User user = subtaskService.getUserFromId(userId);
        model.addAttribute("user", user);
        TaskAndSubtaskDTO taskAndSubtask = subtaskService.getTaskAndSubTaskById(taskId);
        model.addAttribute("taskAndSubtask", taskAndSubtask);

        if(!isUserAuthorized(session, taskAndSubtask.getProjectId())){
            return "error_HTML/accessDenied";
        }
        return "subtask_HTML/showSubtask";
    }

    @GetMapping("/editSubtask/{id}")
    public String editSubtask(HttpSession session, @PathVariable("id") int subtaskId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        Subtask subtask1 = subtaskService.getSubtaskById(subtaskId); //get the subtask, so we can show in html edit site.
        model.addAttribute("subtask", subtask1);
        TaskAndSubtaskDTO taskAndSubtask = subtaskService.getTaskAndSubTaskById(subtask1.getTaskId());
        model.addAttribute("task", taskAndSubtask);
        model.addAttribute("taskId", subtask1.getTaskId());
        if(!isUserAuthorized(session, taskAndSubtask.getProjectId())){
            return "error_HTML/accessDenied";
        }
        User user = subtaskService.getUserFromId(userId);
        model.addAttribute("user", user);

        return "subtask_HTML/editSubtask";
    }

    @PostMapping("/editSubtask")
    public String updateSubtask(@ModelAttribute Subtask subtask, @RequestParam("taskId") int taskId) {
        subtaskService.editSubtask(subtask);
        return "redirect:/showSubtask/" + taskId;
    }

    @GetMapping(value = "/deleteSubtask/{id}")
    public String deleteTask(HttpSession session, @PathVariable("id") int subtaskId) throws LoginException {
        int taskId = subtaskService.getTaskIdBySubtaskId(subtaskId);
        if(!isUserAuthorized(session, subtaskService.getProjectIdBtSubtaskId(subtaskId))){
            return "error_HTML/accessDenied";
        }
        subtaskService.deleteSubtask(subtaskId);
        return "redirect:/showSubtask/" + taskId;
    }

    @GetMapping(value = {"/doneSubtask/{id}"})
    public String doneSubtask(HttpSession session, @PathVariable("id") int id) throws LoginException {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }
        if(!isUserAuthorized(session, subtaskService.getProjectIdBtSubtaskId(id))){
            return "error_HTML/accessDenied";
        }
        int taskId = subtaskService.getTaskIdBySubtaskId(id);
        subtaskService.doneSubtask(id);


        return "redirect:/showSubtask/" + taskId;

    }

    @GetMapping(value = {"/showAllDoneSubtask/{taskId}"})
    public String seeAllDoneSubtask(HttpSession session, Model model, @PathVariable int taskId) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login_HTML/login";
        }


        User user = subtaskService.getUserFromId(userId);
        model.addAttribute("user", user);

        List<DoneSubtaskDTO> doneSubtaskDTOS = subtaskService.getAllDoneSubtask(taskId);
        model.addAttribute("seeDoneSubtask", doneSubtaskDTOS);
        model.addAttribute("taskId", taskId);

        return "subtask_HTML/showAllDoneSubtask";

    }










}
