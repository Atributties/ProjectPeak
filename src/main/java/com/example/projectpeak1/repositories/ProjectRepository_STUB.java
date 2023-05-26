package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneProjectDTO;
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
        HashMap<Integer, Integer> projectMembers = testDataStub.getProjectMembers();

        return projectMembers.containsKey(userId) && projectMembers.containsKey(projectId);
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
        HashMap<Integer, Integer> projectMembers = testDataStub.getProjectMembers();

        List<Project> userProjects = new ArrayList<>();

        for (Project project : projects) {
            Integer projectId = project.getProjectId();
            if (project.getUserId() == userId || (projectMembers.containsKey(userId) && projectMembers.get(userId).equals(projectId))) {
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
        project.setUserId(userId);
        testDataStub.addProject(project);

    }

    @Override
    public void deleteProject(int projectId) throws LoginException {
        List<Project> projects = testDataStub.getProjects();
        for (Project project : projects) {
            if(project.getProjectId() == projectId) {
                testDataStub.getProjects().remove(project);
            }
        }

    }

    @Override
    public void updateProject(Project project) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project1 : projects) {
            if(project1.getProjectId() == project.getProjectId()) {
                testDataStub.getProjects().remove(project1);
                testDataStub.getProjects().add(project);
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
        for (Task t : tasks) {
            if (t.getTaskId() == task.getId()) {
                t.setTaskStartDate(task.getStartDate());
                t.setTaskEndDate(task.getStartDate());
                testDataStub.getTasks().remove(t);
                testDataStub.addTask(t);
            }
        }

        // Update the subtasks' start and end dates
        for (Subtask subtask : subtasks) {
            for (Subtask sub : task.getSubTaskList()) {
                if (subtask.getSubTaskId() == sub.getSubTaskId()) {
                    subtask.setSubTaskStartDate(sub.getSubTaskStartDate());
                    subtask.setSubTaskEndDate(sub.getSubTaskEndDate());
                    testDataStub.getSubtasks().remove(sub);
                    testDataStub.addSubtask(sub);
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
                    testDataStub.addDoneSubtask(subtask);
                    subtaskIterator.remove();
                    break;
                }
            }
        }
    }


    @Override
    public void doneProject(int projectId) {
        List<Project> projects = testDataStub.getProjects();
        for (Project project : projects) {
            if(project.getProjectId() == projectId) {
                testDataStub.addDoneProject(project);
                testDataStub.getProjects().remove(project);
            }
        }

    }

    @Override
    public void doneAllTask(int projectId) {
        List<Task> tasks = testDataStub.getTasks();

        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task task = taskIterator.next();
            if (task.getProjectId() == projectId) {
                testDataStub.addDoneTask(task);
                taskIterator.remove();
            }
        }
    }


    @Override
    public List<DoneProjectDTO> getDoneProjectsByUserId(int userId) {
        List<Project> projects = testDataStub.getProjects();
        List<DoneProjectDTO> doneProjectDTOList = new ArrayList<>();

        for (Project project : projects) {
            if (project.getUserId() == userId){
                DoneProjectDTO doneProjectDTO = new DoneProjectDTO(project.getProjectId(), project.getProjectName(), project.getProjectDescription(), project.getProjectStartDate(), project.getProjectEndDate(), project.getUserId());
                doneProjectDTOList.add(doneProjectDTO);
            }
        }
        return doneProjectDTOList;
    }

    @Override
    public void addMemberToProject(int projectId, int memberUserId) {
        testDataStub.projectMembers.put(memberUserId, projectId);
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
        for (Map.Entry<Integer, Integer> entry : testDataStub.getProjectMembers().entrySet()) {
            int userId = entry.getKey();
            int associatedProjectId = entry.getValue();
            if (associatedProjectId == projectId) {
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
