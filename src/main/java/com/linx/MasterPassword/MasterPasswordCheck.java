package com.linx.MasterPassword;

import com.linx.DatabaseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class MasterPasswordCheck extends JFrame{
    private JPasswordField enterPassword;
    private JPanel checkerPanel;
    private JButton submitButton;

    private boolean flag = false;

    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    private MasterPasswordCheck() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (databaseHandler.checkMasterPassword(String.valueOf(enterPassword.getPassword()))){

                        // TODO

                        Window window = SwingUtilities.getWindowAncestor(submitButton);
                        if (window instanceof JDialog){
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Invalid password");
                        enterPassword.setText("");

                    }
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    public boolean masterPasswordChecker(){
        MasterPasswordCheck masterPasswordCheck = new MasterPasswordCheck();
        JDialog dialog = new JDialog(masterPasswordCheck, "Title", true); // create a modal dialog
        dialog.setContentPane(masterPasswordCheck.getCheckerPanel());
        dialog.setSize(200, 120);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // exit the dialog on close
        dialog.setVisible(true); // show the dialog

        return flag;
    }

    public JPanel getCheckerPanel(){
        return checkerPanel;
    }

//    public static void main(String[] args){
//        MasterPasswordCheck masterPasswordCheck = new MasterPasswordCheck();
//        boolean flag = masterPasswordCheck.masterPasswordChecker();
//        System.out.println(flag);
//
//        if (! flag){
//            System.exit(0);
//        }
//
//    }

}
