package com.example.projectpeak1.controller;


import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
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


    //Get the userID by the Httpsession, so we can control if the user session is logged out.
    private int getUserId(HttpSession session) {
        return (int) session.getAttribute("userId");
    }

    @GetMapping("/createSubtask/{id}")
    public String createTask(@PathVariable("id") int taskId, HttpSession session, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {               // Check that the session with userid is logged in.
            return "login";
        }

        User user = subtaskService.getUserFromId(userId);
        model.addAttribute("user", user);
        model.addAttribute("taskId", taskId);
        model.addAttribute("subtask", new Subtask());
        return "addSubtask";
    }

    @PostMapping(value = {"/createSubtask/{id}"})
    public String createTask(@PathVariable("id") int taskId, @ModelAttribute Subtask subtask) {

        subtaskService.createTask(subtask, taskId);
        return "redirect:/showSubtask/" + taskId;

        //TODO: Redirect to projectId instead of taskId
    }

    @GetMapping("/showSubtask/{taskId}")
    public String showSubtaskDetails(HttpSession session, @PathVariable("taskId") int taskId, Model model) {
        int userId = getUserId(session);
        if (userId == 0) {
            return "login";
        }
        User user = subtaskService.getUserFromId(userId);
        model.addAttribute("user", user);
        TaskAndSubtaskDTO taskAndSubtask = subtaskService.getTaskAndSubTaskById(taskId);
        model.addAttribute("taskAndSubtask", taskAndSubtask);
        return "showSubtask";
    }

    @GetMapping("/editSubtask/{id}")
    public String editSubtask(@PathVariable("id") int subtaskId, Model model) {
        Subtask subtask1 = subtaskService.getSubtaskById(subtaskId); //get the subtask, so we can show in html edit site.

        model.addAttribute("subtask", subtask1);
        model.addAttribute("taskId", subtask1.getTaskId());
        return "editTask";
    }

    @PostMapping("/editSubtask/{id}")
    public String updateTask(@ModelAttribute Subtask subtask) {
        subtaskService.editSubtask(subtask);
        return "redirect:/showProject/" + subtaskService.getProjectIdBtSubtaskId(subtask.getSubTaskId());
    }

    @GetMapping(value = "/deleteSubtask/{id}")
    public String deleteTask(HttpSession session, @PathVariable("id") int subtaskId) throws LoginException {
        int taskId = subtaskService.getTaskIdBySubtaskId(subtaskId);
        subtaskService.deleteSubtask(subtaskId);
        return "redirect:/showSubtask/" + taskId;
    }










}
