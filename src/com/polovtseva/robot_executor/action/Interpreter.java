package com.polovtseva.robot_executor.action;

import com.polovtseva.robot_executor.command.CommandResult;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.exception.CodeExecutionException;
import com.polovtseva.robot_executor.command.Command;
import com.polovtseva.robot_executor.view.MainFieldFrame;
import com.polovtseva.robot_executor.command.Lexer;
import com.polovtseva.robot_executor.parser.Parser;

import javax.swing.*;
import java.util.HashMap;

/**
 * Created by User on 24.10.2015.
 */
public class Interpreter {
    public static final int ALPHABET_SIZE = 26;
    private Lexer lexer;
    private Parser parser;
    private Robot robot;
    private static MainFieldFrame frame;
    private HashMap<Integer, Integer> ids;


    public Interpreter(String code, Robot robot, MainFieldFrame frame) {
        lexer = new Lexer(code);
        parser = new Parser(lexer);
        this.robot = robot;
        this.frame = frame;
        ids = new HashMap<>(ALPHABET_SIZE);
    }


    public boolean execute() throws CodeExecutionException {
        Command command = parser.parse();
        CommandResult result =  command.execute(ids, robot);
        if (result == CommandResult.STOP) {
            System.out.println(command);
        }
        return result != CommandResult.STOP;
    }

    public static MainFieldFrame getFrame() {
        return frame;
    }

    //    private boolean doExpression(ExpressionCommand expressionCommand) throws CodeExecutionException {
//        switch (expressionCommand.getType()) {
//            case GO:
//                if (!robot.move()) {
//                    throw new CodeExecutionException("Robot can't go there.");
//                }
//                break;
//            case TURN_LEFT:
//                robot.turnLeft();
//                break;
//            case TURN_RIGHT:
//                robot.turnRight();
//                break;
//            case EOF:
//                return false; // TODO: 27.01.2016
//            case VALUE_ASSIGNMENT:
//                ExpressionAction.countAssignmentExpression(expressionCommand, ids);
//                break;
//            case BOOLEAN_TYPE:
//                ExpressionAction.countBooleanExpression(expressionCommand, ids);
//                break;
//            case CHECK:
//                return robot.check();
//        }
//        return true;
//    }

//    private boolean doConditionalCommand(ConditionalCommand conditionalCommand) throws CodeExecutionException {
////        boolean result = false;
////        switch (conditionalCommand.getCommandType()) {
////            case IF:
////                if (ExpressionAction.countBooleanExpression(conditionalCommand.getExpressionCommand(), ids)) {
////                    if (conditionalCommand.getCommands() != null) {
////                        for (Command command : conditionalCommand.getCommands()) {
////                            result = executeCommand(command);
////                        }
////                    }
////                }
////                break;
////            case WHILE:
////                while (ExpressionAction.countBooleanExpression(conditionalCommand.getExpressionCommand(), ids)) {
////                    for (Command command : conditionalCommand.getCommands()) {
////                        result = executeCommand(command);
////                    }
////                }
////                break;
////            default:
////                throw new CodeExecutionException("Unsupported operation: " + conditionalCommand.getCommandType());
////        }
////        return result;
//    }

//    private boolean executeCommand(Command command) throws CodeExecutionException {
//        if (command instanceof ExpressionCommand) {
//            ExpressionCommand expressionCommand = (ExpressionCommand) command;
//            return doExpression(expressionCommand);
//        } else if (command instanceof ConditionalCommand) {
//            ConditionalCommand conditionalCommand = (ConditionalCommand) command;
//            return doConditionalCommand(conditionalCommand);
//        }
//        return false;
//    }

    private void error(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, e.getMessage());
    }
}
