package com.example.caccounts;

import com.CAccounts.UserSession;

public class CheckingAccount extends BankAccount {


    public CheckingAccount(double balance, String currency, String userSession) {
        super(balance, currency, userSession);
    }
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("Deposit made. Current balance: " + getBalance());
    }
    public void convertToSavings(double amount, SavingsAccount savingsAccount) {
        if (amount <= getBalance()) {
            setBalance(getBalance() + amount);
            savingsAccount.deposit(amount);
            System.out.println("Conversion to savings account successful.");
        } else {
            System.out.println("Insufficient funds for conversion.");
        }
    }

    public void convertToConvert(double amount,
                                 ConvertAccount convertAccount)
    {
           withdraw(amount);
           convertAccount.convertToCurrency(amount,getCurrency());
    }


}
