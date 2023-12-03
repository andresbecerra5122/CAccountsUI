// BankAccountController.java
package com.example.caccounts;

import com.CAccounts.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class BankAccountController implements TransactionListener {

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

    @FXML
    private Tab checkingTab;
    @FXML
    private TextField convertAmountField;
    @FXML
    private CheckBox enableConvertCheckbox;
    @FXML
    private Button convertButton;


    public void initialize() {
        // You can initialize components or perform other actions when the view is loaded

        // Load account information based on the logged-in user
        String loggedInUser = UserSession.getInstance().getLoggedInUser();

        Map<String, BankAccount> accountData = BankAccountData.getAccountData();

        // Get accounts for the logged-in user
        CheckingAccount checkingAccount = (CheckingAccount) accountData.get("Checking");
        this.checkingAccount =  checkingAccount;
        ConvertAccount convertAccount = (ConvertAccount) accountData.get("Convert");
        this.convertAccount = convertAccount;
        SavingsAccount savingsAccount = (SavingsAccount) accountData.get("Savings");
        this.savingsAccount = savingsAccount;

        // Set balance and currency labels
        checkingBalanceLabel.setText("Balance: $" + checkingAccount.getBalance());
        checkingCurrencyLabel.setText("Currency: " + checkingAccount.getCurrency());

        convertBalanceLabel.setText("Balance: $" + convertAccount.getBalance());
        convertCurrencyLabel.setText("Currency: " + convertAccount.getCurrency());

        savingsBalanceLabel.setText("Balance: $" + savingsAccount.getBalance());
        savingsCurrencyLabel.setText("Currency: " + savingsAccount.getCurrency());

    }

    private CheckingAccount checkingAccount;

    private ConvertAccount convertAccount;

    private SavingsAccount savingsAccount;

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
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

    @FXML
    private void handleWithdraw() {
        showTransactionPopup("Withdraw");
    }

    @FXML
    private void handleDeposit() {
        showTransactionPopup("Deposit");
    }

    @FXML
    private void handleCheckboxAction() {
        // Enable or disable the TextField and Button based on the checkbox state
        boolean isCheckboxSelected = enableConvertCheckbox.isSelected();
        convertAmountField.setDisable(!isCheckboxSelected);
        convertButton.setDisable(!isCheckboxSelected);
    }

    @Override
    public void onTransactionSuccess() {
        // Update the checking balance label in the main window
        checkingBalanceLabel.setText("Balance: $" + checkingAccount.getBalance());
        convertBalanceLabel.setText("Balance: $" + convertAccount.getBalance());
    }

    private void showTransactionPopup(String transactionType) {
        try {
            // Load the TransactionPopup.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionPopup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the pop-up window
            Stage popupStage = new Stage();
            popupStage.setTitle(transactionType);
            popupStage.setScene(new Scene(root));

            // Set the pop-up window to be modal
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set controller for the pop-up
            TransactionPopupController popupController = loader.getController();
            popupController.initData(transactionType, checkingAccount);
            popupController.setTransactionListener(this);


            popupStage.showAndWait();

            // You can handle the result from the pop-up (e.g., update the balance)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConvert() {

        // Check if conversion is enabled (checkbox is checked)
        if (!enableConvertCheckbox.isSelected()) {
            showAlert("Conversion Disabled", "Please enable conversion to proceed.");
            return;
        }

        // Get the user input amount (you should validate the input)
        double userInputAmount;
        try {
            userInputAmount = Double.parseDouble(convertAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid numeric amount.");
            return;
        }

        // Check if the user input amount exceeds the checking account balance
        if (userInputAmount > checkingAccount.getBalance()) {
            showAlert("Insufficient Balance", "The amount exceeds your checking account balance.");
            return;
        }

        // Perform the conversion
        //checkingAccount.convertToConvert(userInputAmount, convertAccount);

        // Refresh the displayed balances
        //refreshBalances(checkingAccount, convertAccount);

        // Show the convert preview window
        showConvertPreview(convertAccount.getConvertedAmount(userInputAmount,checkingAccount.getCurrency())
                ,userInputAmount, checkingAccount, convertAccount);
    }

    private void refreshBalances(CheckingAccount checkingAccount, ConvertAccount convertAccount) {
        // Update the displayed balances in the UI
        checkingBalanceLabel.setText("Balance: $" + checkingAccount.getBalance());
        convertBalanceLabel.setText("Balance: $" + convertAccount.getBalance());
    }

    private void showConvertPreview(double convertedAmount, double userInputAmount, CheckingAccount checkingAccount, ConvertAccount convertAccount) {
        // Load the ConvertPreview.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConvertPreview.fxml"));
            Parent root = loader.load();

            // Create a new stage for the preview window
            Stage previewStage = new Stage();
            previewStage.setTitle("Convert Preview");
            previewStage.setScene(new Scene(root));

            // Set the preview window to be modal
            previewStage.initModality(Modality.APPLICATION_MODAL);

            // Set controller for the preview window
            ConvertPreviewController previewController = loader.getController();
            previewController.setConvertedAmount(convertedAmount);
            previewController.setAmount(userInputAmount);
            previewController.setTransactionListener(this);
            previewController.setAccounts(checkingAccount, convertAccount);

            // Show the preview window
            previewStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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
