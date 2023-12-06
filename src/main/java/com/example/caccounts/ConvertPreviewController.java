package com.example.caccounts;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class ConvertPreviewController {

    @FXML
    private Label previewLabel;

    private double convertedAmount;

    private double Amount;
    private TransactionListener transactionListener;
    private CheckingAccount checkingAccount;
    private ConvertAccount convertAccount;

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
        previewLabel.setText("Preview: $" + convertedAmount);
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public void setTransactionListener(TransactionListener transactionListener) {
        this.transactionListener = transactionListener;
    }

    public void setAccounts(CheckingAccount checkingAccount, ConvertAccount convertAccount) {
        this.checkingAccount = checkingAccount;
        this.convertAccount = convertAccount;
    }

    @FXML
    private void proceedWithConversion() {
        // Call the convertToConvert method in CheckingAccount
        if(Objects.equals(toAccount, "ToChecking")){
            convertAccount.convertToChecking(Amount,checkingAccount);
        }else{
            checkingAccount.convertToConvert(Amount, convertAccount);
        }


        // Notify the main window about the successful transaction and updated balance
        if (transactionListener != null) {
            transactionListener.onTransactionSuccess();
        }

        // Close the preview window
        closeWindow();
    }

    private void closeWindow() {
        // Close the current window
        Stage stage = (Stage) previewLabel.getScene().getWindow();
        stage.close();
    }

    private String toAccount;

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
}
