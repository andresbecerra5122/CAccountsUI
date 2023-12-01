package com.example.caccounts;

import com.CAccounts.UserSession;

public class ConvertAccount extends BankAccount {

    private Converter converter;

    public ConvertAccount(double balance, String currency, String userSession) {
        super(balance,currency,userSession);
        this.converter = new Converter();
    }

    public void deposit(double amount) {

        setBalance(getBalance()+ amount);
        System.out.println("Deposit made. Current balance: " + getBalance() + " " + getCurrency());
    }

    public void withdraw(double amount, String withdrawalCurrency) {
        if (amount <= getBalance()) {
            double withdrawnAmount = converter.convert(amount, withdrawalCurrency, getCurrency());
            setBalance(getBalance() - withdrawnAmount);
            System.out.println("Withdrawal made. Amount withdrawn: " + withdrawnAmount + " " + getCurrency() +
                    ". Current balance: " + getBalance() + " " + getCurrency());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void convertToCurrency(double amount, String targetCurrency) {
        double convertedAmount = converter.convert(amount, getCurrency(), targetCurrency);
        setBalance(getBalance() + amount);
        System.out.println("Conversion made. Amount converted: " + amount + " " + getCurrency() +
                " to " + convertedAmount + " " + targetCurrency + ". Current balance: " + getBalance() + " " + getCurrency());
    }

    public void receiveFundsFromChecking(double amount, String checkingCurrency) {
        // Convert the amount to the account's currency
        double convertedAmount = converter.convert(amount, checkingCurrency, getCurrency());

        // Add the converted amount to the account's balance
        setBalance(getBalance() + convertedAmount);

        System.out.println("Funds received from CheckingAccount: " + convertedAmount + " " + getCurrency() +
                ". Current balance: " + getBalance() + " " + getCurrency());
    }
}
