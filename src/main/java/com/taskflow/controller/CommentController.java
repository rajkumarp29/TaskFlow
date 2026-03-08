package com.taskflow.controller;

import com.taskflow.entity.TaskComment;
import com.taskflow.service.CommentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // ===============================
    // GET COMMENTS FOR A TASK
    // ===============================
    @GetMapping("/tasks/{taskId}/comments")
    public List<TaskComment> getComments(@PathVariable Long taskId) {
        return commentService.getCommentsByTask(taskId);
    }

    // ===============================
    // ADD COMMENT
    // ===============================
    @PostMapping("/tasks/{taskId}/comments")
    public TaskComment addComment(
            @PathVariable Long taskId,
            @RequestBody TaskComment comment) {

        return commentService.saveComment(taskId, comment);
    }

    // ===============================
    // DELETE COMMENT
    // ===============================
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

}