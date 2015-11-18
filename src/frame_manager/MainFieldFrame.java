package frame_manager;

import entities.*;
import entities.Robot;
import logic.CodeExecutor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by User on 20.10.2015.
 */
public class MainFieldFrame extends JFrame {
    public static final String RUN_ICON_PATH = "run_icon.png";
    public static final String STOP_ICON_PATH = "stop.png";
    public static final String ROBOT_ICON_PATH = "robot.jpg";
    private ImageIcon robotImage = new ImageIcon(ROBOT_ICON_PATH);
    private int rowHeight;
    private int columnWidth;
    private JLabel label;
    private JTable table;
    private JTextArea codePane;
    private JButton executeCodeButton;
    private JButton stopExecuteCodeButton;
    private JScrollPane tableScrollPane;
    private JScrollPane codeScrollPane;
    private JPanel buttonPanel;
    private int rowCount;
    private int columnCount;
    private int CONST = 95;
    private int upHeight;

    private Field field;
    private Robot robot;

    private boolean isExecuting = false;
    private CodeExecutor codeExecutor;

    public static int WIDTH;
    public static int HEIGHT;

    public MainFieldFrame(int rowCount, int columnCount) {
        field = new Field(rowCount, columnCount);
        robot = new Robot(field);
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        createUI();
    }

    public MainFieldFrame(Field field, String code) {
        this.field = field;
        robot = new Robot(field);
        rowCount = field.getRowCount();
        columnCount = field.getColumnCount();
        createUI();
        codePane.setText(code);
    }

    public void createUI() {
        JMenuBar menuBar = new MenuCreator(this).createMenuBar();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        createButtonPanel();
        upHeight = menuBar.getHeight() + buttonPanel.getHeight();
        WIDTH = (int)screenSize.getWidth();
        HEIGHT = (int)screenSize.getHeight() - buttonPanel.getHeight() - menuBar.getHeight() - 30;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        createCodePane();
        //label = new JLabel();
        scaleRobotImage();
        createTable();


        this.setJMenuBar(menuBar);

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(codeScrollPane, BorderLayout.WEST);
        this.add(tableScrollPane, BorderLayout.CENTER);

        this.setSize((int) WIDTH, (int) HEIGHT);
        //scaleRobotImage();
        refreshTable();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        this.setVisible(true);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                refreshTable();
                scaleRobotImage();
            }
        });
    }

    public void createTable() {
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
        tableScrollPane = new JScrollPane();
        DefaultTableModel defaultTableModel = new DefaultTableModel(rowCount, columnCount);
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
                if (e.getClickCount() == 2) {
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
        rowHeight = (HEIGHT - CONST) / rowCount;
        //columnWidth = (int) ((HEIGHT - codeScrollPane.getWidth()) / columnCount);
        //columnWidth = (int) (HEIGHT / columnCount) - 100;

        table.setRowHeight(rowHeight);
    }

    public void createCodePane() {
        codePane = new JTextArea(5, 20);
        codePane.setSize(100, 100);
        codeScrollPane = new JScrollPane(codePane);
    }

    public void createButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout());
        executeCodeButton = new JButton(new ImageIcon(RUN_ICON_PATH));
        stopExecuteCodeButton = new JButton(new ImageIcon(STOP_ICON_PATH));
        makeBeautifulButton(executeCodeButton);
        makeBeautifulButton(stopExecuteCodeButton);
        buttonPanel.add(executeCodeButton);
        buttonPanel.add(stopExecuteCodeButton);
        executeCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isExecuting) {
                    isExecuting = true;
                    codeExecutor = new CodeExecutor(getCode(), robot);
                    setCodePaneNotEditable();
                    codeExecutor.execute();
                } else {
                    codeExecutor.execute();
                }
                refreshTable();
            }
        });

        stopExecuteCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isExecuting = false;
                field.setRobotColumn(0);
                field.setRobotRow(0);
                setCodePaneEditable();
                refreshTable();
            }
        });
    }

    public void makeBeautifulButton(JButton button) {
        button.setBackground(Color.LIGHT_GRAY);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusPainted(false);
    }

    public Field getField() {
        return field;
    }

    //scale picture
    public void scaleRobotImage() {
        BufferedImage bi = new BufferedImage(robotImage.getIconWidth(), robotImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        if (columnWidth > 0) {
            g.drawImage(robotImage.getImage(), 0, 0, columnWidth, rowHeight, null);
            Image scaledRobotImage = robotImage.getImage().getScaledInstance(columnWidth, rowHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledRobot = new ImageIcon(scaledRobotImage);
            label = new JLabel(scaledRobot);
        }
    }

    public void refreshTable() {
        rowHeight = (this.getHeight() - upHeight - CONST) / rowCount;
        if(rowHeight > 1) {
            columnWidth = ((this.getHeight() - codeScrollPane.getWidth()) / columnCount);
            table.setRowHeight(rowHeight);
        }
    }

    public String getCode() {
        return codePane.getText();
    }

    public void setCodePaneNotEditable() {
        codePane.setEditable(false);
    }

    public void setCodePaneEditable() {
        codePane.setEditable(true);
    }

    public static void main(String[] args) {
        final MainFieldFrame frame = new MainFieldFrame(5, 5);
    }


}
