package gui.login;

import business.facade.GlobalFacade;
import business.system.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Scrum;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text message;

    public void initialize() {
    }

    @FXML
    void goToRegister(ActionEvent event) throws IOException {
        Scrum.setScene(getClass().getResource("../register/Register.fxml"));
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException, SQLException {

        // Pourquoi cela ne change pas ?
        message.setText("Identifying");
        message.setVisible(true);

        String username = usernameField.getText();
        String password = passwordField.getText();

        User u;
        try {
            u = GlobalFacade.getInstance().login(username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            message.setText("Invalid Mail or Password");
            return;
        }

        message.setText("Loading");

        Scrum.goToProjectList(u, getClass().getResource("../project/ProjectList.fxml"));

    }
}

