package business.system;

public class ProductOwner extends Collaborator {

    public ProductOwner(int idUser, int idProject) {
        super.idRole = 3;
        super.idUser = idUser;
        super.idProject = idProject;
        super.isAdmin = false;
    }

    public ProductOwner(int idUser, int idProject, boolean isAdmin) {
        super.idRole = 3;
        super.idUser = idUser;
        super.idProject = idProject;
        super.isAdmin = isAdmin;
    }
}