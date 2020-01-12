package gui.ticket;

import business.facade.GlobalFacade;
import business.system.Project;
import business.system.Ticket;
import business.system.User;
import gui.main.AbstractControlleur;
import gui.main.HomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class CreateTicketController extends AbstractControlleur {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    private Project currentProject;
    private User currentUser;
    private HomeController homeControlleur;

    @FXML
    private Text message;

    @FXML
    void handleCreationTicket(ActionEvent event) throws IOException {

        String titleTicket = titleField.getText();
        String descriptionTicket = descriptionField.getText();

        boolean success = false;
        Ticket newTicket = new Ticket(-1, titleTicket, descriptionTicket, "Unsolved");

        try {
            success = GlobalFacade.getInstance().addTicket(newTicket, currentProject.getId());
        } catch (SQLException e) {
            message.setText("Error adding User Story");
            message.setVisible(true);
            return;
        }

        if (success) {
            homeControlleur.changeSubScene("../main/HomeController", newTicket);
        } else {
            message.setText("Error adding User Story");
            message.setVisible(true);
        }
    }

    public void exit() throws IOException {
        // TODO : goto Ticket Backlog
        System.out.println("Exit pressed");
        // homeControlleur.changeSubScene("", );
    }

    @Override
    public void setProject(Project project) {
        this.currentProject = project;
    }

    @Override
    public void setUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void setHomeControlleur(HomeController homeControlleur) {
        this.homeControlleur = homeControlleur;
    }

    @Override
    public void init(Object param) {

    }
}
