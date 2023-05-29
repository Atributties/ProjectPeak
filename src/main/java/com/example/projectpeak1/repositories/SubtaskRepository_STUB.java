package com.example.projectpeak1.repositories;

import com.example.projectpeak1.dto.DoneSubtaskDTO;
import com.example.projectpeak1.dto.TaskAndSubtaskDTO;
import com.example.projectpeak1.entities.Project;
import com.example.projectpeak1.entities.Subtask;
import com.example.projectpeak1.entities.Task;
import com.example.projectpeak1.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository("SubtaskRepository_STUB")
public class SubtaskRepository_STUB implements ISubtaskRepository{


    TestData_STUB testDataStub;

    @Autowired
    public SubtaskRepository_STUB(TestData_STUB testDataStub) {
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
    public void createSubtask(Subtask subtask, int taskId) {
        subtask.setTaskId(taskId);
        testDataStub.addSubtask(subtask);
    }


    @Override
    public Subtask getSubtaskById(int id) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        for (Subtask subtask : subtasks) {
            if(subtask.getSubTaskId() == id){
                return subtask;
            }
        }
        return null;
    }

    @Override
    public List<Subtask> getSubtasksByTaskId(int taskId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        List<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if(subtask.getTaskId() == taskId){
                allSubtasks.add(subtask);
            }
        }
        return allSubtasks;

    }

    @Override
    public void editSubtask(Subtask subtask) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask currentSubtask = subtasks.get(i);
            if (currentSubtask.getSubTaskId() == subtask.getSubTaskId()) {
                subtasks.remove(i);
                subtasks.add(i, subtask);
                break; // Exit the loop after updating the subtask
            }
        }
    }


    @Override
    public int getProjectIdBySubtaskId(int subtaskId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        List<Task> tasks = testDataStub.getTasks();

        for (Task task : tasks) {
            for(Subtask subtask : subtasks) {
                if(task.getTaskId() == subtask.getTaskId()) {
                    return task.getProjectId();
                }
            }
        }
        return 0;
    }

    @Override
    public void deleteSubtask(int subtaskId) throws LoginException {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        for (Subtask subtask : subtasks) {
            if(subtask.getSubTaskId() == subtaskId) {
                testDataStub.getSubtasks().remove(subtask);
            }
        }
    }

    @Override
    public TaskAndSubtaskDTO getTaskAndSubTask(int taskId) {
        List<Task> tasks = testDataStub.getTasks();
        List<Subtask> subtasks = testDataStub.getSubtasks();


        for(Task task : tasks ) {
            if(task.getTaskId() == taskId) {
                List<Subtask> subtaskToTask = new ArrayList<>();
                for(Subtask subtask: subtasks) {
                    if(subtask.getTaskId() == task.getTaskId()){
                        Subtask subtask1 = new Subtask(subtask.getSubTaskId(), subtask.getSubTaskName(), subtask.getSubTaskDescription(), subtask.getSubTaskStartDate(), subtask.getSubTaskEndDate(), subtask.getStatus(), subtask.getTaskId());
                        subtaskToTask.add(subtask1);
                    }
                }
                return new TaskAndSubtaskDTO(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getTaskStartDate(), task.getTaskEndDate(), task.getStatus(), task.getProjectId(), subtaskToTask);


            }
        }
        return null;
    }

    @Override
    public int getTaskIdBySubtaskId(int subtaskId) throws LoginException {
        List<Subtask> subtasks = testDataStub.getSubtasks();

        for (Subtask subtask : subtasks) {
            if(subtask.getSubTaskId() == subtaskId) {
                return subtask.getTaskId();
            }

        }
        return 0;
    }

    @Override
    public LocalDate getStartDateSubtask(int subtaskId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        for (Subtask subtask : subtasks) {
            if(subtask.getSubTaskId() == subtaskId) {
                return subtask.getSubTaskStartDate();
            }
        }
        return null;
    }

    @Override
    public LocalDate getEndDateSubtask(int subTaskId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        for (Subtask subtask : subtasks) {
            if(subtask.getSubTaskId() == subTaskId) {
                return subtask.getSubTaskEndDate();
            }
        }
        return null;
    }

    @Override
    public void doneSubtask(int taskId) {
        List<Subtask> subtasks = testDataStub.getSubtasks();
        Subtask subtaskToRemove = null;

        for (Subtask subtask : subtasks) {
            if (subtask.getTaskId() == taskId) {
                subtaskToRemove = subtask;
                break;
            }
        }

        if (subtaskToRemove != null) {
            DoneSubtaskDTO doneSubtaskDTO = new DoneSubtaskDTO();
            doneSubtaskDTO.setSubTaskId(subtaskToRemove.getSubTaskId());
            doneSubtaskDTO.setSubTaskName(subtaskToRemove.getSubTaskName());
            doneSubtaskDTO.setSubTaskDescription(subtaskToRemove.getSubTaskDescription());
            doneSubtaskDTO.setSubTaskStartDate(subtaskToRemove.getSubTaskStartDate());
            doneSubtaskDTO.setSubTaskEndDate(subtaskToRemove.getSubTaskEndDate());
            doneSubtaskDTO.setSubtaskCompletedDate(LocalDate.now()); // Set current date as projectCompletedDate
            doneSubtaskDTO.setStatus(subtaskToRemove.getStatus());
            doneSubtaskDTO.setTaskId(subtaskToRemove.getTaskId());

            testDataStub.addDoneSubtask(doneSubtaskDTO);
            testDataStub.getSubtasks().remove(subtaskToRemove);
        }
    }



    @Override
    public List<DoneSubtaskDTO> getAllDoneSubtask(int taskId) {
        List<DoneSubtaskDTO> doneSubtaskDTOS = testDataStub.getDoneSubtask();
        List<DoneSubtaskDTO> filteredSubtask = new ArrayList<>();

        for (DoneSubtaskDTO subtaskDTO : doneSubtaskDTOS) {
            if (subtaskDTO.getTaskId() == taskId) {
                filteredSubtask.add(subtaskDTO);
            }
        }

        return filteredSubtask;
    }
}
