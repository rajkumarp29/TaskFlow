package com.taskflow.service;

import com.taskflow.entity.Task;
import com.taskflow.entity.TaskComment;
import com.taskflow.entity.User;
import com.taskflow.repository.CommentRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public CommentService(CommentRepository commentRepository,
                          TaskRepository taskRepository,
                          UserRepository userRepository,
                          ActivityLogService activityLogService) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    // ===============================
    // GET COMMENTS
    // ===============================
    public List<TaskComment> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    // ===============================
    // ADD COMMENT
    // ===============================
    public TaskComment saveComment(Long taskId, TaskComment comment) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Temporary: get first user until JWT user auth is implemented
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No users found"));

        comment.setTask(task);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDateTime.now());

        TaskComment savedComment = commentRepository.save(comment);

        // 🔹 LOG COMMENT ACTIVITY
        activityLogService.log(
                "COMMENT",
                user.getId(),
                task.getId(),
                user.getFullName() + " commented on " + task.getTitle()
        );

        return savedComment;
    }

    // ===============================
    // DELETE COMMENT
    // ===============================
    public void deleteComment(Long commentId) {

        TaskComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Task task = comment.getTask();
        User user = comment.getAuthor();

        commentRepository.deleteById(commentId);

        // 🔹 LOG COMMENT DELETE ACTIVITY
        activityLogService.log(
                "DELETE_COMMENT",
                user.getId(),
                task.getId(),
                user.getFullName() + " deleted a comment on " + task.getTitle()
        );
    }
}