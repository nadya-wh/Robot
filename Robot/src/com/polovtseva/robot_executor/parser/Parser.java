package com.polovtseva.robot_executor.parser;

import com.polovtseva.robot_executor.entity.*;
import com.polovtseva.robot_executor.exception.CodeExecutionException;
import com.polovtseva.robot_executor.command.*;
import com.polovtseva.robot_executor.lexer.Lexer;

/**
 * Created by User on 20.10.2015.
 */
public class Parser {
    private Lexer lexer;

    private static int openedBras = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Command parse() throws CodeExecutionException {
        Command command = null;
        ExpressionCommand expressionCommand = new ExpressionCommand();
        lexer.nextToken();
        int step = 0;
        while (lexer.getToken().getType() != Lexer.LexerValue.SEMICOLON) {
            System.out.println(lexer.getToken());
            switch (lexer.getToken().getType()) {
                case ID:
                case NUM:
                    if (command == null ||
                            command.getCommandEnum() == CommandEnum.EXPRESSION_COMMAND) { //|| command instanceof ConditionalCommand
                        command = expressionCommand;
                        buildExpressionCommand(expressionCommand, step);
                        step++;
                    } else if (command.getCommandEnum() == CommandEnum.OUTPUT_COMMAND) {
                        OutputCommand outputCommand = (OutputCommand) command;
                        outputCommand.setOperand(new Token(lexer.getToken().getValue(), lexer.getToken().getType()));
                    } else if (command.getCommandEnum() == CommandEnum.CONDITIONAL_COMMAND) {
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
                    command = new ConditionalCommand(ConditionalCommandType.IF);
                    break;
                case WHILE:
                    command = new ConditionalCommand(ConditionalCommandType.WHILE);
                    break;
                case WRITE:
                    command = new OutputCommand();
                    break;
                case LPAR: //(
                    if (command != null && command.getCommandEnum() == CommandEnum.CONDITIONAL_COMMAND) {
                        ((ConditionalCommand) command).setExpressionCommand(expressionCommand);
                    }
                    break;
                case RPAR:
                    if (command.getCommandEnum() == CommandEnum.CONDITIONAL_COMMAND) {
                        return buildCommandBlock(command);
                    }
                    break;
                case RBRA:
                    openedBras--;
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
        openedBras++;
        int braId = openedBras;
        ConditionalCommand conditionalCommand = (ConditionalCommand) command;
        while (openedBras != braId - 1) {
            Command newCommand = parse();
            conditionalCommand.addCommand(newCommand);
        }
        //lexer.nextToken();
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
