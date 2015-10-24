package lexer_analyser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 06.10.2015.
 */
public class Token {
    private Integer value;
    private Lexer.LexerValues symbol;
    public static final Map<String, Lexer.LexerValues> SYMBOLS;
    public static final Map<String, Lexer.LexerValues> WORDS;
    static {
        SYMBOLS = new HashMap<String, Lexer.LexerValues>();
        WORDS = new HashMap<String, Lexer.LexerValues>();
        SYMBOLS.put("{", Lexer.LexerValues.LBRA);
        SYMBOLS.put("}", Lexer.LexerValues.RBRA);
        SYMBOLS.put("=", Lexer.LexerValues.EQUAL);
        SYMBOLS.put(";", Lexer.LexerValues.SEMICOLON);
        SYMBOLS.put("(", Lexer.LexerValues.LPAR);
        SYMBOLS.put(")", Lexer.LexerValues.RPAR);
        SYMBOLS.put("+", Lexer.LexerValues.PLUS);
        SYMBOLS.put("-", Lexer.LexerValues.MINUS);
        SYMBOLS.put(">", Lexer.LexerValues.MORE);
        SYMBOLS.put("<", Lexer.LexerValues.LESS);
        SYMBOLS.put("!", Lexer.LexerValues.NOT);
        WORDS.put("if", Lexer.LexerValues.IF);
        WORDS.put("else", Lexer.LexerValues.ELSE);
        WORDS.put("do", Lexer.LexerValues.DO);
        WORDS.put("while", Lexer.LexerValues.WHILE);
        WORDS.put("turn_right", Lexer.LexerValues.TURN_RIGHT);
        WORDS.put("turn_left", Lexer.LexerValues.TURN_LEFT);
        WORDS.put("go", Lexer.LexerValues.GO);
        WORDS.put("and", Lexer.LexerValues.AND);
        WORDS.put("or", Lexer.LexerValues.OR);
    }

    public Token(){
        value = 0;
        symbol = null;
    }

    public void setSymbol(Lexer.LexerValues symbol) {
        this.symbol = symbol;
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

    public Lexer.LexerValues getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "lexer_analyser.Token{" +
                "value=" + value +
                ", symbol=" + symbol +
                '}';
    }
}
