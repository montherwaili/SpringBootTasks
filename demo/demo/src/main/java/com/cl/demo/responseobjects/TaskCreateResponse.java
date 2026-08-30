package com.cl.demo.responseobjects;

import com.cl.demo.entities.Task;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskCreateResponse {
    private String taskId;
    private String title;
    private String description;
    private String taskNumber;
    private String taskStatus;
    private String dueDate;
    private Boolean isAssigned;


    public static TaskCreateResponse convert(Task task) {
        if (task == null || task.getId() == null) {
            return null;
        }

        TaskCreateResponse res = new TaskCreateResponse();
        res.setTaskId(task.getId().toString());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setTaskNumber(task.getTaskNumber());
        res.setTaskStatus(task.getTaskStatus());
        res.setDueDate(task.getDueDate() != null ? task.getDueDate().toString() : null);
        res.setIsAssigned(task.getIsAssigned());
        return res;
    }
    
    public static List<TaskCreateResponse> convert(List<Task> tasks) {
        List<TaskCreateResponse> list = new ArrayList<>();
        if (tasks == null) return list;

        for (Task t : tasks) {
            TaskCreateResponse res = convert(t);
            if (res != null) {
                list.add(res);
            }
        }
        return list;
    }
}
