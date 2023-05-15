package com.example.projectpeak1;
/*
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.repositories.DbRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectRepositoryApplicationTests {
    @Autowired
    private DbRepository dbRepository;

    @Test
    public void testCreateProject() {
        Project project = new Project("Test Project", "This is a test project", LocalDate.now(), LocalDate.now().plusDays(5), 1);
        dbRepository.createProject(project, 1);
        List<Project> projects = dbRepository.getAllProjectById(1);
        boolean projectExists = false;
        for (Project p : projects) {
            if (p.getProjectName().equals(project.getProjectName()) &&
                    p.getProjectDescription().equals(project.getProjectDescription()) &&
                    p.getProjectStartDate().equals(project.getProjectStartDate()) &&
                    p.getProjectEndDate().equals(project.getProjectEndDate()) &&
                    p.getUserId() == project.getUserId()) {
                projectExists = true;
                break;
            }
        }
        assertTrue(projectExists);
    }

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
    public void testDeleteProject() throws LoginException {
        // Create a project with a known name
        Project project = new Project("Test Project", "This is a test project", LocalDate.now(), LocalDate.now().plusDays(5), 1);
        dbRepository.createProject(project, 1);

        // Retrieve the project by name
        List<Project> projects = dbRepository.getAllProjectById(1);
        Project retrievedProject = null;
        for (Project p : projects) {
            if (p.getProjectName().equals("Test Project")) {
                retrievedProject = p;
                break;
            }
        }

        // Delete the project and verify that it was deleted
        dbRepository.deleteProject(retrievedProject.getProjectId());
        List<Project> remainingProjects = dbRepository.getAllProjectById(1);
        assertFalse(remainingProjects.contains(retrievedProject));
    }


}
*/