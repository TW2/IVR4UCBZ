package org.wingate.ivr;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * IVR is free for all, and you are free to fork this code or make a new one
 * I just want you to put a link or a thank in your app
 * <a href="https://github.com/TW2/IVR4UCBZ">IVR by TW2</a>
 * @author The Wingate 2940
 */
public class IVR {
    public static final ResourceBundle RSX = ResourceBundle.getBundle("LNG");
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            FlatDarkLaf.setup();
            MainFrame mf = new MainFrame();
            mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mf.setTitle("IVR by TW2 - https://github.com/TW2/IVR4UCBZ");
            mf.setSize(1900, 1000);
            mf.setLocationRelativeTo(null);
            mf.setVisible(true);
        });
    }
}
