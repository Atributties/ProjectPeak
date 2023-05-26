package com.example.projectpeak1.controller;

import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.security.auth.login.LoginException;

@Controller
@RequestMapping(path = "")
public class LoginController {


    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/frontendWithProjects";
        } else {
            model.addAttribute("user", new User());
            return "login_HTML/login";
        }
    }




    @PostMapping(path = "/login")
    public String loginUser(HttpSession session, @ModelAttribute("user") User user, Model model) {
        try {
            User user1 = loginService.login(user.getEmail(), user.getPassword());
            if (user1 != null) {
                session.setAttribute("userId", user1.getUserId());
                return "redirect:/frontendWithProjects";
            } else {
                throw new LoginException("Invalid email or password");
            }
        } catch (LoginException e) {
            model.addAttribute("errorLogin", e.getMessage());
            return "login_HTML/login";
        }
    }



    @GetMapping("/signup")
    public String showSignUp(Model model) {
        model.addAttribute("user", new User());
        return "login_HTML/signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user, Model model) {
        try {
            if (loginService.doesUserExist(user.getEmail())) {
                model.addAttribute("errorSignUp", "An account with this email already exists.");
                return "login_HTML/signup";
            } else {
                loginService.createUser(user);
                return "redirect:/login";
            }
        } catch (LoginException e) {
            model.addAttribute("errorSignUp", e.getMessage());
            return "login_HTML/signup";
        }
    }



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
