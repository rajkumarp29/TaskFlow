package com.taskflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSummary {

    private long totalTasks;

    private long todoTasks;

    private long inProgressTasks;

    private long doneTasks;

    private long overdueTasks;

    private long dueToday;

    private double completionRate;

}