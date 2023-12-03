package com.example.caccounts;

import com.CAccounts.UserSession;

public class BankAccount {
    private double balance;

    private String currency;

    private String userSession;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public BankAccount(double balance, String currency, String userSession) {
        this.balance = balance;
        this.currency = currency;
        this.userSession = userSession;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit made. Current balance: " + balance);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
           return false;
        }
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}