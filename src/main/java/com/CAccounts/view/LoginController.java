// LoginController.java
package com.CAccounts.view;

import com.CAccounts.UserAuthentication;
import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

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
            System.out.println("Login successful!");

            // Set the user in the session
            UserSession.getInstance().setLoggedInUser(username);

            // You can navigate to another view or perform other actions here
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }
}
