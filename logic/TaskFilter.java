package logic;

@FunctionalInterface
interface TaskFilter {
  boolean isDone(Task task);
}
