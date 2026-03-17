@FunctionalInterface
interface TaskFilter {
  boolean isDone(Task task);
}

TaskFilter a = (task) -> {return task.isCompleted() };
