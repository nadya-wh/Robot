package com.polovtseva.robot_executor.command;

import com.polovtseva.robot_executor.action.ExpressionAction;
import com.polovtseva.robot_executor.action.Interpreter;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.entity.Token;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.HashMap;

/**
 * Created by User on 12.03.2016.
 */
public class OutputCommand implements Command {

    private Token operand;

    public OutputCommand() {
    }

    public void setOperand(Token operand) {
        this.operand = operand;
    }

    @Override
    public CommandResult execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException {
        Interpreter.getFrame().getLogPane().setText(Interpreter.getFrame().getLogPane().getText() + String.valueOf(ExpressionAction.takeOperandValue(operand, ids)));
        return CommandResult.TRUE;
    }
}
