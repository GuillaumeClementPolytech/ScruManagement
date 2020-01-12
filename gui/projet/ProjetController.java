package gui.projet;

import business.system.Projet;
import business.system.User;
import gui.main.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.Scrum;

import java.io.IOException;

public class ProjetController {

    private Projet projet;
    private User user;

    @FXML
    private Text projectName;

    @FXML
    void loadProjet() throws IOException {
        // Todo
        System.out.println("ProjetController.loadProjet");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../main/Home.fxml"));

        Parent root = loader.load();
        HomeController cont = loader.<HomeController>getController();

        cont.setProjet(projet);
        cont.setUser(user);

        Scene scene = new Scene(root);
        Scrum.getStage().setScene(scene);

    }

    public void setProject(Projet p) {
        this.projet = p;
        this.projectName.setText(p.getName());
    }

    public void setUser(User u) {
        this.user = u;
    }

    public void editProject(MouseEvent mouseEvent) {
        // Todo : go to edit project screen (Add user, change name, ...)
        System.out.println("ProjetController.editProject");
        System.out.println("mouseEvent = " + mouseEvent);
    }
}