package com.taskflow.controller;

import com.taskflow.entity.ActivityLog;
import com.taskflow.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {

    private final ActivityLogRepository repository;

    @GetMapping
    public List<ActivityLog> getRecentActivity(){
        return repository.findTop20ByOrderByCreatedAtDesc();
    }

    // Add this method to handle POST requests
    @PostMapping
    public ActivityLog createActivity(@RequestBody ActivityLog activity) {
        return repository.save(activity);
    }
}