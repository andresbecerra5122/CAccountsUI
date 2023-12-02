// BankAccountController.java
package com.example.caccounts;

import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class BankAccountController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label checkingBalanceLabel;

    @FXML
    private Label checkingCurrencyLabel;

    @FXML
    private Label convertBalanceLabel;

    @FXML
    private Label convertCurrencyLabel;

    @FXML
    private Label savingsBalanceLabel;

    @FXML
    private Label savingsCurrencyLabel;


    public void initialize() {
        // You can initialize components or perform other actions when the view is loaded

        // Load account information based on the logged-in user
        String loggedInUser = UserSession.getInstance().getLoggedInUser();

        Map<String, BankAccount> accountData = BankAccountData.getAccountData();

        // Get accounts for the logged-in user
        CheckingAccount checkingAccount = (CheckingAccount) accountData.get("Checking");
        ConvertAccount convertAccount = (ConvertAccount) accountData.get("Convert");
        SavingsAccount savingsAccount = (SavingsAccount) accountData.get("Savings");

        // Set balance and currency labels
        checkingBalanceLabel.setText("Balance: $" + checkingAccount.getBalance());
        checkingCurrencyLabel.setText("Currency: " + checkingAccount.getCurrency());

        convertBalanceLabel.setText("Balance: $" + convertAccount.getBalance());
        convertCurrencyLabel.setText("Currency: " + convertAccount.getCurrency());

        savingsBalanceLabel.setText("Balance: $" + savingsAccount.getBalance());
        savingsCurrencyLabel.setText("Currency: " + savingsAccount.getCurrency());

    }

    @FXML
    private void handleSignOut() {
        // You can perform any additional cleanup or logic before signing out

        // Close the current BankAccountUI window
        Stage currentStage = (Stage) checkingBalanceLabel.getScene().getWindow();
        currentStage.close();

        // Open the login window
        openLoginWindow();
    }

    private void openLoginWindow() {
        try {
            // Load the LoginUI.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));

            // Show the login window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
