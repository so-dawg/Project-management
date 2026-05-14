package logic;

public class Owner extends Member {

  private Project project;

  public Owner(String firstName, String lastName, String email, String username, String password) {
    super(firstName, lastName, email, username, password);
  }

  public Owner(int id, String firstName, String lastName, String email, String username, String password) {
    super(id, firstName, lastName, email, username, password);
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public String getRole() {
    return "Owner";
  }

  @Override
  public boolean can(String action) {
    return true;
  }

  @Override
  public String toString() {
    return super.toString() + "owned" + project;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Owner && super.equals(obj);
  }
}
