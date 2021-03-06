package business.facade;

import DAO.ProjectDAO;
import DAO.factory.AbstractFactoryDAO;
import business.system.Collaborator;
import business.system.Project;
import business.system.Sprint;
import business.system.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ProjectFacade {

    private GlobalFacade globalFacade;

    public List<Project> getProjectListFromUser(User user) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectListFromUser(user.getId());
    }

    public Project getProjectByID(int idProject) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectByID(idProject);
    }


    public List<Collaborator> getProjectTeam(Project project) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectTeam(project.getId());
    }

    public User getProjectAdmin(Project project) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectAdmin(project.getId());
    }

    public User getProjectScrumMaster(Project project) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectScrumMaster(project.getId());
    }

    public User getProjectProductOwner(Project project) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectProductOwner(project.getId());
    }

    public List<User> getProjectDevelopers(Project project) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.getProjectDevelopers(project.getId());
    }


    public Project createProject(String name, String summary, String type, Date deadline, User user) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        Project project = projectDAO.createProject(name, summary, type, deadline);
        if (project == null) {
            return null;
        }
        boolean success = globalFacade.initiateProductTicketBacklog(project.getId());
        if (!success) {
            return null;
        }
        String role = "Scrum Master";
        int idRole;
        if (role.equals("Scrum Master")) {
            idRole = 2;
        } else if (role.equals("Product Owner")) {
            idRole = 3;
        } else {
            idRole = 4;
        }
        Collaborator collaborator = GlobalFacade.getInstance().addCollaborator(project.getId(),
                user.getId(), idRole, true);
        if (collaborator == null) {
            return null;
        }
        return project;
    }

    public boolean editProject(Project project) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.editProject(project);
    }

    public boolean deleteProject(Project project) throws SQLException {
        // Remove Sprint
        Sprint[] sprints = globalFacade.getSprintsByProject(project);
        for (Sprint s : sprints) {
            boolean success = globalFacade.deleteSprint(s);
            if (!success) {
                System.err.println("Pb delete Sprint");
            }
        }

        // Remove Backlogs
        boolean success = globalFacade.deleteBacklogs(project);
        if (!success) {
            System.err.println("Err Rm Backlogs");
            return false;
        }

        // Remove Collaborators
        List<Collaborator> projectTeam = getProjectTeam(project);
        for (Collaborator c : projectTeam) {
            removeCollaborator(c);
        }

        // delete Project
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.deleteProject(project.getId());
    }




    public Collaborator addCollaborator(int idProject, int idCollaborator, int idRole, boolean isAdmin) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.addCollaborator(idProject, idCollaborator, idRole, isAdmin);
    }

    public boolean editCollaborator(Collaborator collaborator) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.editCollaborator(collaborator.getIdProject(), collaborator.getIdUser(), collaborator.getIdRole(), collaborator.isAdmin());
    }

    public boolean removeCollaborator(Collaborator collaborator) throws SQLException {
        ProjectDAO projectDAO = AbstractFactoryDAO.getInstance().createProjectDAO();
        return projectDAO.removeCollaborator(collaborator.getIdProject(), collaborator.getIdUser());
    }

    public void setGlobalFacade(GlobalFacade globalFacade) {
        this.globalFacade = globalFacade;
    }
}