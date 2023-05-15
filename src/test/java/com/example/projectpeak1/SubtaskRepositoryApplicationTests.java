package com.example.projectpeak1;

import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.repositories.DbRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubtaskRepositoryApplicationTests {


    @Autowired
    private DbRepository dbRepository;

    @Test
    public void testCreateSubtask() throws LoginException {
        Subtask subtask = new Subtask(8,1, "Test Subtask create", "Test description", LocalDate.now(), LocalDate.now().plusDays(1), "Open", 1);
        dbRepository.createSubtask(subtask, 1);
        Subtask createdSubtask = dbRepository.getSubtaskById(subtask.getSubTaskId());
        assertEquals(subtask.getSubTaskName(), createdSubtask.getSubTaskName());
        assertEquals(subtask.getSubTaskDescription(), createdSubtask.getSubTaskDescription());
        assertEquals(subtask.getSubTaskStartDate(), createdSubtask.getSubTaskStartDate());
        assertEquals(subtask.getSubTaskEndDate(), createdSubtask.getSubTaskEndDate());
        assertEquals(subtask.getStatus(), createdSubtask.getStatus());
        assertEquals(subtask.getTaskId(), createdSubtask.getTaskId());

        dbRepository.deleteSubtask(subtask.getSubTaskId());
    }

    @Test
    public void testGetSubtaskById() throws LoginException {
        Subtask subtask = new Subtask(9,1, "Test Subtask", "Test description", LocalDate.now(), LocalDate.now().plusDays(1), "Open", 1);
        dbRepository.createSubtask(subtask, 1);
        Subtask createdSubtask = dbRepository.getSubtaskById(subtask.getSubTaskId());
        assertNotNull(createdSubtask);
        assertEquals(subtask.getSubTaskId(), createdSubtask.getSubTaskId());
        assertEquals(subtask.getSubTaskName(), createdSubtask.getSubTaskName());
        assertEquals(subtask.getSubTaskDescription(), createdSubtask.getSubTaskDescription());
        assertEquals(subtask.getSubTaskStartDate(), createdSubtask.getSubTaskStartDate());
        assertEquals(subtask.getSubTaskEndDate(), createdSubtask.getSubTaskEndDate());
        assertEquals(subtask.getStatus(), createdSubtask.getStatus());
        assertEquals(subtask.getTaskId(), createdSubtask.getTaskId());
        dbRepository.deleteSubtask(subtask.getSubTaskId());
    }

    /*
    @Test
    public void testEditSubtask() {
        Subtask subtask = new Subtask(1, "Test Subtask", "Test description", LocalDate.now(), LocalDate.now().plusDays(1), "Open", 1);
        dbRepository.createSubtask(subtask, 1);
        subtask.setStatus("In Progress");
        dbRepository.editSubtask(subtask);
        Subtask updatedSubtask = dbRepository.getSubtaskById(subtask.getSubTaskId());
        assertEquals(subtask.getStatus(), updatedSubtask.getStatus());
    }

     */

    @Test
    public void testGetProjectIdBySubtaskId() {
        int projectId = dbRepository.getProjectIdBySubtaskId(1);
        assertEquals(1, projectId);
    }

    /*
    @Test
    public void testDeleteSubtask() throws LoginException {
        Subtask subtask = new Subtask(8, "Test Subtask", "Test description", LocalDate.now(), LocalDate.now().plusDays(1), "Open", 1);
        dbRepository.createSubtask(subtask, 1);
        dbRepository.deleteSubtask(subtask.getSubTaskId());
        Subtask deletedSubtask = dbRepository.getSubtaskById(subtask.getSubTaskId());
        assertNull(deletedSubtask);
    }

     */


}
