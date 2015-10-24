package frame_manager;

import entities.*;
import entities.Robot;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by User on 20.10.2015.
 */
public class MainFieldFrame extends JFrame {
    ImageIcon robotImage = new ImageIcon("robot.jpg");
    private int rowHeight;
    private int columnWidth;
    private JLabel label;
    private JTable table;
    private JTextArea codePane;
    private JButton executeCodeButton;
    private JScrollPane tableScrollPane;
    private JScrollPane codeScrollPane;
    private int rowCount;
    private int columnCount;


    private Field field;
    private Robot robot;

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
        WIDTH = (int)screenSize.getWidth();
        HEIGHT = (int)screenSize.getHeight() - 30;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        createCodePane();
        createTable();


        this.setJMenuBar(menuBar);
        this.add(codeScrollPane, BorderLayout.WEST);
        this.add(tableScrollPane, BorderLayout.CENTER);

        this.setSize((int) WIDTH, (int) HEIGHT);
        scaleRobotImage();
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
                    label.setBackground(Color.GREEN);
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
        rowHeight = (HEIGHT - 65) / rowCount;
        columnWidth = (int) ((HEIGHT - codeScrollPane.getWidth()) / columnCount);

        table.setRowHeight(rowHeight);
    }

    public void createCodePane() {
        codePane = new JTextArea(5, 20);
        codePane.setSize(100, 100);
        codeScrollPane = new JScrollPane(codePane);
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
        rowHeight = (this.getHeight() - 65) / rowCount;
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

    public static void main(String[] args) {
        final MainFieldFrame frame = new MainFieldFrame(5, 5);
    }


}
