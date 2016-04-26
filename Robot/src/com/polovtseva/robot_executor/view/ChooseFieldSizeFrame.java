package com.polovtseva.robot_executor.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 13.12.2015.
 */
public class ChooseFieldSizeFrame extends JDialog implements ActionListener {
    public static final String ENTER_NUMBER_OF_ROWS = "Enter number of rows: ";
    public static final String ENTER_NUMBER_OF_COLUMNS = "Enter number of columns: ";
    public static final String OK = "OK!";
    private JLabel rowCountLabel;
    private JLabel columnCountLabel;
    private JTextField rowCountField;
    private JTextField columnCountField;
    private JButton okButton;
    private int rowCount;
    private int columnCount;

    public ChooseFieldSizeFrame() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //this.setLocationRelativeTo(null);
        createUI();
        okButton.addActionListener(this);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(ImageWorker.APPLICATION_ICON)));
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rowCountField = new JTextField();
        columnCountField = new JTextField();
        rowCountLabel = new JLabel(ENTER_NUMBER_OF_ROWS);
        columnCountLabel = new JLabel(ENTER_NUMBER_OF_COLUMNS);
        okButton = new JButton(OK);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        columnCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowCountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        columnCountField.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(rowCountLabel);
        mainPanel.add(rowCountField);
        mainPanel.add(columnCountLabel);
        mainPanel.add(columnCountField);
        mainPanel.add(okButton);

        this.add(mainPanel);
        this.pack();
    }


    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            rowCount = Integer.parseInt(rowCountField.getText());
            columnCount = Integer.parseInt(columnCountField.getText());
            if (rowCount < 21 && rowCount > 0 && columnCount < 21 && columnCount > 0) {
                //MainFieldFrame frame = new MainFieldFrame(rowCount, columnCount);
                //frame.setVisible(true);

                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect input. Values must be from 1 to 20.");
            }

        } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(this, "Incorrect input. Values must be integers.");
        }
    }
}
