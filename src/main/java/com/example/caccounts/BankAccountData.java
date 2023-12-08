// BankAccountData.java (new)
package com.example.caccounts;

import com.example.caccounts.BankAccount;
import com.example.caccounts.CheckingAccount;
import com.example.caccounts.ConvertAccount;
import com.example.caccounts.SavingsAccount;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankAccountData {
   /* private static Map<String, BankAccount> accountData;

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
    }*/

    private static final String ACCOUNTS_FILE = "src/main/resources/accounts.txt";
    private static Map<String, Map<String, BankAccount>> userAccountData;

    static {
        userAccountData = new HashMap<>();
        loadAccountData();
    }

    public static void loadAccountData() {
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(":");
                String username = userData[0];
                String accountType = userData[1];
                double balance = Double.parseDouble(userData[2]);
                String currency = userData[3];
                String userSession = userData[4];

                BankAccount account;
                switch (accountType) {
                    case "Checking":
                        account = new CheckingAccount(balance, currency, userSession);
                        break;
                    case "Convert":
                        account = new ConvertAccount(balance, currency, userSession);
                        break;
                    case "Savings":
                        account = new SavingsAccount(balance, currency, userSession);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid account type: " + accountType);
                }

                // Add the account to the user's account data
                userAccountData.computeIfAbsent(username, k -> new HashMap<>());
                userAccountData.get(username).put(accountType, account);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, BankAccount> getAccountData(String username) {
        return userAccountData.get(username);
    }

 /*   public static void saveAccountData(String username, Map<String, BankAccount> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true))) {
            for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
                BankAccount account = entry.getValue();
                String line = String.format("%s:%s:%.2f:%s:%s",
                        username, entry.getKey(), account.getBalance(), account.getCurrency(), account.getUserSession());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
 public static void saveAccountData(String username, Map<String, BankAccount> accounts) {
     try {
         // Read existing data
         StringBuilder fileContent = new StringBuilder();
         try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 // Exclude lines related to the specified username
                 if (!line.startsWith(username + ":")) {
                     fileContent.append(line).append("\n");
                 }
             }
         }

         // Append new data
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
             writer.write(fileContent.toString());  // Write existing data (excluding the specified username)
             for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
                 BankAccount account = entry.getValue();
                 String newLine = String.format("%s:%s:%.2f:%s:%s",
                         username, entry.getKey(), account.getBalance(), account.getCurrency(), account.getUserSession());
                 writer.write(newLine);
                 writer.newLine();
             }
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}
