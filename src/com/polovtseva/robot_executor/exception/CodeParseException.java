package com.polovtseva.robot_executor.exception;

import com.polovtseva.robot_executor.command.ExpressionCommand;
import com.polovtseva.robot_executor.command.Lexer;
import com.polovtseva.robot_executor.entity.Token;

/**
 * Created by User on 26.01.2016.
 */
public class CodeParseException extends ExpressionCommand {
    public CodeParseException() {
        super();
    }

    @Override
    public Token getFirstOperand() {
        return super.getFirstOperand();
    }

    @Override
    public void setFirstOperand(Token firstOperand) {
        super.setFirstOperand(firstOperand);
    }

    @Override
    public Token getSecondOperand() {
        return super.getSecondOperand();
    }

    @Override
    public void setSecondOperand(Token secondOperand) {
        super.setSecondOperand(secondOperand);
    }

    @Override
    public Lexer.LexerValue getOperation() {
        return super.getOperation();
    }

    @Override
    public void setOperation(Lexer.LexerValue operation) {
        super.setOperation(operation);
    }
}
