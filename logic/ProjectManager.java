package logic;

import java.util.ArrayList;

public class ProjectManager {
    private ArrayList<Project> projects = new ArrayList<>();

    /**
     * Create a new project (only Owner can create)
     */
    public Project createProject(String title, String description, Owner owner) {
        Project project = new Project(title, description, owner);
        projects.add(project);
        return project;
    }

    /**
     * Remove a project by ID
     */
    public boolean removeProject(int projectId) {
        Project project = getProjectById(projectId);
        if (project != null) {
            projects.remove(project);
            return true;
        }
        return false;
    }

    /**
     * Remove a project by object
     */
    public boolean removeProject(Project project) {
        return projects.remove(project);
    }

    /**
     * Get all projects owned by a specific owner
     */
    public ArrayList<Project> getProjectsByOwner(Owner owner) {
        ArrayList<Project> ownedProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getOwner().getId().equals(owner.getId())) {
                ownedProjects.add(project);
            }
        }
        return ownedProjects;
    }

    /**
     * Get all projects where a user is a member
     */
    public ArrayList<Project> getProjectsByMember(Member member) {
        ArrayList<Project> memberProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.searchMemberById(member.getId()) != null) {
                memberProjects.add(project);
            }
        }
        return memberProjects;
    }

    /**
     * Get all projects where a user is either owner or member
     */
    public ArrayList<Project> getAllUserProjects(IUser_Member user) {
        ArrayList<Project> userProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getOwner().getId().equals(user.getId())) {
                userProjects.add(project);
            }
            else if (project.searchMemberById(user.getId()) != null) {
                userProjects.add(project);
            }
        }
        return userProjects;
    }

    /**
     * Get a project by ID
     */
    public Project getProjectById(int projectId) {
        for (Project project : projects) {
            if (project.getProjectID() == projectId) {
                return project;
            }
        }
        return null;
    }

    /**
     * Get all projects
     */
    public ArrayList<Project> getAllProjects() {
        return projects;
    }

    /**
     * Get total number of projects
     */
    public int getProjectCount() {
        return projects.size();
    }
}
