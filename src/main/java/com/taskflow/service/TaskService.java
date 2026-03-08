package com.taskflow.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taskflow.dto.TaskRequest;
import com.taskflow.dto.TaskSummary;
import com.taskflow.entity.Task;
import com.taskflow.entity.User;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // ===============================
    // CREATE TASK
    // ===============================
    public Task createTask(TaskRequest request) {

        User assignedUser = null;

        if (request.getAssignedTo() != null) {
            assignedUser = userRepository.findById(request.getAssignedTo())
                    .orElse(null);
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : "TO_DO")
                .dueDate(request.getDueDate())
                .priority(request.getPriority() != null ? request.getPriority() : "MEDIUM")
                .assignedTo(assignedUser)
                .createdAt(LocalDateTime.now())
                .build();

        return taskRepository.save(task);
    }

    // ===============================
    // GET ALL TASKS
    // ===============================
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ===============================
    // UPDATE TASK
    // ===============================
    public Task updateTask(Long id, TaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());

        if (request.getAssignedTo() != null) {

            User user = userRepository.findById(request.getAssignedTo())
                    .orElse(null);

            task.setAssignedTo(user);

        } else {

            task.setAssignedTo(null);

        }

        return taskRepository.save(task);
    }

    // ===============================
    // DELETE TASK
    // ===============================
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public TaskSummary getTaskSummary() {

        long total = taskRepository.count();

        long todo = taskRepository.countByStatus("TO_DO");

        long progress = taskRepository.countByStatus("IN_PROGRESS");

        long done = taskRepository.countByStatus("DONE");

        long overdue = taskRepository.countOverdueTasks();

        long today = taskRepository.countDueToday();

        double completionRate = total == 0 ? 0 : ((double) done / total) * 100;

        return new TaskSummary(
                total,
                todo,
                progress,
                done,
                overdue,
                today,
                completionRate
        );
    }

}