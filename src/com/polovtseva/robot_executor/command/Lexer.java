package com.polovtseva.robot_executor.command;

import com.polovtseva.robot_executor.entity.Token;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

/**
 * Created by User on 06.10.2015.
 */
public class Lexer {
    private String input;
    private int i;
    private Character currentSymbol;
    private Token token;

    public enum LexerValue {
        NUM, ID, IF, ELSE, WHILE, DO, LBRA, RBRA, LPAR, RPAR, PLUS, MINUS, LESS,
        VALUE_ASSIGNMENT, SEMICOLON, EOF, MORE, TURN_RIGHT, TURN_LEFT, GO, NOT, AND, OR, CHECK, BIWISE_AND,
        BITWISE_OR, WRITE, EQUAL
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Lexer(String input) {
        token = new Token();
        currentSymbol = new Character('\0');
        this.input = input;
        i = 0;
        currentSymbol = ' ';
    }

    private void getNextChar() {
        if (i < input.length())
            currentSymbol = input.charAt(i);
        else {
            currentSymbol = null;
        }
        i++;
    }


    public void nextToken() throws CodeExecutionException {
        token.setType(null);
        token.setValue(null);
        while (token.getType() == null) {
            if (currentSymbol == null) {
                token.setType(LexerValue.EOF);
            } else if (Character.isWhitespace(currentSymbol) || currentSymbol == '\n') {
                getNextChar();
            } else if (Token.SYMBOLS.containsKey(String.valueOf(currentSymbol))) {
                token.setType(Token.SYMBOLS.get(String.valueOf(currentSymbol)));
                getNextChar();
            } else if (Character.isDigit(currentSymbol)) {
                int intValue = 0;
                while (Character.isDigit(currentSymbol)) {
                    intValue = intValue * 10 + Character.getNumericValue(currentSymbol);
                    getNextChar();
                }
                token.setValue(intValue);
                token.setType(LexerValue.NUM);
            } else if (Character.isAlphabetic(currentSymbol)) {
                StringBuilder ident = new StringBuilder("");
                while (Character.isAlphabetic(currentSymbol) || currentSymbol.equals(new Character('_'))) {
                    ident.append(currentSymbol);
                    getNextChar();
                }
                if (Token.SYMBOLS.containsKey(ident.toString())) {
                    token.setType(Token.SYMBOLS.get(ident.toString()));
                    getNextChar();
                } else if (Token.WORDS.containsKey(ident.toString())) {
                    token.setType(Token.WORDS.get(ident.toString()));
                } else if (ident.length() == 1) {
                    token.setType(LexerValue.ID);
                    token.setValue((int) ident.charAt(0));
                } else {
                    throw new CodeExecutionException("Unknown identifier: " + ident);
                }
            } else {
                throw new CodeExecutionException("Unexpected symbol: " + currentSymbol);
            }
        }
    }

    public static void main(String[] args) {
        String code = "a = 1;\n" +
                "if (a = 1) {\n" +
                "go;\n" +
                "}\n";
        Lexer lexer = new Lexer(code);
        while (lexer.token.getType() != LexerValue.EOF) {
            try {
                lexer.nextToken();
                System.out.println(lexer.token);
            } catch (CodeExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
