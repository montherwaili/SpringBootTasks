package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.Task;
import com.cl.demo.entities.TaskStatus;
import com.cl.demo.requestobjects.TaskCreateRequest;
import com.cl.demo.requestobjects.TaskUpdateRequest;
import com.cl.demo.utils.HelperUtils;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaskService {
    public static final String TASK_SAVED = "Task saved";


    public Map<String, String> addTask(TaskCreateRequest req) {
        Map<String, String> response = new HashMap<>();


        if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
            response.put("error", "Title cannot be empty");
            return response;
        }

        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setIsActive(Boolean.TRUE);
        task.setCreatedDate(new Date());
        task.setTaskNumber(generateTaskNumber());


        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setDueDate(req.getDueDate());
        task.setStartDate(req.getStartDate());
        task.setTaskStatus(TaskStatus.valueOf(req.getTaskStatus()));
        task.setIsAssigned(req.getIsAssigned());


        if (DemoApplication.Task_List.add(task)) {
            response.put("response", TASK_SAVED);
        }
        return response;
    }


    public Task getTaskById(UUID uuid) {
        if (uuid == null) return null;
        for (Task t : DemoApplication.Task_List) {

            if (t.getId().equals(uuid) && Boolean.TRUE.equals(t.getIsActive())) {
                return t;
            }
        }
        return null;
    }


    public List<Task> getAllTasks() {
        List<Task> activeTasks = new ArrayList<>();
        for (Task t : DemoApplication.Task_List) {

            if (Boolean.TRUE.equals(t.getIsActive())) {
                activeTasks.add(t);
            }
        }
        return activeTasks;
    }


    public Task updateTask(TaskUpdateRequest req) {
        Task task = getTaskById(req.getUuid());
        if (task == null) return null;


        task.setTitle(HelperUtils.compare(task.getTitle(), req.getTitleToUpdate()));
        task.setDescription(HelperUtils.compare(task.getDescription(), req.getDescriptionToUpdate()));
        task.setTaskStatus((TaskStatus) HelperUtils.compare(task.getTaskStatus(), req.getTaskStatusToUpdate()));
        task.setDueDate(HelperUtils.compare(task.getDueDate(), req.getDueDateToUpdate()));
        task.setIsAssigned(HelperUtils.compare(task.getIsAssigned(), req.getIsAssignedToUpdate()));


        task.setUpdatedDate(new Date());
        return task;
    }


    public Boolean deleteById(UUID uuid) {
        Task task = getTaskById(uuid);
        if (task != null) {
            task.setIsActive(Boolean.FALSE);
            return true;
        }
        return false;
    }


    public String generateTaskNumber() {
        return "TASK-" + (int)(Math.random() * 10000);
    }
}
