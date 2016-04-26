package com.polovtseva.robot_executor.entity;

import com.polovtseva.robot_executor.controller.Controller;

/**
 * Created by User on 24.10.2015.
 */
public class Robot {
    private Field field;

    private boolean changedDirection = false;

    public enum Direction {NORTH, SOUTH, WEST, EAST}

    private Direction direction = Direction.SOUTH;

    public boolean hasChangedDirection() {
        return changedDirection;
    }

    public void setChangedDirection(boolean changedDirection) {
        this.changedDirection = changedDirection;
    }

    public Robot(Field field) {
        this.field = field;
    }

    public boolean move() {
        System.out.println("Robot moved");
        int row = countNewRowIndex();
        int column = countNewColumnIndex();
        if (coordinatesAreValid(row, column) && !field.isMarked(row, column)) {
            field.setRobotColumn(column);
            field.setRobotRow(row);
            Controller.getInstance().getFrame().refreshTable();
            return true;
        } else {
            return false;
        }

    }

    public void turnLeft() {
        changedDirection = true;
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
        }
    }

    public void turnRight() {
        changedDirection = true;
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
        }
    }

    private boolean coordinatesAreValid(int row, int column) {
        if (row >= 0 && row < field.getRowCount() && column >= 0 && column < field.getColumnCount())
            return true;
        return false;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean check() {
        int row = countNewRowIndex();
        int column = countNewColumnIndex();
        if (coordinatesAreValid(row, column) && !field.isMarked(row, column)) {
            return true;
        }
        return false;
    }

    private int countNewRowIndex() {
        int row = field.getRobotRow();
        switch (direction) {
            case NORTH:
                row -= 1;
                break;
            case SOUTH:
                row += 1;
                break;
        }
        return row;
    }

    private int countNewColumnIndex() {
        int column = field.getRobotColumn();
        switch (direction) {
            case WEST:
                column -= 1;
                break;
            case EAST:
                column += 1;
                break;
        }
        return column;
    }
}
