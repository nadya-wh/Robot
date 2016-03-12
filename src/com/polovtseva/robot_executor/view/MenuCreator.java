package com.polovtseva.robot_executor.view;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.entity.Field;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by User on 24.10.2015.
 */
public class MenuCreator {
    private MainFieldFrame parent;

    public MenuCreator(MainFieldFrame parent) {
        this.parent = parent;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createOpenMenu());
        menuBar.add(createSaveMenu());
        return menuBar;
    }

    private JMenu createSaveMenu() {
        JMenu saveMenu = new JMenu("Save");

        JMenuItem saveFieldItem = new JMenuItem("Save Field");
        saveFieldItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save");
                int retVal = fileChooser.showOpenDialog(parent);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Controller.getInstance().getField().write(file);
                    } catch (IOException e1) {
                        JOptionPane.showInputDialog(parent, "Couldn't write to file");
                    }
                }
            }
        });
        saveMenu.add(saveFieldItem);
        return saveMenu;
    }

    private JMenu createOpenMenu() {
        JMenu openMenu = new JMenu("Open");

        JMenuItem openFieldItem = new JMenuItem("Open and load field");
        openFieldItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setName("Open");
                int retVal = fileChooser.showOpenDialog(parent);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Field field = null;
                    try {
                        field = Controller.getInstance().getField().read(file);
                        if (field != null) {
                            parent.setVisible(false);
                            MainFieldFrame newFrame = new MainFieldFrame(field, parent.getCode());
                            newFrame.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(parent, "Invalid file.");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        openMenu.add(openFieldItem);
        return openMenu;
    }
}
