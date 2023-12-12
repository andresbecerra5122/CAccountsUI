package com.CAccounts;

import com.example.caccounts.BankAccount;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAuthentication {

    private Map<String, String> userDatabase;

    private static final String FILE_PATH = "src/main/resources/users.txt";
    private static final String ACCOUNTS_FILE = "src/main/resources/accounts.txt";


    public UserAuthentication() {
        userDatabase = new HashMap<>();
        loadUsersFromFile(FILE_PATH);
    }

    private void loadUsersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(":");
                userDatabase.put(userData[0], userData[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.newLine();
            writer.write(username + ":" + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        String storedPassword = userDatabase.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            // Set the user in the session upon successful login
            UserSession.getInstance().setLoggedInUser(username);
            return true;
        }
        return false;
    }

    public void createUser(String username, String password, Map<String, BankAccount> accounts) {

        // Save the new user and accounts to the file
        saveUserToFile(username, password);
        saveAccountData(username, accounts);
    }

    public boolean UserExists(String username){
        return userDatabase.containsKey(username);

    }

    private void removeUserFromFile(String username) {

        try {
            // Read the existing file
            List<String> lines = Files.readAllLines(Paths.get(ACCOUNTS_FILE));

            // Remove lines with the given username
            lines.removeIf(line -> line.startsWith(username + ":"));

            // Write the updated lines back to the file
            Files.write(Paths.get(ACCOUNTS_FILE), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAccountData(String username, Map<String, BankAccount> accounts) {
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
    }
}
