package parser;

import lexer_analyser.Lexer;
import lexer_analyser.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 20.10.2015.
 */
public class Parser {
    Lexer lexer;
    HashMap<Integer, Integer> ids;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        ids = new HashMap<Integer, Integer>(26);
    }

    public Lexer.LexerValues parse() throws IOException {
        lexer.nextToken();
        switch (lexer.token.getSymbol()) {
            case LBRA:
                return Lexer.LexerValues.LBRA;
            case RBRA:
                return Lexer.LexerValues.RBRA;
            case IF:
                lexer.nextToken();
                if(countStatement()) {
                   doBlock(); //TODO: check;
                } else {
                    missStatement();
                }
                return Lexer.LexerValues.IF;
            case WHILE:
                return Lexer.LexerValues.WHILE;
            case GO:
                return Lexer.LexerValues.GO;
            case TURN_LEFT:
                return Lexer.LexerValues.TURN_RIGHT;
            case TURN_RIGHT:
                return Lexer.LexerValues.TURN_RIGHT;
            case ID:
                idToken();
                return Lexer.LexerValues.ID;
            case EOF:
                return Lexer.LexerValues.EOF;
            case SEMICOLON:
                return Lexer.LexerValues.SEMICOLON;
            default:
                throw new IllegalArgumentException("SGW");
        }
    }

    public void idToken() throws IOException {
        int id = lexer.token.getValue();
        lexer.nextToken();
        switch (lexer.token.getSymbol()) {
            case EQUAL:
                int value = countExpression();
                ids.put(id, value);
                break;
            case SEMICOLON:
                ids.put(id, 0);
                break;
        }

    }

    public int countExpression() throws IOException {
        System.out.println("Count statement");
        int value = 0;
        Lexer.LexerValues prevOperation = Lexer.LexerValues.EQUAL;
        lexer.nextToken();
        while (lexer.token.getSymbol() != Lexer.LexerValues.SEMICOLON) {
            if (lexer.token.getSymbol() == Lexer.LexerValues.NUM || lexer.token.getSymbol() == Lexer.LexerValues.ID) {
                if (prevOperation == Lexer.LexerValues.EQUAL) {
                    value = lexer.token.getValue();
                } else if (prevOperation == Lexer.LexerValues.MINUS) {
                    value -= lexer.token.getValue();
                } else if (prevOperation == Lexer.LexerValues.PLUS) {
                    value += lexer.token.getValue();
                } else {
                    throw new IllegalArgumentException("Illegal operation");
                }
                lexer.nextToken();
            } else {
                throw new IllegalArgumentException("Illegal operation");
            }

        }
        return value;
    }

    public boolean countStatement() throws IOException {
        System.out.println("Count statement");
        if(lexer.token.getSymbol() == Lexer.LexerValues.LPAR) {
            //throw new IllegalArgumentException("'(' expected");
            lexer.nextToken();
        }
        ArrayList<Token> tokens = new ArrayList<Token>();
        while (lexer.token.getSymbol() != Lexer.LexerValues.RPAR && lexer.token.getSymbol() != Lexer.LexerValues.AND
                && lexer.token.getSymbol() != Lexer.LexerValues.OR) {
            Token token = new Token();
            token.setSymbol(lexer.token.getSymbol());
            token.setValue(lexer.token.getValue());
            tokens.add(token);
            lexer.nextToken();
        }
        //tokens.remove(tokens.size() - 1);
        if (lexer.token.getSymbol() == Lexer.LexerValues.AND) {
            lexer.nextToken();
            boolean left = countSimpleExpression(tokens);
            boolean right = countStatement();
            return left && right;
        } else if (lexer.token.getSymbol() == Lexer.LexerValues.OR) {
            lexer.nextToken();
            boolean left = countSimpleExpression(tokens);
            boolean right = countStatement();
            return left || right;
        } else {
            lexer.nextToken();
            return countSimpleExpression(tokens);
        }
    }

    public void missStatement() throws IOException {
        if(lexer.token.getSymbol() != Lexer.LexerValues.LBRA){
            throw new IllegalArgumentException("'{' expected");
        }
        while (lexer.token.getSymbol() != Lexer.LexerValues.RBRA) {
            lexer.nextToken();
        }
    }

    public void doBlock() throws IOException {
        //lexer.nextToken();
        if (lexer.token.getSymbol() != Lexer.LexerValues.LBRA) {
            throw new IllegalArgumentException("'{' expected");
        }
        lexer.nextToken();
        boolean stop = false;
        while (!stop) {
            if(lexer.token.getSymbol() == Lexer.LexerValues.RBRA)
                stop = true;
            //TODO: check;
            lexer.nextToken();
        }
    }

    public boolean countSimpleExpression(ArrayList<Token> tokens) {
        System.out.println("Count simple statement");
        for(int i = 0; i < tokens.size(); i++) {
            switch (tokens.get(i).getSymbol()) {
                case ID:
                    if (tokens.size() == 3) {
                        i++;
                        switch (tokens.get(i).getSymbol()) {
                            case LESS:
                                return ids.get(tokens.get(i - 1).getValue()) < ids.get(tokens.get(i + 1).getValue());
                            case MORE:
                                return ids.get(tokens.get(i - 1).getValue()) > ids.get(tokens.get(i + 1).getValue());
                            case EQUAL:
                                return ids.get(tokens.get(i - 1).getValue()) == ids.get(tokens.get(i + 1).getValue());
                        }
                    } else {
                        throw new IllegalArgumentException("Too difficult expression in if statement.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown expression in if statement.");
            }
        }
        return false;
    }

}
