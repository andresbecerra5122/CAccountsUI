// BankAccountData.java (new)
package com.example.caccounts;

import com.example.caccounts.BankAccount;
import com.example.caccounts.CheckingAccount;
import com.example.caccounts.ConvertAccount;
import com.example.caccounts.SavingsAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountData {
    private static Map<String, BankAccount> accountData;

    static {
        accountData = new HashMap<>();

        // Dummy data for user "Abdullah"
        CheckingAccount checkingAccount = new CheckingAccount( 1000.00,"USD", "Abdullah");
        ConvertAccount convertAccount = new ConvertAccount(750.00, "EUR","Abdullah");
        SavingsAccount savingsAccount = new SavingsAccount( 5000.00,"USD","Abdullah");

        accountData.put("Checking", checkingAccount);
        accountData.put("Convert", convertAccount);
        accountData.put("Savings", savingsAccount);
    }

    public static Map<String, BankAccount> getAccountData() {
        return accountData;
    }
}
