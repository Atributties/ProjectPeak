package com.example.projectpeak1.controller;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@Controller
@RequestMapping(path = "")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private int getUserId(HttpSession session) {
        return (int) session.getAttribute("userId");
    }
    private boolean isLoggedIn(HttpSession session){
        return session.getAttribute("userId") != null;
    }

    @GetMapping("/editUser/{id}")
    public String editUser(HttpSession session, @PathVariable("id") int userIdPath, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        else {
            User user = userService.getUserFromId(userIdPath);
            model.addAttribute("user", user);
            return "user_HTML/editUser";
        }
    }

    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/frontendWithProjects";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(HttpSession session, @PathVariable("id") int userIdPath) throws LoginException {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        else {
            session.invalidate();
            userService.deleteUser(userIdPath);
            return "redirect:/";
        }
    }




}
