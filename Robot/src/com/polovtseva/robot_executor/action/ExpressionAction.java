package com.polovtseva.robot_executor.action;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.command.ExpressionCommand;
import com.polovtseva.robot_executor.lexer.Lexer;
import com.polovtseva.robot_executor.entity.Token;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.HashMap;

/**
 * Created by User on 26.01.2016.
 */
public class ExpressionAction {
    /*
    a = a + b
    a = 5
    a = b
    a = b + 5
    a = 5 + b
     */
    public static void countAssignmentExpression(ExpressionCommand expressionCommand, HashMap<Integer, Integer> ids) throws CodeExecutionException {
        if (expressionCommand.getThirdOperand() != null) {
            int firstOp = takeOperandValue(expressionCommand.getSecondOperand(), ids);
            int secOp = takeOperandValue(expressionCommand.getThirdOperand(), ids);
            int result = 0;
            switch (expressionCommand.getOperation()) {
                case PLUS:
                    result = firstOp + secOp;
                    break;
                case MINUS:
                    result = firstOp - secOp;
                    break;
                case BIWISE_AND:
                    result = firstOp & secOp;
                    break;
                case BITWISE_OR:
                    result = firstOp | secOp;
                    break;
            }
            ids.put(expressionCommand.getFirstOperand().getValue(), result);
        } else if (expressionCommand.getSecondOperand() != null &&
                expressionCommand.getSecondOperand().getType() == Lexer.LexerValue.NUM) { //b = 5
            ids.put(expressionCommand.getFirstOperand().getValue(), expressionCommand.getSecondOperand().getValue());
        } else if (expressionCommand.getSecondOperand() != null &&
                expressionCommand.getSecondOperand().getType() == Lexer.LexerValue.ID) {
            if (ids.containsKey(expressionCommand.getSecondOperand().getValue())) {
                //b = c
                int val = ids.get(expressionCommand.getSecondOperand().getValue());
                ids.put(expressionCommand.getSecondOperand().getValue(), val);
            } else {
                throw new CodeExecutionException("No such variable: " +
                        (char) expressionCommand.getSecondOperand().getValue());
            }
        }
    }

    public static int takeOperandValue(Token token, HashMap<Integer, Integer> ids) throws CodeExecutionException {
        int value = 0;
        if (token.getType() == Lexer.LexerValue.ID) {
            if (ids.containsKey(token.getValue())) {
                value = ids.get(token.getValue());
            } else {
                throw new CodeExecutionException("No such variable: " + (char) token.getValue());
            }
        } else if (token.getType() == Lexer.LexerValue.NUM) {
            value = token.getValue();
        } else {
            throw new CodeExecutionException("Not supportable type.");
        }
        return value;
    }

    public static boolean countBooleanExpression(ExpressionCommand expressionCommand, HashMap<Integer, Integer> ids) throws CodeExecutionException {
        int firstOp = 0;
        int secondOp = 0;
        switch (expressionCommand.getOperation()) {
            case CHECK:
                return Controller.getInstance().getRobot().check();
            case MORE:
                firstOp = takeOperandValue(expressionCommand.getFirstOperand(), ids);
                secondOp = takeOperandValue(expressionCommand.getSecondOperand(), ids);
                return firstOp > secondOp;
            case LESS:
                firstOp = takeOperandValue(expressionCommand.getFirstOperand(), ids);
                secondOp = takeOperandValue(expressionCommand.getSecondOperand(), ids);
                return firstOp < secondOp;
            case EQUAL:
                firstOp = takeOperandValue(expressionCommand.getFirstOperand(), ids);
                secondOp = takeOperandValue(expressionCommand.getSecondOperand(), ids);
                return firstOp == secondOp;

        }
        return false; // TODO: 27.01.2016  
    }

}