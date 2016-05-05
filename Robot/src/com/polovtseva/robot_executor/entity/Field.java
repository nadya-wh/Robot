package com.polovtseva.robot_executor.entity;

import java.io.*;

/**
 * Field.
 */
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    private int rowCount;
    private int columnCount;
    private int robotRow;
    private int robotColumn;

    private boolean[][] field;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        field = new boolean[rowCount][columnCount];
        robotColumn = 0;
        robotRow = 0;
    }

    private void setEmpty() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                field[i][j] = false;
            }
        }
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getRobotRow() {
        return robotRow;
    }

    public int getRobotColumn() {
        return robotColumn;
    }

    public void changeCell(int row, int column) {
        field[row][column] = !field[row][column];
    }

    public boolean isMarked(int row, int column) {
        return field[row][column];
    }

    public void setRobotColumn(int robotColumn) {
        this.robotColumn = robotColumn;
    }

    public void setRobotRow(int robotRow) {
        this.robotRow = robotRow;
    }

    public void write(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        fos.close();
        oos.close();

    }

    public Field read(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return  (Field) ois.readObject();
    }


}