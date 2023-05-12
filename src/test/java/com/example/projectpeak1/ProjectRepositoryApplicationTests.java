package com.example.projectpeak1;

import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.repositories.DbRepository;
import com.example.projectpeak1.utility.LoginException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectRepositoryApplicationTests {
    /*
    @Autowired
    private DbRepository dbRepository;

    @Test
    public void testCreateProject() {
        Project project = new Project("Test Project", "This is a test project", LocalDate.now(), LocalDate.now().plusDays(5), 1);
        dbRepository.createProject(project, 1);
        List<Project> projects = dbRepository.getAllProjectById(1);
        assertTrue(projects.contains(project));
        assertNotNull(project.getProjectId()); // check that the project id was generated
    }
    // if getallprojectsById(1) isNotNull getProject

    @Test
    public void testGetAllProjectById() {
        List<Project> projects = dbRepository.getAllProjectById(1);
        assertNotNull(projects);
    }

    @Test
    public void testGetProjectById() {
        Project project = dbRepository.getProjectById(1);
        assertNotNull(project);
    }

    @Test
    public void testUpdateProject() {
        Project project = dbRepository.getProjectById(1);
        project.setProjectName("Updated Project Name");
        dbRepository.updateProject(project);
        Project updatedProject = dbRepository.getProjectById(1);
        assertEquals("Updated Project Name", updatedProject.getProjectName());
    }

    @Test
    public void testDeleteProject() throws LoginException, javax.security.auth.login.LoginException {
        int projectId = 1;
        dbRepository.deleteProject(projectId);
        assertNull(dbRepository.getProjectById(projectId));
    }

     */
}
