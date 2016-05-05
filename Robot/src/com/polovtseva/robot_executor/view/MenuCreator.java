package com.polovtseva.robot_executor.view;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.entity.Field;
import com.polovtseva.robot_executor.util.FileUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by User on 24.10.2015.
 */
public class MenuCreator {

    static final Logger LOG = Logger.getLogger(MenuCreator.class);

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

        JMenuItem saveFieldItem = new JMenuItem("Field");
        JMenuItem saveSourceCode = new JMenuItem("Source code");

        saveFieldItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save");
                fileChooser.setApproveButtonText("Save");
                int retVal = fileChooser.showOpenDialog(parent);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Controller.getInstance().getField().write(file);
                    } catch (IOException e1) {
                        LOG.error(e);
                        JOptionPane.showInputDialog(parent, "Couldn't write to file");
                    }
                }
            }
        });
        saveSourceCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save");
                fileChooser.setApproveButtonText("Save");
                int retVal = fileChooser.showOpenDialog(parent);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    String code = Controller.getInstance().getFrame().getCode();
                    if (!FileUtil.writeFile(file, code)) {
                        JOptionPane.showInputDialog(parent, "Couldn't write to file");
                    }

                }
            }
        });

        saveMenu.add(saveFieldItem);
        saveMenu.add(saveSourceCode);
        return saveMenu;
    }

    private JMenu createOpenMenu() {
        JMenu openMenu = new JMenu("Open");

        JMenuItem openFieldItem = new JMenuItem("Field");
        JMenuItem openSourceCode = new JMenuItem("Source code");
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
                            MainFieldFrame newFrame = new MainFieldFrame(parent.getCode());
                            newFrame.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(parent, "Invalid file.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(parent, "Invalid file.", "Error", JOptionPane.ERROR_MESSAGE);
                        LOG.error(e);
                    }
                }
            }
        });
        openSourceCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setName("Open");
                int retVal = fileChooser.showOpenDialog(parent);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    String code = FileUtil.readFile(file);
                    Controller.getInstance().getFrame().setCode(code);
                }
            }
        });

        openMenu.add(openFieldItem);
        openMenu.add(openSourceCode);
        return openMenu;
    }
}
