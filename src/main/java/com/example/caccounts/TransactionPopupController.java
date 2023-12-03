// TransactionPopupController.java (updated)
package com.example.caccounts;

import com.CAccounts.UserAuthentication;
import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionPopupController {

    @FXML
    private TextField amountField;

    @FXML
    private PasswordField passwordField;

    private String transactionType;
    private CheckingAccount checkingAccount;

    private int numberOfTries = 0;
    private TransactionListener transactionListener;

    // Inject the TransactionListener through a method or constructor
    public void setTransactionListener(TransactionListener transactionListener) {
        this.transactionListener = transactionListener;
    }

    public void initData(String transactionType, CheckingAccount checkingAccount) {
        this.transactionType = transactionType;
        this.checkingAccount = checkingAccount;
    }

    @FXML
    private void handleSubmit() {
        if ("Deposit".equals(transactionType)) {
            handleDeposit();
        } else if ("Withdraw".equals(transactionType)) {
            handleWithdraw();
        }

        // Close the pop-up window if the transaction is successful or after 4 tries
        if (numberOfTries >= 4) {
            showAlert("Transaction Failed", "Maximum number of tries reached. Closing the window.");
            closePopup();
        }
    }


    private void handleDeposit() {

        UserAuthentication userAuthentication = new UserAuthentication();
        if (userAuthentication.authenticateUser(UserSession.getInstance().getLoggedInUser(),
                passwordField.getText())) {
            double depositAmount = Double.parseDouble(amountField.getText());

            // Perform deposit for the specific account
            checkingAccount.deposit(depositAmount);

            showAlert("Transaction Successful", "Deposit of $" + depositAmount + " completed successfully.");

            if (transactionListener != null) {
                transactionListener.onTransactionSuccess();
            }

            closePopup();
        } else {
            // Password is invalid, handle accordingly (show an error message, etc.)
            showAlert("Transaction Failed","Password is invalid");
            numberOfTries++;
        }

    }

    private void handleWithdraw() {


        UserAuthentication userAuthentication = new UserAuthentication();
        if (userAuthentication.authenticateUser(UserSession.getInstance().getLoggedInUser(),
                passwordField.getText())) {
            double withdrawAmount = Double.parseDouble(amountField.getText());

            // Perform withdrawal for the specific account
            if (checkingAccount.withdraw(withdrawAmount)) {
                // Withdrawal successful
                // Update the balance labels in the main window
                // mainController.updateBalanceLabels();
                showAlert("Transaction Successful", "Withdrawal of $" + withdrawAmount + " completed successfully.");
                if (transactionListener != null) {
                    transactionListener.onTransactionSuccess();
                }

                closePopup();
            } else {
                // Withdrawal failed (insufficient balance, etc.)
                // Handle accordingly (show an error message, etc.)
                showAlert("Transaction Failed","insufficient balance");
                numberOfTries++;
            }
        } else {
            // Password is invalid, handle accordingly (show an error message, etc.)
            showAlert("Transaction Failed","Password is invalid");
            numberOfTries++;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closePopup() {
        // Close the pop-up window
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }
}
