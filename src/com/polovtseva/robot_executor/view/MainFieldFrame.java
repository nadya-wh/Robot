package com.polovtseva.robot_executor.view;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.entity.*;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.util.ResizeAdapter;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by User on 20.10.2015.
 */
public class MainFieldFrame extends JFrame {

    private static final Logger LOG = Logger.getLogger(MainFieldFrame.class);

    public static final int LOG_PANE_HEIGHT;

    private static final DefaultStyledDocument document = new DefaultStyledDocument();
    private int rowHeight;
    private int columnWidth;
    private final int CONST = 95;
    private int upHeight;

    private ImageIcon robotImage;
    private JLabel label;
    private JTable table;
    private JTextArea codePane;
    private JButton executeCodeButton;
    private JButton executeAllButton;
    private JButton stopExecuteCodeButton;
    private JScrollPane tableScrollPane;
    private JScrollPane codeScrollPane;
    private JScrollPane logScrollPane;
    private JTextPane logPane;
    private JPanel buttonPanel;

    public static int WIDTH;
    public static int HEIGHT;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LOG_PANE_HEIGHT = screenSize.height / 4;
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight() - 30;
    }

    public MainFieldFrame() {
        createUI();
    }

    public MainFieldFrame(String code) {
        createUI();
        codePane.setText(code);
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public ImageIcon getRobotImage() {
        return robotImage;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public String getCode() {
        return codePane.getText();
    }

    public void setCode(String code) {
        codePane.setText(code);
    }

    public void setCodePaneNotEditable() {
        codePane.setEditable(false);
    }

    public void setCodePaneEditable() {
        codePane.setEditable(true);
    }

    public void createUI() {
        robotImage = new ImageIcon(this.getClass().getResource(ImageWorker.ROBOT_IMAGES[ImageWorker.SOUTH]));
        JMenuBar menuBar = new MenuCreator(this).createMenuBar();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        createButtonPanel();
        upHeight = menuBar.getHeight() + buttonPanel.getHeight();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        createCodePane();
        createLogPanel();


        //label = ImageWorker.scaleRobotImage(robotImage, columnWidth, rowHeight);

        createTable();
        //table.setPreferredSize(new Dimension(tableScrollPane.getWidth(), tableScrollPane.getHeight()));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;

        this.setJMenuBar(menuBar);

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(codeScrollPane, BorderLayout.WEST);
        this.add(tableScrollPane, BorderLayout.CENTER);
        this.add(logScrollPane, BorderLayout.SOUTH);

        this.setSize(WIDTH, HEIGHT);
        refreshTable();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(ImageWorker.APPLICATION_ICON)));
        this.setVisible(true);
        this.addComponentListener(new ResizeAdapter(this));
    }

    public void createTable() {
        final Field field = Controller.getInstance().getField();
        table = new JTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
                Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);

                if (rowIndex == field.getRobotRow() && columnIndex == field.getRobotColumn()) {
                    return label;
                } else if (field.isMarked(rowIndex, columnIndex)) {
                    component.setBackground(Color.RED);
                } else {
                    component.setBackground(Color.GREEN);
                }
//                int rendererWidth = component.getPreferredSize().width;
//                TableColumn tableColumn = getColumnModel().getColumn(columnIndex);
//                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

                return component;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == field.getRobotColumn()) {
                    return JLabel.class;
                } else {
                    return Object.class;
                }
            }
        };
        DefaultTableModel defaultTableModel = new DefaultTableModel(field.getRowCount(), field.getColumnCount());
        table.setModel(defaultTableModel);
        table.setTableHeader(null);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2 && !Controller.getInstance().isExecuting()) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    field.changeCell(row, column);
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        tableScrollPane = new JScrollPane(table);

        rowHeight = (HEIGHT - CONST - 80) / field.getRowCount();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        rowHeight = table.getRowHeight();
        table.setRowHeight(rowHeight);
    }

    public void createLogPanel() {
        logPane = new JTextPane(document);
        logScrollPane = new JScrollPane(logPane);
        logScrollPane.setPreferredSize(new Dimension(this.getWidth(), LOG_PANE_HEIGHT));
    }

    public void createCodePane() {
        codePane = new JTextArea(5, 20);
        codePane.setColumns(30);
        codePane.setFont(new Font("Monospaced", Font.BOLD, 14));
        codeScrollPane = new JScrollPane(codePane);
    }


    public void createButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout());
        executeCodeButton = new JButton(new ImageIcon(this.getClass().getResource(ImageWorker.RUN_ICON_PATH)));
        stopExecuteCodeButton = new JButton(new ImageIcon(this.getClass().getResource(ImageWorker.STOP_ICON_PATH)));
        executeAllButton = new JButton(new ImageIcon(this.getClass().getResource(ImageWorker.PLAY_ALL_ICON_PATH)));
        makeBeautifulButton(executeCodeButton);
        makeBeautifulButton(stopExecuteCodeButton);
        makeBeautifulButton(executeAllButton);
        buttonPanel.add(executeCodeButton);
        //buttonPanel.add(executeAllButton);
        buttonPanel.add(stopExecuteCodeButton);

        executeCodeButton.addActionListener(Controller.getInstance().getCodeExecuteActionListener());

        stopExecuteCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopExecutingCode();
            }
        });
    }

    public void stopExecutingCode() {
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        StyleConstants.setForeground(style, Color.RED);
        try {
            document.insertString(0, "lal", style);
        } catch (BadLocationException e) {
            LOG.error(e);
        }
        logPane.setText(logPane.getText() + "The program has finished.\n");
        Field field = Controller.getInstance().getField();
        Robot robot = Controller.getInstance().getRobot();
        Controller.getInstance().setExecuting(false);
        field.setRobotColumn(0);
        field.setRobotRow(0);
        setCodePaneEditable();
        robot.setDirection(Robot.Direction.SOUTH);
        robot.setChangedDirection(true);
        refreshTable();
    }

    public void makeBeautifulButton(JButton button) {
        button.setBackground(Color.LIGHT_GRAY);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusPainted(false);
    }


    public void refreshTable() {
        Robot robot = Controller.getInstance().getRobot();
        Field field = Controller.getInstance().getField();
        rowHeight = (this.getHeight() - upHeight - CONST) / field.getRowCount();
        if (rowHeight > 1) {
            //for robot image
            columnWidth = ((this.getHeight() - codeScrollPane.getWidth()) / field.getColumnCount());
            table.setRowHeight(rowHeight);
        }

        if (robot.hasChangedDirection()) {
            robot.setChangedDirection(false);
            int direction = 0;
            switch (robot.getDirection()) {
                case NORTH:
                    direction = ImageWorker.NORTH;
                    break;
                case SOUTH:
                    direction = ImageWorker.SOUTH;
                    break;
                case WEST:
                    direction = ImageWorker.WEST;
                    break;
                case EAST:
                    direction = ImageWorker.EAST;
                    break;
            }
            robotImage = new ImageIcon(this.getClass().getResource(ImageWorker.ROBOT_IMAGES[direction]));
            label = ImageWorker.scaleRobotImage(robotImage, columnWidth, rowHeight);
        }

    }

    public JTextPane getLogPane() {
        return logPane;
    }
}
