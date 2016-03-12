package com.polovtseva.robot_executor.logic;

import com.polovtseva.robot_executor.action.ExpressionAction;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.entity.Token;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.HashMap;

/**
 * Created by User on 26.01.2016.
 */
public class ExpressionCommand implements Command {
    private Token firstOperand;
    private Token secondOperand;
    private Token thirdOperand;
    private Lexer.LexerValue operation;
    private ExpressionType type;

    public ExpressionCommand() {
    }

    public Token getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(Token firstOperand) {
        this.firstOperand = firstOperand;
    }

    public Token getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(Token secondOperand) {
        this.secondOperand = secondOperand;
    }

    public Lexer.LexerValue getOperation() {
        return operation;
    }

    public void setOperation(Lexer.LexerValue operation) {
        this.operation = operation;
    }

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }

    public Token getThirdOperand() {
        return thirdOperand;
    }

    public void setThirdOperand(Token thirdOperand) {
        this.thirdOperand = thirdOperand;
    }

    @Override
    public boolean execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException {
        switch (getType()) {
            case GO:
                if (!robot.move()) {
                    throw new CodeExecutionException("Robot can't go there.");
                }
                break;
            case TURN_LEFT:
                robot.turnLeft();
                break;
            case TURN_RIGHT:
                robot.turnRight();
                break;
            case EOF:
                return false; // TODO: 27.01.2016
            case EQUAL:
                ExpressionAction.countEqualExpression(this, ids);
                break;
            case BOOLEAN_TYPE:
                ExpressionAction.countBooleanExpression(this, ids);
                break;
            case CHECK:
                return robot.check();
        }
        return true;
    }
}
