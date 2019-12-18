import DAO.factory.AbstractFactoryDAO;
import DAO.mariadb.FactoryDAOMariaDB;
import gui.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class Scrum extends Application {

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();

        //parse java.env var
        String url = env.get("DATA_BASE");
        String dbUser = env.get("USER");
        String dbPwd = env.get("PASSWORD");

        //create factory and add it to facade
        AbstractFactoryDAO.setInstance(new FactoryDAOMariaDB(url, dbUser, dbPwd));

        //start
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //load fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/login/LoginFXML.fxml"));
        Parent root = loader.load();

        // pass Stage
        LoginController lc = loader.<LoginController>getController();
        lc.setPrimaryStage(stage);

        //display fxml
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
