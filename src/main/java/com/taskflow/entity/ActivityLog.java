package com.taskflow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp; // Add this import

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionType;

    private String message;

    private Long actorId;

    private Long taskId;

    @CreationTimestamp // This will automatically set the current time on insert
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}