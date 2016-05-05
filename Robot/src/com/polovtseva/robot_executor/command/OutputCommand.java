package com.polovtseva.robot_executor.command;

import com.polovtseva.robot_executor.action.EditorPaneAction;
import com.polovtseva.robot_executor.action.ExpressionAction;
import com.polovtseva.robot_executor.action.Interpreter;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.entity.Token;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.HashMap;

/**
 * Created by User on 12.03.2016.
 */
public class OutputCommand extends Command {

    private Token operand;

    public OutputCommand() {
        this.commandEnum = CommandEnum.OUTPUT_COMMAND;
    }

    public void setOperand(Token operand) {
        this.operand = operand;
    }

    @Override
    public CommandResult execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException {
        EditorPaneAction.addSimpleText(Interpreter.getFrame().getLogPane(),
                String.valueOf(ExpressionAction.takeOperandValue(operand, ids)) + "\n");
        return CommandResult.TRUE;
    }
}
