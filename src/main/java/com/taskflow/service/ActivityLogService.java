package com.taskflow.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.taskflow.entity.ActivityLog;
import com.taskflow.repository.ActivityLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    /**
     * Generic activity logger
     * Used by TaskService, CommentService etc.
     */
    public void log(String actionType, Long actorId, Long taskId, String message) {

        ActivityLog activity = ActivityLog.builder()
                .actionType(actionType)
                .actorId(actorId)
                .taskId(taskId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        activityLogRepository.save(activity);
    }

    /**
     * Log when a task is created
     */
    public void logTaskCreated(Long actorId, Long taskId, String taskTitle) {

        log(
                "CREATE",
                actorId,
                taskId,
                "Task created: " + taskTitle
        );
    }

    /**
     * Log when a task status changes
     */
    public void logStatusChanged(Long actorId, Long taskId, String taskTitle, String newStatus) {

        log(
                "STATUS",
                actorId,
                taskId,
                "Status changed for \"" + taskTitle + "\" to " + newStatus
        );
    }

    /**
     * Log when priority changes
     */
    public void logPriorityChanged(Long actorId, Long taskId, String taskTitle, String newPriority) {

        log(
                "PRIORITY",
                actorId,
                taskId,
                "Priority changed for \"" + taskTitle + "\" to " + newPriority
        );
    }

    /**
     * Log when task is assigned
     */
    public void logAssigned(Long actorId, Long taskId, String taskTitle, String assignedUserName) {

        log(
                "ASSIGN",
                actorId,
                taskId,
                "Task \"" + taskTitle + "\" assigned to " + assignedUserName
        );
    }

    /**
     * Log when task deleted
     */
    public void logDeleted(Long actorId, Long taskTitleId, String taskTitle) {

        log(
                "DELETE",
                actorId,
                taskTitleId,
                "Task deleted: " + taskTitle
        );
    }

}