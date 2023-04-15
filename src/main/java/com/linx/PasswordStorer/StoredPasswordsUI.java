package com.linx.PasswordStorer;

import com.linx.DatabaseHandler;

import javax.swing.*;
import java.awt.event.*;

public class StoredPasswordsUI extends JFrame{
    private JTextField searchField;
    private JList<PasswordDataClass> listView;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton generateButton;
    private JTextField descriptionField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JPanel viewPanel;
    private JButton deselectButton;

    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public StoredPasswordsUI(){
        generateListView();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                if(listView.isSelectionEmpty()) {
                    PasswordDataClass passwordDataClass = new PasswordDataClass();
                    passwordDataClass.setDescription(description);
                    passwordDataClass.setUsername(username);
                    passwordDataClass.setPassword(password);

                    databaseHandler.addEntry(passwordDataClass);
                }
                else {
                    PasswordDataClass update = listView.getSelectedValue();

                    update.setDescription(description);
                    update.setUsername(username);
                    update.setPassword(password);

                    databaseHandler.updateEntry(update);

                }

                descriptionField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                generateListView();
            }
        });

        listView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                PasswordDataClass selected = listView.getSelectedValue();

                for (PasswordDataClass entry: databaseHandler.fetchAll()){
                    if (selected.equals(entry)){
                        descriptionField.setText(selected.getDescription());
                        usernameField.setText(selected.getUsername());
                        passwordField.setText(selected.getPassword());
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PasswordDataClass selected = listView.getSelectedValue();
                    databaseHandler.deleteEntry(selected);

                    descriptionField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");

                    generateListView();
                }catch(Exception exception){
                    JOptionPane.showMessageDialog(null, "Please select an item");
                }
            }
        });

        deselectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listView.clearSelection();
                descriptionField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                DefaultListModel<PasswordDataClass> defaultListModel = new DefaultListModel<PasswordDataClass>();

                for (PasswordDataClass entry: databaseHandler.fetchAll()){
                    if (entry.getDescription().toLowerCase().contains(searchField.getText().toLowerCase())){
                        defaultListModel.addElement(entry);
                    }
                }
                listView.setModel(defaultListModel);
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.setText(
                        new PasswordGenerator().generateRandomString()
                );
            }
        });
    }

    public void generateListView(){
        DefaultListModel<PasswordDataClass> defaultListModel = new DefaultListModel<PasswordDataClass>();

        for (PasswordDataClass entry: databaseHandler.fetchAll()){
            defaultListModel.addElement(entry);
        }

        listView.setModel(defaultListModel);
    }

    public JPanel getViewPanel(){
        return viewPanel;
    }
}