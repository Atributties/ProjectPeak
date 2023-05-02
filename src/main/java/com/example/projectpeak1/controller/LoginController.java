package com.example.projectpeak1.controller;


import com.example.projectpeak1.entities.User;
import com.example.projectpeak1.repositories.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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


    IRepository repository;


    public LoginController(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }


    @GetMapping("/login")
        public String showLoginForm(Model model) {
            model.addAttribute("testUser", new User());
                return "login";
        }



    @PostMapping(path = "/login")
        public String loginUser(@ModelAttribute("testUser") User testUser, Model model) throws LoginException {
        User user1 = repository.login(testUser.getEmail(), testUser.getPassword());

        if(user1 != null) {
            return "userFrontend";
        } else {
            return "login";
        }

    }



}
