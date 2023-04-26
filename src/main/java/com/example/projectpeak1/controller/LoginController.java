package com.example.projectpeak1.controller;


import com.example.projectpeak1.entities.TestUser;
import com.example.projectpeak1.repositories.IRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class LoginController {

    private final IRepository repository;


    public LoginController(ApplicationContext context, @Value("${projectPeak.repository.impl}") String impl) {
        repository = (IRepository) context.getBean(impl);
    }


    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("userId") != null) {
            return "redirect:/userFrontend";
        } else {
            model.addAttribute("TestUser", new TestUser());
            return "login";
        }
    }



    @PostMapping(path = "/login")
    public String loginUser(HttpServletRequest request, @ModelAttribute("user") TestUser user, Model model) {
        TestUser user1 = repository.login(user.getEmail(), user.getPassword());

        if(user1 != null) {
            return "userFrontend";
        } else {
            return "login";
        }

    }



}
