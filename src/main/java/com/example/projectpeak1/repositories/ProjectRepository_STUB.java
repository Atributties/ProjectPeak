package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneProjectDTO;
import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.DoneTaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.*;

@Repository("ProjectRepository_STUB")
public class ProjectRepository_STUB implements IProjectRepository{

    TestData_STUB testDataStub;

    @Autowired
    public ProjectRepository_STUB(TestData_STUB testDataStub) {
        this.testDataStub = testDataStub;
    }



    @Override
    public boolean isUserAuthorized(int userId, int projectId) {
        HashMap<Integer, List<Integer>> projectMembers = testDataStub.getProjectMembers();

        if (projectMembers.containsKey(userId)) {
            List<Integer> projectIds = projectMembers.get(userId);
            return projectIds.contains(projectId);
        }

        return false;
    }


    @Override
    public User getUserFromId(int id) {
        List<User> users = testDataStub.getUsers();

        for (User user : users) {
            if(user.getUserId() == id){
                return user;
            }
        }
        return null;
    }




    @Override
    public List<Project> getAllProjectById(int userId) {
        List<Project> projects = testDataStub.getProjects();
        HashMap<Integer, List<Integer>> projectMembers = testDataStub.getProjectMembers();

        List<Project> userProjects = new ArrayList<>();

        for (Project project : projects) {
            Integer projectId = project.getProjectId();
            if (project.getUserId() == userId || (projectMembers.containsKey(userId) && projectMembers.get(userId).contains(projectId))) {
                userProjects.add(project);
            }
        }

        return userProjects;
    }




    @Override
    public List<TaskAndSubtaskDTO> getTaskAndSubTaskList(int projectId) {
        List<TaskAndSubtaskDTO> list = new ArrayList<>();

        List<Task> tasks = testDataStub.getTasks();

        List<Subtask> subtasks = testDataStub.getSubtasks();


        for(Task task : tasks ) {
            if(task.getProjectId() == projectId) {
                List<Subtask> subtaskToTask = new ArrayList<>();
                for(Subtask subtask: subtasks) {
                    if(subtask.getTaskId() == task.getTaskId()){
                        Subtask subtask1 = new Subtask(subtask.getSubTaskId(), subtask.getSubTaskName(), subtask.getSubTaskDescription(), subtask.getSubTaskStartDate(), subtask.getSubTaskEndDate(), subtask.getStatus(), subtask.getTaskId());
                        subtaskToTask.add(subtask1);
                    }
                }
                TaskAndSubtaskDTO taskAndSubtaskDTO = new TaskAndSubtaskDTO(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getTaskStartDate(), task.getTaskEndDate(), task.getStatus(), task.getProjectId(), subtaskToTask);
                list.add(taskAndSubtaskDTO);

            }

        }
        return list;
    }

    @Override
    public Project getProjectById(int id) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project : projects) {
            if(project.getProjectId() == id){
                return project;
            }
        }
        return null;
    }

    @Override
    public void createProject(Project project, int userId) {
        // Generate the next available projectId
        int nextProjectId = testDataStub.getProjects().size() + 1;

        project.setProjectId(nextProjectId);
        project.setUserId(userId);
        testDataStub.addProject(project);

        // Add the userId to the projectMembers HashMap
       testDataStub.addProjectMember(userId, nextProjectId);
    }




    @Override
    public void deleteProject(int projectId)  {
        List<Project> projects = testDataStub.getProjects();

        for (Iterator<Project> iterator = projects.iterator(); iterator.hasNext(); ) {
            Project project = iterator.next();
            if (project.getProjectId() == projectId) {
                iterator.remove();
                break;
            }
        }
    }


    @Override
    public void updateProject(Project project) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project1 : projects) {
            if (project1.getProjectId() == project.getProjectId()) {
                projects.remove(project1);
                projects.add(project);
                break; // Exit the loop after updating the project
            }
        }
    }


    @Override
    public LocalDate getStartDateProject(int projectId) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project : projects) {
            if(project.getProjectId() == projectId) {
                return project.getProjectStartDate();
            }
        }
        return null;
    }

    @Override
    public LocalDate getStartDateTask(int taskId) {
        List<Task> tasks = testDataStub.getTasks();
        for (Task task : tasks) {
            if(task.getTaskId() == taskId) {
                return task.getTaskStartDate();
            }
        }
        return null;
    }

    @Override
    public LocalDate getEndDateTask(int taskId) {
        List<Task> tasks = testDataStub.getTasks();
        for (Task task : tasks) {
            if(task.getTaskId() == taskId) {
                return task.getTaskStartDate();
            }
        }
        return null;
    }

    @Override
    public LocalDate getEndDateProject(int projectId) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project : projects) {
            if(project.getProjectId() == projectId) {
                return project.getProjectEndDate();
            }
        }
        return null;
    }

    @Override
    public void updateTaskAndSubtaskDates(TaskAndSubtaskDTO task) {
        // Retrieve the task and subtasks from the test repository
        List<Task> tasks = testDataStub.getTasks();
        List<Subtask> subtasks = testDataStub.getSubtasks();

        // Update the task's start and end dates
        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.get(i);
            if (currentTask.getTaskId() == task.getId()) {
                currentTask.setTaskStartDate(task.getStartDate());
                currentTask.setTaskEndDate(task.getEndDate());
                tasks.set(i, currentTask);
                break; // Exit the loop after updating the task
            }
        }

        // Update the subtasks' start and end dates
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask currentSubtask = subtasks.get(i);
            for (Subtask sub : task.getSubTaskList()) {
                if (currentSubtask.getSubTaskId() == sub.getSubTaskId()) {
                    currentSubtask.setSubTaskStartDate(sub.getSubTaskStartDate());
                    currentSubtask.setSubTaskEndDate(sub.getSubTaskEndDate());
                    subtasks.set(i, currentSubtask);
                    break; // Exit the loop after updating the subtask
                }
            }
        }
    }



    @Override
    public void doneAllSubtask(int projectId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        List<Task> tasks = testDataStub.getTasks();

        Iterator<Subtask> subtaskIterator = subtasks.iterator();
        while (subtaskIterator.hasNext()) {
            Subtask subtask = subtaskIterator.next();
            for (Task task : tasks) {
                if (task.getProjectId() == projectId && subtask.getTaskId() == task.getTaskId()) {
                    DoneSubtaskDTO doneSubtask = new DoneSubtaskDTO();
                    doneSubtask.setSubTaskId(subtask.getSubTaskId());
                    doneSubtask.setSubTaskName(subtask.getSubTaskName());
                    doneSubtask.setSubTaskDescription(subtask.getSubTaskDescription());
                    doneSubtask.setSubTaskStartDate(subtask.getSubTaskStartDate());
                    doneSubtask.setSubTaskEndDate(subtask.getSubTaskEndDate());
                    doneSubtask.setSubtaskCompletedDate(LocalDate.now()); // Set current date as projectCompletedDate
                    doneSubtask.setStatus(subtask.getStatus());
                    doneSubtask.setTaskId(subtask.getTaskId());

                    testDataStub.addDoneSubtask(doneSubtask);
                    subtaskIterator.remove();
                    break;
                }
            }
        }
    }



    public void doneProject(int projectId) {
        Project projectToMarkAsDone = null;
        for (Project project : testDataStub.getProjects()) {
            if (project.getProjectId() == projectId) {
                projectToMarkAsDone = project;
                break;
            }
        }
        if (projectToMarkAsDone != null) {
            DoneProjectDTO doneProjectDTO = new DoneProjectDTO();
            doneProjectDTO.setProjectId(projectToMarkAsDone.getProjectId());
            doneProjectDTO.setProjectName(projectToMarkAsDone.getProjectName());
            doneProjectDTO.setProjectDescription(projectToMarkAsDone.getProjectDescription());
            doneProjectDTO.setProjectStartDate(projectToMarkAsDone.getProjectStartDate());
            doneProjectDTO.setProjectEndDate(projectToMarkAsDone.getProjectEndDate());
            doneProjectDTO.setProjectCompletedDate(LocalDate.now()); // Set current date as projectCompletedDate
            doneProjectDTO.setUserId(projectToMarkAsDone.getUserId());


            testDataStub.addDoneProject(doneProjectDTO);
            testDataStub.getProjects().remove(projectToMarkAsDone);
        }
    }





    @Override
    public void doneAllTask(int projectId) {
        List<Task> tasks = testDataStub.getTasks();

        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task task = taskIterator.next();
            if (task.getProjectId() == projectId) {
                DoneTaskDTO doneTask = new DoneTaskDTO();
                doneTask.setTaskId(task.getTaskId());
                doneTask.setTaskName(task.getTaskName());
                doneTask.setTaskDescription(task.getTaskDescription());
                doneTask.setTaskStartDate(task.getTaskStartDate());
                doneTask.setTaskEndDate(task.getTaskEndDate());
                doneTask.setTaskCompletedDate(LocalDate.now()); // Set current date as projectCompletedDate
                doneTask.setStatus(task.getStatus());
                doneTask.setProjectId(task.getProjectId());

                testDataStub.addDoneTask(doneTask);
                taskIterator.remove();
            }
        }
    }



    @Override
    public List<DoneProjectDTO> getDoneProjectsByUserId(int userId) {
        List<DoneProjectDTO> userDoneProjects = new ArrayList<>();
        List<DoneProjectDTO> doneProjectDTOS = testDataStub.getDoneProjects();

        for (DoneProjectDTO doneProject : doneProjectDTOS ) {
            if (doneProject.getUserId() == userId) {
                userDoneProjects.add(doneProject);
            }
        }

        return userDoneProjects;
    }



    @Override
    public void addMemberToProject(int projectId, int memberUserId) {
        HashMap<Integer, List<Integer>> projectMembers = testDataStub.getProjectMembers();

        List<Integer> projectIds = projectMembers.getOrDefault(memberUserId, new ArrayList<>());
        projectIds.add(projectId);
        projectMembers.put(memberUserId, projectIds);
    }


    @Override
    public int getUserIdByEmail(String email) {
        List<User> users = testDataStub.getUsers();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user.getUserId();
            }
        }
        return 0; // User not found
    }

    @Override
    public List<String> getAllEmailsOnProject(int projectId) {
        List<String> emails = new ArrayList<>();
        List<User> users = testDataStub.getUsers();
        HashMap<Integer, List<Integer>> projectMembers = testDataStub.getProjectMembers();

        for (Map.Entry<Integer, List<Integer>> entry : projectMembers.entrySet()) {
            int userId = entry.getKey();
            List<Integer> associatedProjectIds = entry.getValue();

            if (associatedProjectIds.contains(projectId)) {
                for (User user : users) {
                    if (user.getUserId() == userId) {
                        emails.add(user.getEmail());
                        break;
                    }
                }
            }
        }

        return emails;
    }


}
