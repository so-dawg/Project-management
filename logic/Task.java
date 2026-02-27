package logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Task - Represents a task in the project management system
 */
public class Task {

    /**
     * TaskPriority - Enum representing the priority levels of a task
     */
    public enum TaskPriority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    private int assignTo;  // Member ID (from database)
    private LocalDate deadline;
    private String title;
    private String taskDescription;
    private TaskPriority priority;
    private boolean completed;

    /**
     * Constructor for Task
     */
    public Task(String title, TaskPriority priority, LocalDate deadline, int assignTo, String taskDescription) {
        this.assignTo = assignTo;
        this.deadline = deadline;
        setNewTitle(title);
        setNewPriority(priority);
        setNewTaskDescription(taskDescription);
        this.completed = false;
    }

    /**
     * Constructor for Task without assignee
     */
    public Task(String title, TaskPriority priority, LocalDate deadline, String taskDescription) {
        this(title, priority, deadline, 0, taskDescription);
    }

    /**
     * Set deadline for the task
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    // ==================== Getters ====================

    public int getAssignTo() {
        return this.assignTo;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public String getDeadlineString() {
        if (this.deadline == null) {
            return "Not set";
        }
        return this.deadline.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getTitle() {
        return this.title;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public TaskPriority getPriority() {
        return this.priority;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    // ==================== Setters ====================

    public void setNewPriority(TaskPriority p) {
        if (p == null) {
            System.out.println("Priority cannot be null");
            return;
        }
        this.priority = p;
    }

    public void setNewTitle(String t) {
        if (t == null) {
            System.out.println("Title cannot be null");
            return;
        }

        String sanitizedTitle = t.trim();

        if (sanitizedTitle.length() > 255) {
            System.out.println("Title exceeds maximum length of 255 characters");
            return;
        }

        if (sanitizedTitle.isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        }

        this.title = sanitizedTitle;
    }

    public void setNewTaskDescription(String description) {
        if (description == null) {
            System.out.println("Task description cannot be null");
            return;
        }

        String sanitizedDescrip = description.trim();

        if (sanitizedDescrip.length() > 10000) {
            System.out.println("Task description exceeds maximum length of 10000 characters");
            return;
        }

        this.taskDescription = sanitizedDescrip;
    }

    public void setAssignTo(int memberId) {
        this.assignTo = memberId;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markIncomplete() {
        this.completed = false;
    }

    // ==================== Utility Methods ====================

    /**
     * Check if the task is past its deadline
     */
    public boolean isPastDeadline() {
        if (this.deadline == null) {
            return false;
        }
        return this.deadline.isBefore(LocalDate.now());
    }

    /**
     * Get days remaining until deadline
     * @return positive if not past deadline, negative if past
     */
    public long getDaysUntilDeadline() {
        if (this.deadline == null) {
            return -1;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), this.deadline);
    }

    // ==================== Override ====================

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
               "Priority: " + priority + "\n" +
               "Deadline: " + getDeadlineString() + "\n" +
               "Assigned To: " + (assignTo > 0 ? String.valueOf(assignTo) : "Unassigned") + "\n" +
               "Status: " + (completed ? "Completed" : "Pending") + "\n" +
               "Description: " + taskDescription;
    }
}
