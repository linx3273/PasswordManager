package com.linx;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        StoredPasswordsUI storedPasswordsUI = new StoredPasswordsUI();
        storedPasswordsUI.setContentPane(storedPasswordsUI.getViewPanel());
        storedPasswordsUI.setTitle("Password Manager");
        storedPasswordsUI.setSize(600, 400);
        storedPasswordsUI.setVisible(true);
        storedPasswordsUI.setResizable(false);
        storedPasswordsUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}