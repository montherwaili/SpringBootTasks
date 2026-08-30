package com.cl.demo.responseobjects;

import com.cl.demo.entities.Task;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskUpdateResponse {
    private String taskId;
    private String title;
    private String description;
    private String taskNumber;
    private String taskStatus;
    private String dueDate;
    private Boolean isAssigned;

    public static TaskUpdateResponse convert(Task task) {
        if (task == null || task.getId() == null) {
            return null;
        }

        TaskUpdateResponse res = new TaskUpdateResponse();
        res.setTaskId(task.getId().toString());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setTaskNumber(task.getTaskNumber());
        res.setTaskStatus(String.valueOf(task.getTaskStatus()));
        res.setDueDate(task.getDueDate() != null ? task.getDueDate().toString() : null);
        res.setIsAssigned(task.getIsAssigned());
        return res;
    }

    public static List<TaskUpdateResponse> convert(List<Task> tasks) {
        List<TaskUpdateResponse> list = new ArrayList<>();
        if (tasks == null) return list;

        for (Task t : tasks) {
            TaskUpdateResponse res = convert(t);
            if (res != null) {
                list.add(res);
            }
        }
        return list;
    }
}
