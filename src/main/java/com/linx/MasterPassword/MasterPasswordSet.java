package com.linx.MasterPassword;

import com.linx.DatabaseHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class MasterPasswordSet extends JFrame {
    private JButton submitButton;
    private JPasswordField enterPassword;
    private JPasswordField confirmPassword;
    private JPanel setterPanel;

    private static final DatabaseHandler databaseHandler = new DatabaseHandler();
    private boolean flag = false;

    private MasterPasswordSet() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enterPwd = String.valueOf(enterPassword.getPassword());
                String confPwd = String.valueOf(confirmPassword.getPassword());

                try {
                    if (enterPwd.equals(confPwd)) {
                        databaseHandler.createMasterPassword(enterPwd);

                        // TODO

                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords do not match");
                        enterPassword.setText("");
                        confirmPassword.setText("");
                    }
                } catch (NoSuchAlgorithmException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public boolean masterPasswordSetter(){
        MasterPasswordSet masterPasswordSet = new MasterPasswordSet();
        JDialog dialog = new JDialog(masterPasswordSet, "Master Password", true); // create a modal dialog
        dialog.setContentPane(masterPasswordSet.getSetterPanel());
        dialog.setSize(300, 150);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        return flag;
    }

    public JPanel getSetterPanel(){
        return setterPanel;
    }

//    public static void main(String[] args){
//        MasterPasswordSet masterPasswordSet = new MasterPasswordSet();
//        boolean flag = masterPasswordSet.masterPasswordSetter();
//
//        System.out.println(flag);
//
//        if (! flag){
//            System.exit(0);
//        }
//
//    }
}
