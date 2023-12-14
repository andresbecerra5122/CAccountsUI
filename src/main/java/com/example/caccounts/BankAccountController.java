// BankAccountController.java
package com.example.caccounts;

import com.CAccounts.UserSession;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class BankAccountController implements TransactionListener {
    @FXML
    public CheckBox currencyConversionCheckBox;
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
    private TextField transferAmountField;
    @FXML
    private CheckBox enableTransferCheckbox;
    @FXML
    private Button transferButton;

    @FXML
    private CheckBox enableTransferCheckingCheckbox;

    @FXML
    private TextField transferCheckingAmountField;

    @FXML
    private Button transferCheckingButton;
    @FXML
    private TextField convertAmountField;
    @FXML
    private CheckBox enableConvertCheckbox;
    @FXML
    private Button convertButton;
    @FXML
    private CheckBox enableConvertCheckingCheckbox;
    @FXML
    private TextField convertCheckingAmountField;
    @FXML
    private Button convertCheckingButton;
    private Stage stage;
    int decimalPlaces = 3;

    Map<String, BankAccount> accountData;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                handleCloseRequest(event);
            }
        });
    }

    public void initialize() {
        // Load account information based on the logged-in user
        String loggedInUser = UserSession.getInstance().getLoggedInUser();

        BankAccountData.loadAccountData();
        accountData = BankAccountData.getAccountData(loggedInUser);


        // Get accounts for the logged-in user
        CheckingAccount checkingAccount = (CheckingAccount) accountData.get("Checking");
        this.checkingAccount =  checkingAccount;
        ConvertAccount convertAccount = (ConvertAccount) accountData.get("Convert");
        this.convertAccount = convertAccount;
        SavingsAccount savingsAccount = (SavingsAccount) accountData.get("Savings");
        this.savingsAccount = savingsAccount;

        checkingBalanceLabel.setText("Balance: $" + returnCorrectFormat(checkingAccount.getBalance()));
        checkingCurrencyLabel.setText("Currency: " + checkingAccount.getCurrency());

        convertBalanceLabel.setText("Balance: $" + returnCorrectFormat(convertAccount.getBalance()));
        convertCurrencyLabel.setText("Currency: " + convertAccount.getCurrency());

        savingsBalanceLabel.setText("Balance: $" + returnCorrectFormat(savingsAccount.getBalance()));
        savingsCurrencyLabel.setText("Currency: " + savingsAccount.getCurrency());

    }

    private String returnCorrectFormat(double balance) {

       return  String.format("%." + decimalPlaces + "f", balance);
    }

    private CheckingAccount checkingAccount;

    private ConvertAccount convertAccount;

    private SavingsAccount savingsAccount;

    @FXML
    private void handleSignOut() {
        
        BankAccountData.saveAccountData(checkingAccount.getUserSession(), accountData);
        // Close the current BankAccountUI window
        Stage currentStage = (Stage) checkingBalanceLabel.getScene().getWindow();
        currentStage.close();

        openLoginWindow();
    }

    private void openLoginWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));

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
    private void handleCheckboxActionConvert() {
        // Enable or disable the TextField and Button based on the checkbox state
        boolean isCheckboxSelected = enableConvertCheckbox.isSelected();
        convertAmountField.setDisable(!isCheckboxSelected);
        convertButton.setDisable(!isCheckboxSelected);
    }

    @FXML
    private void handleCheckboxActionSavings() {
        // Enable or disable the TextField and Button based on the checkbox state
        boolean isCheckboxSelected = enableTransferCheckbox.isSelected();
        transferAmountField.setDisable(!isCheckboxSelected);
        transferButton.setDisable(!isCheckboxSelected);
    }

    @FXML
    private void handleCheckboxCheckingActionConvert() {
        // Enable or disable the TextField and Button based on the checkbox state
        boolean isCheckboxSelected = enableConvertCheckingCheckbox.isSelected();
        convertCheckingAmountField.setDisable(!isCheckboxSelected);
        convertCheckingButton.setDisable(!isCheckboxSelected);
    }

    @Override
    public void onTransactionSuccess() {
        // Update the checking balance label in the main window
        checkingBalanceLabel.setText("Balance: $" + returnCorrectFormat(checkingAccount.getBalance()));
        convertBalanceLabel.setText("Balance: $" + returnCorrectFormat(convertAccount.getBalance()));
        convertCurrencyLabel.setText("Currency: " + convertAccount.getCurrency());
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
            TransactionConvertPopupController popupController = loader.getController();
            popupController.setAccount( checkingAccount);
            popupController.setTransactionType(transactionType);
            popupController.setTransactionListener(this);

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConvert() {

        // Get the user input amount (you should validate the input)
        double userInputAmount;
        try {
            userInputAmount = Double.parseDouble(convertAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid numeric amount.");
            return;
        }

        if (userInputAmount > checkingAccount.getBalance()) {
            showAlert("Insufficient Balance", "The amount exceeds your checking account balance.");
            return;
        }

        showConvertPreview(convertAccount.getConvertedAmount(userInputAmount,checkingAccount.getCurrency())
                ,userInputAmount, checkingAccount, convertAccount, "ToConvert");
    }

    @FXML
    private void handleConvertToChecking() {

        double userInputAmount;
        try {
            userInputAmount = Double.parseDouble(convertCheckingAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid numeric amount.");
            return;
        }

        // Check if the user input amount exceeds the checking account balance
        if (userInputAmount > convertAccount.getBalance()) {
            showAlert("Insufficient Balance", "The amount exceeds your checking account balance.");
            return;
        }

        // Show the convert preview window
        showConvertPreview(convertAccount.getConvertedAmountTo(userInputAmount,checkingAccount.getCurrency())
                ,userInputAmount, checkingAccount, convertAccount,"ToChecking");
    }

    private void refreshBalances(CheckingAccount checkingAccount, SavingsAccount savingsAccount) {
        // Update the displayed balances in the UI
        checkingBalanceLabel.setText("Balance: $" + returnCorrectFormat(checkingAccount.getBalance()));
        savingsBalanceLabel.setText("Balance: $" + returnCorrectFormat(savingsAccount.getBalance()));
    }

    private void showConvertPreview(double convertedAmount, double userInputAmount, CheckingAccount checkingAccount, ConvertAccount convertAccount, String toAccount) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConvertPreview.fxml"));
            Parent root = loader.load();

            // Create a new stage for the preview window
            Stage previewStage = new Stage();
            previewStage.setTitle("Convert Preview");
            previewStage.setScene(new Scene(root));

            // Set the preview window to be modal
            previewStage.initModality(Modality.APPLICATION_MODAL);

            ConvertPreviewController previewController = loader.getController();
            previewController.setConvertedAmount(convertedAmount);
            previewController.setAmount(userInputAmount);
            previewController.setTransactionListener(this);
            previewController.setToAccount(toAccount);
            previewController.setAccounts(checkingAccount, convertAccount);

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

    @FXML
    private void handleTransfer() {

        if (!enableTransferCheckbox.isSelected()) {
            showAlert("Transfer Disabled", "Please enable transfer to proceed.");
            return;
        }

        double userInputAmount;
        try {
            userInputAmount = Double.parseDouble(transferAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid numeric amount.");
            return;
        }

        // Check if the user input amount exceeds the checking account balance
        if (userInputAmount > checkingAccount.getBalance()) {
            showAlert("Insufficient Balance", "The amount exceeds your checking account balance.");
            return;
        }

        checkingAccount.convertToSavings(userInputAmount, savingsAccount);

        // Refresh the displayed balances
        refreshBalances(checkingAccount, savingsAccount);

        showAlert("Transfer Successful", "Money transferred to Savings Account.");
    }

    @FXML
    private void handleConvertWithdraw() {
        showTransactionConvertPopup("Withdraw", "Convert");
    }

    @FXML
    private void handleConvertDeposit() {
        showTransactionConvertPopup("Deposit","Convert");
    }

    private void showTransactionConvertPopup(String transactionType, String account) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionPopup.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle(transactionType);
            popupStage.setScene(new Scene(root));

            popupStage.initModality(Modality.APPLICATION_MODAL);

            TransactionConvertPopupController popupController = loader.getController();
            popupController.setTransactionType(transactionType);
            popupController.setTransactionListener(this);
            if(Objects.equals(account, "Convert")) {
                popupController.setAccount(convertAccount);
            }
            else {
                popupController.setAccount(convertAccount);
            }
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConvertCurrency() {
        showCurrencyConversionPopup(convertAccount);
    }


    private void showCurrencyConversionPopup(ConvertAccount convertAccount) {
        try {
            // Load the CurrencyConversionPopup.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrencyConversionPopup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the pop-up window
            Stage popupStage = new Stage();
            popupStage.setTitle("Currency Conversion");
            popupStage.setScene(new Scene(root));

            // Set the pop-up window to be modal
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set controller for the pop-up
            CurrencyConversionController popupController = loader.getController();
            popupController.setConvertAccount(convertAccount);
            popupController.setTransactionListener(this);


            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckboxChecinkgActionSavings() {

        // Enable or disable the TextField and Button based on the checkbox state
        boolean isCheckboxSelected = enableTransferCheckingCheckbox.isSelected();
        transferCheckingAmountField.setDisable(!isCheckboxSelected);
        transferCheckingButton.setDisable(!isCheckboxSelected);
    }


    @FXML
    private void handleTransferFromSavings() {

        if (!enableTransferCheckingCheckbox.isSelected()) {
            showAlert("Transfer Disabled", "Please enable transfer to proceed.");
            return;
        }

        double userInputAmount;
        try {
            userInputAmount = Double.parseDouble(transferCheckingAmountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid numeric amount.");
            return;
        }

        if (userInputAmount > savingsAccount.getBalance()) {
            showAlert("Insufficient Balance", "The amount exceeds your checking account balance.");
            return;
        }

       savingsAccount.ConvertToChecking(userInputAmount,checkingAccount);

        refreshBalances(checkingAccount, savingsAccount);

        showAlert("Transfer Successful", "Money transferred to Checking Account.");
    }

    private void handleCloseRequest(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Closing Bank Account");
        alert.setContentText("Are you sure you want to close your bank account?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            BankAccountData.saveAccountData(checkingAccount.getUserSession(), accountData);
            Stage currentStage = this.stage;
            currentStage.close();
        } else {

            event.consume();
        }
    }

}
