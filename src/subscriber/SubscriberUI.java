/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package subscriber;

import remote.OutputHandler;
import remote.ServiceRemote;
import remote.SubscriberRemote;
import remote.outputdata.TextAreaOutput;
import service.Service;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 *
 * @author shadow
 */
public class SubscriberUI extends javax.swing.JFrame {
    ServiceRemote service;
    String name;
    SubscriberRemote subscriberRemote;
    Map<String, Boolean> notificationOptions;
    /**
     * Creates new form SubscriberUI
     */
    public SubscriberUI() {
        initComponents();
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", Service.PORT);
            service = (ServiceRemote) registry.lookup("service");
            subscriberRemote = new SubscriberRemoteImpl(new TextAreaOutput(notificationArea), new UpdateNotificationOptionsHandler());

            registerUser();
            retrieveSubscriptions();
            updateNotificationOptionsList();
            jLabel3.setText("Welcome " + name);
            service.resendMissedNotifications(name);
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
    }

    private class UpdateNotificationOptionsHandler implements OutputHandler {

        @Override
        public void output(String output) {
            try {
                retrieveSubscriptions();
                updateNotificationOptionsList();
            } catch (RemoteException e) {
                System.out.println("Unable to update: " + e.getMessage());
            }
        }
    }

    private void registerUser() throws RemoteException{
        do {
            name = JOptionPane.showInputDialog(this, "Enter your username", "Register Subscriber", JOptionPane.PLAIN_MESSAGE);
        } while (name == null || name.isEmpty());
        service.registerSubscriber(name.trim().toLowerCase(), subscriberRemote);
    }

    private void retrieveSubscriptions() throws RemoteException {
        ArrayList<String> allTopics = service.getAvailableTopics();
        System.out.println("All topics: " + allTopics);
        Set<String> subscribedTopics = service.getSubscribedTopics(name);
        System.out.println("Subscribed topics: " + subscribedTopics);
        if (notificationOptions != null)
            notificationOptions.clear();
        else
            notificationOptions = new HashMap<>();

        for (String topic : allTopics) {
            if (subscribedTopics.contains(topic))
                notificationOptions.put(topic, true);
            else
                notificationOptions.put(topic, false);
        }
    }

    private void updateNotificationOptionsList() throws RemoteException {
        notificationOptionList.removeAll();
        for (var entry : notificationOptions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            JCheckBox checkBox = new JCheckBox(entry.getKey());
            checkBox.setSelected(entry.getValue());
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        service.subscribe(name, entry.getKey());
                        service.resendMissedNotifications(name);
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(this, "Unable to subscribe to " + entry.getKey() + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ((JCheckBox) e.getSource()).setSelected(false);
                    }
                } else {
                    try {
                        service.unsubscribe(name, entry.getKey());
                        service.resendMissedNotifications(name);
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(this, "Unable to unsubscribe to " + entry.getKey() + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ((JCheckBox) e.getSource()).setSelected(true);
                    }

                }
            });

            notificationOptionList.add(checkBox);
        }

        // These three lines are crucial to update the UI
        notificationOptionList.revalidate();
        notificationOptionList.repaint();
        jScrollPane1.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notificationOptionList = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notificationArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("List of Notifications");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(9, 15, 9, 10);
        getContentPane().add(jLabel1, gridBagConstraints);

        notificationOptionList.setLayout(new javax.swing.BoxLayout(notificationOptionList, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(notificationOptionList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 15, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        notificationArea.setColumns(20);
        notificationArea.setRows(5);
        jScrollPane2.setViewportView(notificationArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 15, 15);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setText("New Notifications");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 15);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel3.setText("Welcome Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 15);
        getContentPane().add(jLabel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SubscriberUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubscriberUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubscriberUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubscriberUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubscriberUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea notificationArea;
    private javax.swing.JPanel notificationOptionList;
    // End of variables declaration//GEN-END:variables
}
