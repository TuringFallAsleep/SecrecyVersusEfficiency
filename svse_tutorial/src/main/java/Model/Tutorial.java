package Model;

import View.GUI;

import View.GUI;

import javax.swing.*;

public class Tutorial {
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIManager.put("swing.boldMetal",Boolean.FALSE);
                GUI.createAndShowGUI();
            }
        });
//        GUI window = new GUI();
//        window.setVisible(true);

        //test jFreeChart




    } // main

}


