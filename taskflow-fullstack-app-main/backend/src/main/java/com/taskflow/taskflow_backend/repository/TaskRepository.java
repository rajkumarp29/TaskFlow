package com.taskflow.taskflow_backend.repository;

import com.taskflow.taskflow_backend.entity.Task;
import com.taskflow.taskflow_backend.entity.TaskPriority;
import com.taskflow.taskflow_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Tasks created by user
    List<Task> findByUser(User user);

    // Tasks created by user OR assigned to user
    List<Task> findByUserOrAssignedTo(User user, User assignedTo);

    List<Task> findByPriority(TaskPriority priority);

    Optional<Task> findByIdAndUser(Long id, User user);

    List<Task> findByTeam_Id(Long teamId);

    List<Task> findByTeam_IdIn(List<Long> teamIds);

    @Query("""
        SELECT t FROM Task t
        WHERE t.team.id IN :teamIds
        AND t.priority = :priority
    """)
    List<Task> findByTeam_IdInAndPriority(
            @Param("teamIds") List<Long> teamIds,
            @Param("priority") TaskPriority priority);

    // MEMBER / VIEWER
    List<Task> findByUser_IdOrAssignedTo_Id(Long userId, Long assignedToId);

    @Query("""
        SELECT t FROM Task t
        WHERE (t.user.id = :userId OR t.assignedTo.id = :assignedToId)
        AND t.priority = :priority
    """)
    List<Task> findByUser_IdOrAssignedTo_IdAndPriority(
            @Param("userId") Long userId,
            @Param("assignedToId") Long assignedToId,
            @Param("priority") TaskPriority priority);

    @Query("""
        SELECT t FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.priority = :priority
    """)
    List<Task> findTasksByUserOrAssignedAndPriority(
            @Param("user") User user,
            @Param("priority") TaskPriority priority);

    // ================= SUMMARY =================

    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE t.user = :user OR t.assignedTo = :user
    """)
    int countTotalTasks(User user);

    // Status counts
    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.status = 'TODO'
    """)
    int countTodo(User user);

    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.status = 'IN_PROGRESS'
    """)
    int countInProgress(User user);

    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.status = 'DONE'
    """)
    int countDone(User user);

    // Priority counts
    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.priority = 'HIGH'
    """)
    int countHigh(User user);

    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.priority = 'MEDIUM'
    """)
    int countMedium(User user);

    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.priority = 'LOW'
    """)
    int countLow(User user);

    // Overdue
    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE (t.user = :user OR t.assignedTo = :user)
        AND t.dueDate < CURRENT_DATE
        AND t.status != 'DONE'
    """)
    int countOverdue(User user);

    // ✅ FIXED for MySQL
    @Query(value = """
        SELECT COUNT(*)
        FROM tasks t
        WHERE (t.user_id = :#{#user.id} OR t.assigned_to = :#{#user.id})
        AND t.created_at >= CURRENT_DATE - INTERVAL 7 DAY
    """, nativeQuery = true)
    int countTasksThisWeek(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.assignedTo = null WHERE t.assignedTo.id = :userId")
    void clearAssignedTo(@Param("userId") Long userId);

    // ================= GLOBAL =================

    @Query("SELECT COUNT(t) FROM Task t")
    int countAllTasks();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'TODO'")
    int countAllTodo();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'IN_PROGRESS'")
    int countAllInProgress();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'DONE'")
    int countAllDone();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.priority = 'HIGH'")
    int countAllHigh();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.priority = 'MEDIUM'")
    int countAllMedium();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.priority = 'LOW'")
    int countAllLow();

    @Query("""
        SELECT COUNT(t)
        FROM Task t
        WHERE t.dueDate < CURRENT_DATE
        AND t.status != 'DONE'
    """)
    int countAllOverdue();

    // ✅ FIXED for MySQL
    @Query(value = """
        SELECT COUNT(*)
        FROM tasks t
        WHERE t.created_at >= CURRENT_DATE - INTERVAL 7 DAY
    """, nativeQuery = true)
    int countAllTasksThisWeek();
}