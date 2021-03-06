package gui.main;

import business.system.Project;
import business.system.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.Scrum;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class HomeController {

    @FXML
    public TitledPane titlePane;
    private User activeUser;
    private Project project;
    @FXML
    private GridPane listSprint;

    @FXML
    private Pane childContent;

    public void setUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public void setProject(Project project) {
        this.project = project;
        this.titlePane.setText(project.getName());
        try {
            changeSubScene("../backlog/Backlog.fxml", "Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change la sub-scene (volet de droite) de la fenetre
     *
     * @param pathToFxml Chemin d'un fxml dont le controlleur implemente MainControlleur
     * @param param      Objet qui sera passé a la fonction init du controlleur
     * @throws IOException
     **/
    public void changeSubScene(String pathToFxml, Object param) throws IOException {

        URL resource = getClass().getResource(pathToFxml);
        FXMLLoader loader = new FXMLLoader(resource);

        Parent root = loader.load();

        MainController childContentController = loader.getController();

        childContentController.setHomeController(this);
        childContentController.setProject(this.project);
        childContentController.setUser(this.activeUser);
        childContentController.init(param);

        ObservableList<Node> children = childContent.getChildren();
//        System.out.println(children);

        if (children.size() > 0) {
            children.set(0, root);
        } else {
            children.add(root);
        }

    }

    // Abandonned
    public void handleChat() {
        System.out.println("HomeController.handleChat");
    }

    public void goToProjectList(MouseEvent mouseEvent) throws IOException, SQLException {
        Scrum.goToProjectList(activeUser, getClass().getResource("../project/ProjectList.fxml"));
    }

    public void handleProjectSetting(MouseEvent mouseEvent) throws IOException {
        this.changeSubScene("../project/EditProject.fxml", null);
    }


    public void goToProductBacklog(ActionEvent event) {
        try {
            this.changeSubScene("../backlog/Backlog.fxml", "Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToTicketBacklog(ActionEvent event) {
        try {
            this.changeSubScene("../backlog/Backlog.fxml", "Ticket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCreationTicket(MouseEvent event) {
        try {
            this.changeSubScene("../ticket/CreateTicket.fxml", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCreationUserStory(MouseEvent mouseEvent) {
        try {
            this.changeSubScene("../userstory/CreateUserStory.fxml", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleCreationSprint(MouseEvent mouseEvent) {
        System.out.println("HomeController.handleCreationSprint");
    }

    public void goToSprints(ActionEvent event) {
        System.out.println("HomeController.goToSprints");
    }

    public void handleAddCollab(MouseEvent mouseEvent) {
        System.out.println("HomeController.handleAddCollab");
    }

    public void goToCollabs(ActionEvent actionEvent) {
        System.out.println("HomeController.goToCollabs");
    }
}
