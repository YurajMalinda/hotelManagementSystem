package lk.ijse.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent =  FXMLLoader.load(getClass().getResource("/view/log_in_form.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("log in");
        stage.centerOnScreen();

        stage.show();
    }
}
