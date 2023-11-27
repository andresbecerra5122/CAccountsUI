package com.CAccounts;

import com.CAccounts.view.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public Main() {
        // Default constructor
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setUserAuthentication(new UserAuthentication());

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Login Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
