package com.example.caccounts;

import com.CAccounts.UserAuthentication;
import com.CAccounts.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

// CurrencyConversionController.java
public class CurrencyConversionController {
    @FXML
    private Converter converter;

    public void initialize() {
        // Initialize the ComboBox with currencies
        List<String> currencies = new Converter().getCurrencies();
        ObservableList<String> currencyOptions = FXCollections.observableArrayList(currencies);
        currencyComboBox.setItems(currencyOptions);
    }

    private ConvertAccount convertAccount;

    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    private PasswordField  passwordField;

    public void setConvertAccount(ConvertAccount convertAccount) {
        this.convertAccount = convertAccount;
    }

    @FXML
    private CheckBox enableConversionCheckBox;

    private TransactionListener transactionListener;


    public void setTransactionListener(TransactionListener transactionListener) {
        this.transactionListener = transactionListener;
    }

    @FXML
    private void handleConvert() {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (userAuthentication.authenticateUser(UserSession.getInstance().getLoggedInUser(),
                passwordField.getText())) {

            String targetCurrency = currencyComboBox.getValue();

            try {
                convertAccount.convertCurrency(targetCurrency);
            }
            catch(Exception e)
            {
                showAlert("Error while converting currency", e.getMessage());
            }


            if (transactionListener != null) {
                transactionListener.onTransactionSuccess();
            }
            // Close the pop-up window
            closePopup();
        }
        else{
            showAlert("Transaction Failed","Password is invalid");
        }

    }
    private void closePopup() {
        // Close the pop-up window
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
