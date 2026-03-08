package com.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taskflow.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(String status);

    @Query("SELECT COUNT(t) FROM Task t WHERE DATE(t.dueDate) = CURRENT_DATE")
    long countDueToday();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.dueDate < CURRENT_DATE AND t.status <> 'DONE'")
    long countOverdueTasks();

}