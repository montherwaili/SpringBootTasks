package com.cl.demo.controllers;

import com.cl.demo.entities.Task;
import com.cl.demo.requestobjects.TaskCreateRequest;
import com.cl.demo.requestobjects.TaskUpdateRequest;
import com.cl.demo.responseobjects.TaskCreateResponse;
import com.cl.demo.responseobjects.TaskUpdateResponse;
import com.cl.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PostMapping("/add")
    public Map<String, String> addTask(@RequestBody TaskCreateRequest req) {
        return taskService.addTask(req);
    }


    @GetMapping("/getById")
    public TaskCreateResponse getTaskById(@RequestParam UUID uuid) {
        Task task = taskService.getTaskById(uuid);
        return TaskCreateResponse.convert(task);
    }


    @GetMapping("/getAll")
    public List<TaskCreateResponse> getAllTasks() {
        return TaskCreateResponse.convert(taskService.getAllTasks());
    }


    @PutMapping("/update")
    public TaskUpdateResponse updateTask(@RequestBody TaskUpdateRequest req) {
        Task task = taskService.updateTask(req);
        return TaskUpdateResponse.convert(task);
    }

    
    @DeleteMapping("/deleteById")
    public Boolean deleteById(@RequestParam UUID id) {
        return taskService.deleteById(id);
    }
}
