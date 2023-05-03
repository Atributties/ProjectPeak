package com.example.projectpeak1.controller;

import com.example.projectpeak1.entities.Project;
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
        model.addAttribute("userId", getUserId(request));
        if (getUserId(request) != 0) {
            return "userFrontend";
        } else {
            return "index";
        }
    }

    @GetMapping("/createProject")
    public String createProject(Model model) {
        model.addAttribute("project", new Project());
        return "createProject";
    }

    @PostMapping(value = {"/createProject"})
    public String processCreateProject(HttpServletRequest request, @ModelAttribute Project project) {
        int userId = getUserId(request);

        repository.createProject(project, userId);
        return "userFrontend";
    }
}

