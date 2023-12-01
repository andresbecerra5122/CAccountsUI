// BankAccountController.java
package com.example.caccounts;

import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
