package gui.userstory;

import business.facade.GlobalFacade;
import business.system.Column;
import business.system.ProductBacklog;
import business.system.UserStory;
import gui.main.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class UserStoryController extends AbstractController {

    private UserStory currentUS;

    @FXML
    private ChoiceBox<Column> columnPicker;

    @FXML
    private Button editButton;

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descrField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField scoreField;

    @FXML
    private Text message;

    private boolean delete;
    private Column oldColumn;

    @FXML
    void exit() {
        getHomeController().goToProductBacklog(null);
    }

    @FXML
    void handleEdit(ActionEvent event) throws IOException {

        message.setVisible(false);

        if (!nomField.isEditable()) {
            nomField.setEditable(true);
            descrField.setEditable(true);
            datePicker.setEditable(true);
            scoreField.setEditable(true);
            editButton.setText("Confirm Edit");
            message.setText("You can now edit the User Story.");
            message.setVisible(true);
            return;
        }

        String name = nomField.getText();
        String descr = descrField.getText();
        int score = Integer.parseInt(scoreField.getText());

        Date deadline = Date.valueOf(datePicker.getValue());

        boolean success = false;

        try {
            success = GlobalFacade.getInstance()
                    .updateUserStory(currentUS,
                            new UserStory(currentUS.getId(), name, descr, score, deadline));
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("Error submitting User Story");
            message.setVisible(true);
            return;
        }

        if (!success){
            message.setText("Error submitting User Story");
            message.setVisible(true);
            return;
        }

        try {
            GlobalFacade.getInstance().moveComponent(currentUS, oldColumn, columnPicker.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handleDelete(ActionEvent event) throws IOException {
        if (!delete) {
            this.delete = true;
            message.setText("Are you sure ? Press delete again to confirm.");
            message.setVisible(true);
            return;
        }
        boolean success ;
        try {
            success = GlobalFacade.getInstance().deleteUserStory(currentUS);
        } catch (SQLException e) {
            message.setText("Error deleting User Story");
            message.setVisible(true);
            return;
        }
        if (success) {
            exit();
        } else {
            message.setText("Error deleting User Story");
            message.setVisible(true);
        }
    }

    @Override
    public void init(Object param) {

        this.delete = false;

        this.currentUS = (UserStory) param;

        nomField.setText(currentUS.getName());
        descrField.setText(currentUS.getDescription());
        datePicker.setValue(currentUS.getDeadline().toLocalDate());
        scoreField.setText(String.valueOf(currentUS.getScore()));

        Column[] column;
        try {
            ProductBacklog pb = GlobalFacade.getInstance().getProductBacklog(getProject());
            column = GlobalFacade.getInstance().getColumn(pb);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        columnPicker.getItems().addAll(column);
        columnPicker.setValue(column[0]);

        oldColumn = new Column(currentUS.getColumnId(), null, -1);
    }

}