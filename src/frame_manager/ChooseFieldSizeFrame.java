package frame_manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 13.12.2015.
 */
public class ChooseFieldSizeFrame extends JFrame implements ActionListener {
    public static final String ENTER_NUMBER_OF_ROWS = "Enter number of rows: ";
    public static final String ENTER_NUMBER_OF_COLUMNS = "Enter number of columns: ";
    public static final String OK = "OK!";
    private JLabel rowCountLabel;
    private JLabel columnCountLabel;
    private JTextField rowCountField;
    private JTextField columnCountField;
    private JButton okButton;

    public ChooseFieldSizeFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        createUI();
        okButton.addActionListener(this);
    }

    private void createUI() {
        Panel mainPanel = new Panel(new BorderLayout());
        Panel rowPanel = new Panel(new BorderLayout());
        Panel columnPanel = new Panel(new BorderLayout());
        rowCountField = new JTextField();
        columnCountField = new JTextField();
        rowCountLabel = new JLabel(ENTER_NUMBER_OF_ROWS);
        columnCountLabel = new JLabel(ENTER_NUMBER_OF_COLUMNS);
        okButton = new JButton(OK);

        rowPanel.add(rowCountLabel, BorderLayout.NORTH);
        rowPanel.add(rowCountField, BorderLayout.SOUTH);
        columnPanel.add(columnCountLabel, BorderLayout.NORTH);
        columnPanel.add(columnCountField, BorderLayout.SOUTH);

        mainPanel.add(rowPanel, BorderLayout.NORTH);
        mainPanel.add(columnPanel, BorderLayout.CENTER);
        mainPanel.add(okButton, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.pack();
    }


    public static void main(String[] args) {
        ChooseFieldSizeFrame frame = new ChooseFieldSizeFrame();
        frame.setVisible(true);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int rowCount = Integer.parseInt(rowCountField.getText());
            int columnCount = Integer.parseInt(columnCountField.getText());
            this.setVisible(false);
            MainFieldFrame frame = new MainFieldFrame(rowCount, columnCount);

        } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(this, "Incorrect input. Values must be integers.");
        }
    }
}
