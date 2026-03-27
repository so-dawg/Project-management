package logic;

import java.util.ArrayList;

public class ProjectManager {
  private final ArrayList<Project> projects = new ArrayList<>();

  public Project createProject(String title, String description, IUser owner) {
    Project project = new Project(title, description, owner);
    projects.add(project);
    return project;
  }

  public boolean removeProject(int projectId) {
    Project project = getProjectById(projectId);
    if (project != null) {
      projects.remove(project);
      return true;
    }
    return false;
  }

  public boolean removeProject(Project project) {
    return projects.remove(project);
  }

  public ArrayList<Project> getProjectsByOwner(IUser owner) {
    ArrayList<Project> ownedProjects = new ArrayList<>();
    for (Project project : projects) {
      if (project.getOwner().getId().equals(owner.getId())) {
        ownedProjects.add(project);
      }
    }
    return ownedProjects;
  }

  public ArrayList<Project> getProjectsByMember(Member member) {
    ArrayList<Project> memberProjects = new ArrayList<>();
    for (Project project : projects) {
      if (project.searchMemberById(member.getId()) != null) {
        memberProjects.add(project);
      }
    }
    return memberProjects;
  }

  public ArrayList<Project> getAllUserProjects(IUser user) {
    ArrayList<Project> userProjects = new ArrayList<>();
    for (Project project : projects) {
      if (project.getOwner().getId().equals(user.getId()) ||
          project.searchMemberById(user.getId()) != null) {
        userProjects.add(project);
      }
    }
    return userProjects;
  }

  public Project getProjectById(int projectId) {
    for (Project project : projects) {
      if (project.getProjectID() == projectId) {
        return project;
      }
    }
    return null;
  }

  public ArrayList<Project> getAllProjects() {
    return projects;
  }

  public int getProjectCount() {
    return projects.size();
  }
}
