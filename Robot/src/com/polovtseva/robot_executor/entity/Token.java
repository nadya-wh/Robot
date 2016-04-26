package com.polovtseva.robot_executor.entity;

import com.polovtseva.robot_executor.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 06.10.2015.
 */
public class Token implements Cloneable{
    private Integer value;
    private Lexer.LexerValue type;
    public static final Map<String, Lexer.LexerValue> SYMBOLS;
    public static final Map<String, Lexer.LexerValue> WORDS;
    static {
        SYMBOLS = new HashMap<>();
        WORDS = new HashMap<>();
        SYMBOLS.put("{", Lexer.LexerValue.LBRA);
        SYMBOLS.put("}", Lexer.LexerValue.RBRA);
        SYMBOLS.put("=", Lexer.LexerValue.VALUE_ASSIGNMENT);
        SYMBOLS.put(";", Lexer.LexerValue.SEMICOLON);
        SYMBOLS.put("(", Lexer.LexerValue.LPAR);
        SYMBOLS.put(")", Lexer.LexerValue.RPAR);
        SYMBOLS.put("+", Lexer.LexerValue.PLUS);
        SYMBOLS.put("-", Lexer.LexerValue.MINUS);
        SYMBOLS.put(">", Lexer.LexerValue.MORE);
        SYMBOLS.put("<", Lexer.LexerValue.LESS);
        SYMBOLS.put("!", Lexer.LexerValue.NOT);
        SYMBOLS.put("&", Lexer.LexerValue.BIWISE_AND);
        SYMBOLS.put("|", Lexer.LexerValue.BITWISE_OR);
        WORDS.put("if", Lexer.LexerValue.IF);
        WORDS.put("else", Lexer.LexerValue.ELSE);
        WORDS.put("do", Lexer.LexerValue.DO);
        WORDS.put("while", Lexer.LexerValue.WHILE);
        WORDS.put("turn_right", Lexer.LexerValue.TURN_RIGHT);
        WORDS.put("turn_left", Lexer.LexerValue.TURN_LEFT);
        WORDS.put("go", Lexer.LexerValue.GO);
        WORDS.put("and", Lexer.LexerValue.AND);
        WORDS.put("or", Lexer.LexerValue.OR);
        WORDS.put("check", Lexer.LexerValue.CHECK);
        WORDS.put("equals", Lexer.LexerValue.EQUAL);
        WORDS.put("write", Lexer.LexerValue.WRITE);

    }

    public Token(){
        value = 0;
        type = null;
    }

    public Token(Integer value, Lexer.LexerValue type) {
        this.value = value;
        this.type = type;
    }

    public void setType(Lexer.LexerValue type) {
        this.type = type;
    }

    public void setValue(Integer value) {
        if(value != null) {
            this.value = value;
        } else {
            value = 0;
        }
    }

    public int getValue() {
        return value;
    }

    public Lexer.LexerValue getType() {
        return type;
    }

    @Override
    public String toString() {
        return "com.polovtseva.Token{" +
                "value=" + value +
                ", type=" + type +
                '}';
    }
}
