// CreateUserController.java

package com.example.caccounts;

import com.CAccounts.UserAuthentication;
import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class CreateUserController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private void handleCreateUser() {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();


        CheckingAccount checkingAccount = new CheckingAccount(0.0, "USD", newUsername);
        ConvertAccount convertAccount = new ConvertAccount(0.0, "EUR", newUsername);
        SavingsAccount savingsAccount = new SavingsAccount(0.0, "USD", newUsername);


        Map<String, BankAccount> newAccounts = new HashMap<>();
        newAccounts.put("Checking", checkingAccount);
        newAccounts.put("Convert", convertAccount);
        newAccounts.put("Savings", savingsAccount);


        UserAuthentication userAuthentication = new UserAuthentication();

        if(!userAuthentication.UserExists(newUsername)) {
            userAuthentication.createUser(newUsername, newPassword, newAccounts);
            HelloController Login = new HelloController();
            // Set the user in the session
            UserSession.getInstance().setLoggedInUser(newUsername);
            Login.loadBankAccountView();

            newUsernameField.getScene().getWindow().hide();
        }
        else
        {
           showAlert("UserName Exists","Username slected already exist");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
