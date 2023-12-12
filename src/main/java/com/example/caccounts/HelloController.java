package com.example.caccounts;

import com.CAccounts.UserAuthentication;
import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserAuthentication userAuthentication;

    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserAuthentication userAuthentication = new UserAuthentication();
        if (userAuthentication.authenticateUser(username, password)) {
            System.out.println("Login successful andres!");

            // Set the user in the session
            UserSession.getInstance().setLoggedInUser(username);

            // Load the BankAccountUI.fxml and set it as the new scene
            loadBankAccountView();
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    public void loadBankAccountView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BankAccountUI.fxml"));
            Parent root = loader.load();

            // Get the controller from the FXMLLoader
            BankAccountController bankAccountController = loader.getController();

            // Set any data or perform initialization in the BankAccountController if needed


            Stage stage = new Stage();
            stage.setTitle("Bank Account View");
            stage.setScene(new Scene(root, 400, 300));
            String css = getClass().getResource("styles.css").toExternalForm();
            stage.getScene().getStylesheets().add(css);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateUserWindow() {
        try {
            // Load the CreateUserView.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUserView.fxml"));
            Parent root = loader.load();

            // Create a new stage for the create user window
            Stage createUserStage = new Stage();
            createUserStage.setTitle("Create New User");
            createUserStage.setScene(new Scene(root));

            // Set the window to be modal
            createUserStage.initModality(Modality.APPLICATION_MODAL);

            // Show the create user window
            createUserStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}