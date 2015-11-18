package logic;

import entities.Robot;
import lexer_analyser.Lexer;
import parser.Parser;

import javax.xml.bind.ParseConversionEvent;
import java.io.IOException;

/**
 * Created by User on 24.10.2015.
 */
public class CodeExecutor {

    private Lexer lexer;
    private Parser parser;
    private Robot robot;


    public CodeExecutor(String code, Robot robot) {
        lexer = new Lexer(code);
        parser = new Parser(lexer);
        this.robot = robot;
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
                    e.printStackTrace();
                }
                robot.move();
                break;
            case TURN_LEFT:
                try {
                    if (parser.parse() != Lexer.LexerValues.SEMICOLON) {
                        throw new IllegalArgumentException("Semicolon expected.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                robot.turnLeft();
                break;
            case TURN_RIGHT:
                try {
                    if (parser.parse() != Lexer.LexerValues.SEMICOLON) {
                        throw new IllegalArgumentException("Semicolon expected.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                robot.turnRight();
                break;
        }
        return true;
    }
}
