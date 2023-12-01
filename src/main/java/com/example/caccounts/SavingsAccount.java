package com.example.caccounts;

import com.CAccounts.UserSession;

public class SavingsAccount extends BankAccount {
    public SavingsAccount(double balance, String currency, String userSession)
    {
        super(balance, currency,userSession);
    }

    public void ConvertToChecking(double amount, CheckingAccount checkingAccount) {
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
            checkingAccount.deposit(amount);
            System.out.println("Conversion to savings account successful.");
        } else {
            System.out.println("Insufficient funds for conversion.");
        }
    }

    public void ConvertToConvert(double amount,String currency,
                                 ConvertAccount convertAccount)
    {
        if(convertAccount.getCurrency() == currency)
        {
            convertAccount.convertToCurrency(amount,currency);
        }
        else {
            System.out.println("Not Same Currency as Current Currency for ConvertAccount, \n" +
                    "Please empty the convert account or change the currency inside the convert account");
        }
    }
}