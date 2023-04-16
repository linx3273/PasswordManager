package com.linx;

import com.linx.MasterPassword.MasterPasswordCheck;
import com.linx.MasterPassword.MasterPasswordSet;
import com.linx.PasswordStorer.StoredPasswordsUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        if (!databaseHandler.masterPasswordExists()) {
            // master password does not exist
            // prompt user to create master password
            if (MasterPasswordSet.masterPasswordSetter()) {
                // master password has been created successfully
                // prompt user to login using master password
                if (MasterPasswordCheck.masterPasswordChecker()) {
                    // user has authenticated correctly
                    // display the password manager window
                    StoredPasswordsUI storedPasswordsUI = new StoredPasswordsUI();
                    storedPasswordsUI.setContentPane(storedPasswordsUI.getViewPanel());
                    storedPasswordsUI.setTitle("Password Manager");
                    storedPasswordsUI.setSize(600, 400);
                    storedPasswordsUI.setVisible(true);
                    storedPasswordsUI.setResizable(false);
                    storedPasswordsUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        } else {
            // master password already exists
            // prompt user to authenticate
            if (MasterPasswordCheck.masterPasswordChecker()) {
                // user has authenticated correctly
                // display the password manager window
                StoredPasswordsUI storedPasswordsUI = new StoredPasswordsUI();
                storedPasswordsUI.setContentPane(storedPasswordsUI.getViewPanel());
                storedPasswordsUI.setTitle("Password Manager");
                storedPasswordsUI.setSize(600, 400);
                storedPasswordsUI.setVisible(true);
                storedPasswordsUI.setResizable(false);
                storedPasswordsUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

        }
        System.exit(0);
    }
}