package com.taskflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taskflow.dto.TaskRequest;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // CREATE TASK (placeholder service call)
    @PostMapping
    public ResponseEntity<String> createTask(
            @RequestBody TaskRequest request) {

        return ResponseEntity.ok("Task created successfully");
    }

    // GET ALL TASKS
    @GetMapping
    public ResponseEntity<String> getAllTasks() {

        return ResponseEntity.ok("List of tasks");
    }

    // UPDATE TASK
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request) {

        return ResponseEntity.ok("Task updated successfully");
    }

    // DELETE TASK
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id) {

        return ResponseEntity.ok("Task deleted successfully");
    }
}