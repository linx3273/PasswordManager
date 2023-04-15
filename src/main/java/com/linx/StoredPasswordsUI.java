package com.linx;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public StoredPasswordsUI(){
        generateListView();
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

                    generateListView();
                }catch(Exception exception){
                    JOptionPane.showMessageDialog(null, "Please select an item");
                }
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

