package logic;

import entity.Robot;
import frame_manager.MainFieldFrame;
import lexer_analyser.Lexer;
import parser.Parser;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by User on 24.10.2015.
 */
public class CodeExecutor {

    private Lexer lexer;
    private Parser parser;
    private Robot robot;
    private MainFieldFrame frame;


    public CodeExecutor(String code, Robot robot, MainFieldFrame frame) {
        lexer = new Lexer(code);
        parser = new Parser(lexer);
        this.robot = robot;
        this.frame = frame;
    }


    public boolean execute() {
        Lexer.LexerValues value = null;
        try {
            value = parser.parse();
        } catch (IOException e) {
            return false;
        }
        switch (value) {
            case GO:
                try {
                    if (parser.parse() != Lexer.LexerValues.SEMICOLON) {
                        throw new IllegalArgumentException("Semicolon expected.");
                    }
                } catch (IOException e) {
                    error(e);
                }
                robot.move();
                break;
            case TURN_LEFT:
                try {
                    if (parser.parse() != Lexer.LexerValues.SEMICOLON) {
                        throw new IllegalArgumentException("Semicolon expected.");
                    }
                } catch (IOException e) {
                    error(e);
                }
                robot.turnLeft();
                break;
            case TURN_RIGHT:
                try {
                    if (parser.parse() != Lexer.LexerValues.SEMICOLON) { //TODO:
                        throw new IllegalArgumentException("Semicolon expected.");
                    }
                } catch (IOException e) {
                    error(e);
                }
                robot.turnRight();
                break;
            case EOF:
                return false;
        }
        return true;
    }

    private void error(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, e.getMessage());
    }
}
