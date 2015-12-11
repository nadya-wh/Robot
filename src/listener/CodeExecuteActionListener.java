package listener;

import frame_manager.MainFieldFrame;
import logic.CodeExecutor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 30.11.2015.
 */
public class CodeExecuteActionListener implements ActionListener {

    public static boolean isExecuting = false;
    private CodeExecutor codeExecutor;
    private MainFieldFrame frame;

    public  CodeExecuteActionListener(MainFieldFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isExecuting) {
            isExecuting = true;
            codeExecutor = new CodeExecutor(frame.getCode(), frame.getRobot(), frame);
            frame.setCodePaneNotEditable();
            execute();
        } else {
            execute();
        }
        frame.refreshTable();
    }

    public void execute() {
        if (!codeExecutor.execute())
            JOptionPane.showMessageDialog(frame, "The program is finished.");
    }

}

