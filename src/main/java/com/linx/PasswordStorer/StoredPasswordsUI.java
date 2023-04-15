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
        // fetch list on launch
        generateListView();

        /**
         * event listener for save button
         * called when the button is clicked
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get string values input into the text boxes
                String description = descriptionField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                if(listView.isSelectionEmpty()) {
                    // if no element has been selected in the list consider this as a new entry
                    PasswordDataClass passwordDataClass = new PasswordDataClass();
                    passwordDataClass.setDescription(description);
                    passwordDataClass.setUsername(username);
                    passwordDataClass.setPassword(password);

                    databaseHandler.addEntry(passwordDataClass);
                }
                else {
                    // list element is selected so update the value in the database
                    PasswordDataClass update = listView.getSelectedValue();

                    update.setDescription(description);
                    update.setUsername(username);
                    update.setPassword(password);

                    databaseHandler.updateEntry(update);

                }

                // on successful update clear the text fields and refresh the list again to get updated values
                descriptionField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                generateListView();
            }
        });

        /**
         * event listener for listView
         * called when an element in the list is clicked
         */
        listView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // get the object matching the selected entry
                PasswordDataClass selected = listView.getSelectedValue();

                for (PasswordDataClass entry: databaseHandler.fetchAll()){
                    // check for it's matching entry in the database
                    // on successful match set the text fields with its values
                    if (selected.equals(entry)){
                        descriptionField.setText(selected.getDescription());
                        usernameField.setText(selected.getUsername());
                        passwordField.setText(selected.getPassword());
                    }
                }
            }
        });

        /**
         * event listener for delete button
         * called when the button is clicked
         */
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // get the object matching to the selected entry
                    // and delete the entry matching with this object
                    PasswordDataClass selected = listView.getSelectedValue();
                    databaseHandler.deleteEntry(selected);

                    // after deletion set clear the text fields and regenerate the list view to match the updates
                    descriptionField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    generateListView();
                }catch(Exception exception){
                    JOptionPane.showMessageDialog(null, "Please select an item");
                }
            }
        });

        /**
         * event listener for the deselect button.
         * called when the button is clicked
         */
        deselectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deselect any selected list item and clear the text boxes
                listView.clearSelection();
                descriptionField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        /**
         * event listener for the text field
         * called every time a key has been released
         */
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                // similar to the generateListView() method except in this case it is filtered
                // using the user provided text

                DefaultListModel<PasswordDataClass> defaultListModel = new DefaultListModel<PasswordDataClass>();

                for (PasswordDataClass entry: databaseHandler.fetchAll()){
                    if (entry.getDescription().toLowerCase().contains(searchField.getText().toLowerCase())){
                        defaultListModel.addElement(entry);
                    }
                }
                listView.setModel(defaultListModel);
            }
        });

        /**
         * event listener for generate button
         * called when the button gets clicked
         */
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // calls the PasswordGenerator which returns a randomly generated string and applies it to the passwordField
                passwordField.setText(
                        new PasswordGenerator().generateRandomString()
                );
            }
        });
    }

    /**
     * Obtains an ArrayList of password entries from the database and attaches it to the jlist view
     */
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