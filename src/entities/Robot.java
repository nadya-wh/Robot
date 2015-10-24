package entities;

/**
 * Created by User on 24.10.2015.
 */
public class Robot {
    private Field field;

    public enum Direction {NORTH, SOUTH, WEST, EAST}

    private Direction direction = Direction.SOUTH;

    public Robot(Field field) {
        this.field = field;
    }

    public boolean move() {
        int row = field.getRobotRow();
        int column = field.getRobotColumn();
        switch (direction) {
            case NORTH:
                row -= 1;
                break;
            case SOUTH:
                row += 1;
                break;
            case WEST:
                column -= 1;
                break;
            case EAST:
                column += 1;
                break;
        }
        if (coordinatesAreValid(row, column) && !field.isMarked(row, column)) {
            field.setRobotColumn(column);
            field.setRobotRow(row);
            return true;
        } else {
            return false;
        }
    }

    public void turnLeft() {
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

}
