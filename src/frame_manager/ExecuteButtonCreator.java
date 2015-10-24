package frame_manager;

import lexer_analyser.Lexer;
import parser.Parser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by User on 24.10.2015.
 */
public class ExecuteButtonCreator {

    private MainFieldFrame parent;
    private JButton button;
    private boolean first = true;
    private String code;
    private Lexer lexer;
    private Parser parser;


    public ExecuteButtonCreator(MainFieldFrame parent) {
        this.parent = parent;
    }

    public JButton createButton() {
        button = new JButton("Go!");
        return button;
    }

    public void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (first) {
                    code = parent.getCode();
                    parent.setCodePaneNotEditable();
                    lexer = new Lexer(code);
                    parser = new Parser(lexer);
                }
                try {
                    parser.parse();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                first = false;
            }
        });
    }
}
