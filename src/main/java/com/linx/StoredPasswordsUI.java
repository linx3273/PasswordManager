package com.linx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StoredPasswordsUI extends JFrame{
    private JTextField searchField;
    private JList<PasswordDataClass> passwordList;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton generateButton;
    private JTextField descriptionField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JPanel viewPanel;

    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public StoredPasswordsUI(){
        updateList();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                PasswordDataClass passwordDataClass = new PasswordDataClass();
                passwordDataClass.setDescription(description);
                passwordDataClass.setUsername(username);
                passwordDataClass.setPassword(password);

                databaseHandler.addEntry(passwordDataClass);

                updateList();
            }
        });
    }

    public void updateList(){
        DefaultListModel<PasswordDataClass> defaultListModel = new DefaultListModel<PasswordDataClass>();

        for (PasswordDataClass entry: databaseHandler.fetchAll()){
            defaultListModel.addElement(entry);
        }

        passwordList.setModel(defaultListModel);
    }




    public JPanel getViewPanel(){
        return viewPanel;
    }

}

