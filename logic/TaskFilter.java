package logic;

@FunctionalInterface
public interface TaskFilter {
  boolean matches(Task task);
}
