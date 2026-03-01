package com.taskflow.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taskflow.dto.TaskRequest;
import com.taskflow.entity.Task;
import com.taskflow.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // ✅ CREATE
    public Task createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .dueDate(request.getDueDate())
                .createdAt(LocalDateTime.now())
                .build();

        return taskRepository.save(task);
    }

    // ✅ GET ALL
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ✅ UPDATE
    public Task updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        return taskRepository.save(task);
    }

    // ✅ DELETE
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}