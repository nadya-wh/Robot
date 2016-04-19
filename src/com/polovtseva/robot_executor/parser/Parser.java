package com.polovtseva.robot_executor.parser;

import com.polovtseva.robot_executor.entity.*;
import com.polovtseva.robot_executor.exception.CodeExecutionException;
import com.polovtseva.robot_executor.command.*;

/**
 * Created by User on 20.10.2015.
 */
public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Command parse() throws CodeExecutionException {
        Command command = null;
        ExpressionCommand expressionCommand = new ExpressionCommand();
        lexer.nextToken();
        int step = 0;
        while (lexer.getToken().getType() != Lexer.LexerValue.SEMICOLON) {
            switch (lexer.getToken().getType()) {
                case ID:
                case NUM:
                    if (command == null || command instanceof ExpressionCommand) { //|| command instanceof ConditionalCommand
                        command = expressionCommand;
                        buildExpressionCommand(expressionCommand, step);
                        step++;
                    } else if (command instanceof OutputCommand) {
                        OutputCommand outputCommand = (OutputCommand) command;
                        outputCommand.setOperand(new Token(lexer.getToken().getValue(), lexer.getToken().getType()));
                    } else if (command instanceof ConditionalCommand) {
                        ExpressionCommand current = ((ConditionalCommand) command).getExpressionCommand();
                        buildExpressionCommand(current, step);
                        step++;
                    }
                    break;
                case PLUS:
                case MINUS:
                    expressionCommand.setOperation(lexer.getToken().getType());
                    break;
                case BIWISE_AND:
                case BITWISE_OR:
                    expressionCommand.setOperation(lexer.getToken().getType());
                    break;
                case AND:
                case OR:
                case EQUAL:
                case LESS:
                case MORE:
                    expressionCommand.setOperation(lexer.getToken().getType());
                    expressionCommand.setType(ExpressionType.BOOLEAN_TYPE);
                    break;

                case VALUE_ASSIGNMENT:
                    expressionCommand.setType(ExpressionType.EQUAL);
                    break;
                case CHECK:
                    expressionCommand.setOperation(Lexer.LexerValue.CHECK);
                    expressionCommand.setType(ExpressionType.CHECK);
                    break;
                case TURN_LEFT:
                    expressionCommand.setType(ExpressionType.TURN_LEFT);
                    break;
                case TURN_RIGHT:
                    expressionCommand.setType(ExpressionType.TURN_RIGHT);
                    break;
                case GO:
                    expressionCommand.setType(ExpressionType.GO);
                    break;
                case IF:
                    command = new ConditionalCommand(CommandType.IF);
                    break;
                case WHILE:
                    command = new ConditionalCommand(CommandType.WHILE);
                    break;
                case WRITE:
                    command = new OutputCommand();
                    break;
                case LPAR: //(
                    if (command != null && command instanceof ConditionalCommand) {
                        ((ConditionalCommand) command).setExpressionCommand(expressionCommand);
                    }
                    break;
                case RPAR:
                    if (command instanceof ConditionalCommand) {
                        return buildCommandBlock(command);
                    }
                    break;
                case RBRA:
                    if (command != null) {
                        return command;
                    } else {
                        expressionCommand.setType(ExpressionType.EMPTY);
                        return expressionCommand;
                    }
                case EOF:
                    expressionCommand.setType(ExpressionType.EOF);
                    return expressionCommand;
            }
            lexer.nextToken();
        }
        if (command != null) {
            return command;
        }
        return expressionCommand;
    }

    private Command buildCommandBlock(Command command) throws CodeExecutionException {
        ConditionalCommand conditionalCommand = (ConditionalCommand) command;
        while (lexer.getToken().getType() != Lexer.LexerValue.RBRA) {
            conditionalCommand.addCommand(parse());
        }
        return command;
    }

    private void buildExpressionCommand(ExpressionCommand expressionCommand, int step) {
        if (step == 0) {
            expressionCommand.setFirstOperand(new Token(lexer.getToken().getValue(), lexer.getToken().getType()));
        } else if (step == 1) {
            expressionCommand.setSecondOperand(new Token(lexer.getToken().getValue(), lexer.getToken().getType()));
        } else if (step == 2) {
            expressionCommand.setThirdOperand(new Token(lexer.getToken().getValue(), lexer.getToken().getType()));
        }
    }


}
