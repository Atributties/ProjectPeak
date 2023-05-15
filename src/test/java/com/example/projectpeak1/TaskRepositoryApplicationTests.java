package com.example.projectpeak1;
/*
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.repositories.DbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskRepositoryApplicationTests {

    DbRepository dbRepository;

    @BeforeEach
    public void setUp() {
        dbRepository = new DbRepository();
        // any other initialization code
    }

    @Test
    public void testCreateTask() throws LoginException {
        // Given
        Task task = new Task(5, 3, "Test Task", "Test Description", LocalDate.now(), LocalDate.now().plusDays(7), "in progress", 1);
        int projectId = 1;

        // When
        dbRepository.createTask(task, projectId);
        Task createdTask = dbRepository.getTaskById(task.getTaskId());

        // Then
        assertNotNull(createdTask);
        assertEquals(task.getTaskName(), createdTask.getTaskName());
        assertEquals(task.getTaskDescription(), createdTask.getTaskDescription());
        assertEquals(task.getTaskStartDate(), createdTask.getTaskStartDate());
        assertEquals(task.getTaskEndDate(), createdTask.getTaskEndDate());
        assertEquals(task.getStatus(), createdTask.getStatus());
        assertEquals(task.getProjectId(), createdTask.getProjectId());

        dbRepository.deleteTask(5);

    }

    @Test
    public void testsGetAllTaskById() {
        // Given
        int projectId = 1;
        int expectedTaskCount = 2;

        // When
        List<Task> taskList = dbRepository.getAllTaskById(projectId);

        // Then
        assertEquals(expectedTaskCount, taskList.size());
    }

    @Test
    public void testGetTaskById() {
        // Given
        int taskId = 1;
        String expectedTaskName = "Task 1";

        // When
        Task task = dbRepository.getTaskById(taskId);

        // Then
        assertNotNull(task);
        assertEquals(expectedTaskName, task.getTaskName());
    }

    @Test
    public void testGetTaskAndSubTask() {
        // Given
        int projectId = 1;
        int expectedTaskCount = 2;
        int expectedSubTaskCount = 4;

        // When
        List<TaskAndSubtaskDTO> taskAndSubtaskDTOs = (List<TaskAndSubtaskDTO>) dbRepository.getTaskAndSubTask(projectId);

        // Then
        assertEquals(expectedTaskCount, taskAndSubtaskDTOs.size());
        assertEquals(expectedSubTaskCount, taskAndSubtaskDTOs.stream().flatMap(task -> task.getSubTaskList().stream()).count());
    }

    // need test for edit task method
    /*@Test
    public void testDeleteTask() throws LoginException {
        // Given
        Task task = new Task(5,3, "Test Task delete", "Test Description", LocalDate.now(), LocalDate.now().plusDays(7), "in progress", 1);
        int projectId = 1;

        dbRepository.createTask(task, projectId);

        // Get the ID of the created task
        int taskId = task.getTaskId();


        dbRepository.deleteTask(taskId);


        assertNull(dbRepository.getTaskById(taskId));
    }

}

 */


