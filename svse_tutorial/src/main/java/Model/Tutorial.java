package Model;

import View.GUI;

import View.GUI;

import javax.swing.*;

public class Tutorial {
    public static void main(String args[]) {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

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


