// BankAccountController.java
package com.example.caccounts;

import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BankAccountController {

    @FXML
    private Label welcomeLabel;


    public void initialize() {
        // You can initialize components or perform other actions when the view is loaded

        // Load account information based on the logged-in user
        String loggedInUser = UserSession.getInstance().getLoggedInUser();

        // For example, you might have a service that retrieves account information
        double amount = 1000.00; // Replace with actual logic to get the amount
        String currency = "USD"; // Replace with actual logic to get the currency

        //amountLabel.setText("Amount: $" + amount);
        //currencyLabel.setText("Currency: " + currency);
    }
}
