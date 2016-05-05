package com.polovtseva.robot_executor.command;

import com.polovtseva.robot_executor.action.ExpressionAction;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 27.01.2016.
 */
public class ConditionalCommand extends Command {
    private ExpressionCommand expressionCommand;
    private ArrayList<Command> commands;
    private ConditionalCommandType conditionalCommandType;

    public ConditionalCommand(ConditionalCommandType conditionalCommandType) {
        this.conditionalCommandType = conditionalCommandType;
        commands = new ArrayList<>();
        this.commandEnum = CommandEnum.CONDITIONAL_COMMAND;
    }

    public ConditionalCommand(ExpressionCommand expressionCommand, ArrayList<Command> commands, ConditionalCommandType conditionalCommandType) {
        this.expressionCommand = expressionCommand;
        this.commands = commands;
        this.conditionalCommandType = conditionalCommandType;
        this.commandEnum = CommandEnum.CONDITIONAL_COMMAND;
    }

    public ExpressionCommand getExpressionCommand() {
        return expressionCommand;
    }

    public void setExpressionCommand(ExpressionCommand expressionCommand) {
        this.expressionCommand = expressionCommand;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public ConditionalCommandType getConditionalCommandType() {
        return conditionalCommandType;
    }

    public void setConditionalCommandType(ConditionalCommandType conditionalCommandType) {
        this.conditionalCommandType = conditionalCommandType;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public CommandResult execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException {
        CommandResult result = CommandResult.FALSE;
        switch (getConditionalCommandType()) {
            case IF:
                if (ExpressionAction.countSimpleBooleanExpression(getExpressionCommand(), ids)) {
                    if (getCommands() != null) {
                        for (Command command : getCommands()) {
                            result = command.execute(ids, robot);
                        }
                    }
                }
                break;
            case WHILE:
                while (ExpressionAction.countSimpleBooleanExpression(getExpressionCommand(), ids)) {
                    for (Command command : getCommands()) {
                        result = command.execute(ids, robot);
                    }
                }
                break;
            default:
                throw new CodeExecutionException("Unsupported operation: " + getConditionalCommandType());
        }
        return result;
    }
}
