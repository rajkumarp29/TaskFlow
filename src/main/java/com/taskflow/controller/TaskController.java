package com.taskflow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taskflow.dto.TaskRequest;
import com.taskflow.dto.TaskSummary;
import com.taskflow.entity.Task;
import com.taskflow.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;

    // ===============================
    // CREATE TASK
    // ===============================
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {

        Task createdTask = taskService.createTask(request);

        return ResponseEntity.ok(createdTask);
    }

    // ===============================
    // GET ALL TASKS
    // ===============================
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        List<Task> tasks = taskService.getAllTasks();

        return ResponseEntity.ok(tasks);
    }

    // ===============================
    // UPDATE TASK
    // ===============================
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request) {

        Task updatedTask = taskService.updateTask(id, request);

        return ResponseEntity.ok(updatedTask);
    }

    // ===============================
    // DELETE TASK
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/summary")
    public ResponseEntity<TaskSummary> getSummary() {

        TaskSummary summary = taskService.getTaskSummary();

        return ResponseEntity.ok(summary);
    }

}