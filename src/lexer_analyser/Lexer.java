package lexer_analyser;

import java.io.IOException;

/**
 * Created by User on 06.10.2015.
 */
public class Lexer {
    private String input;
    private int i;
    private Character currentSymbol;
    public Token token;

    public enum LexerValues {
        NUM, ID, IF, ELSE, WHILE, DO, LBRA, RBRA, LPAR, RPAR, PLUS, MINUS, LESS,
        EQUAL, SEMICOLON, EOF, MORE, TURN_RIGHT, TURN_LEFT, GO, NOT, AND, OR
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

    private boolean hasNextChar() {
        if (i < input.length())
            return true;
        return false;
    }

    public void nextToken() throws IOException {
        token.setSymbol(null);
        token.setValue(null);
        while (token.getSymbol() == null) {
            if (currentSymbol == null) {
                token.setSymbol(LexerValues.EOF);
            } else if (Character.isWhitespace(currentSymbol) || currentSymbol == '\n') {
                getNextChar();
            } else if (Token.SYMBOLS.containsKey(String.valueOf(currentSymbol))) {
                token.setSymbol(Token.SYMBOLS.get(String.valueOf(currentSymbol)));
                getNextChar();
            } else if (Character.isDigit(currentSymbol)) {
                int intValue = 0;
                while (Character.isDigit(currentSymbol)) {
                    intValue = intValue * 10 + Character.getNumericValue(currentSymbol);
                    getNextChar();
                }
                token.setValue(intValue);
                token.setSymbol(LexerValues.NUM);
            } else if (Character.isAlphabetic(currentSymbol)) { //TODO:tests
                StringBuilder ident = new StringBuilder("");
                while (Character.isAlphabetic(currentSymbol) || currentSymbol.equals(new Character('_'))) {
                    ident.append(currentSymbol);
                    getNextChar();
                }
                if (Token.SYMBOLS.containsKey(ident.toString())) {
                    token.setSymbol(Token.SYMBOLS.get(ident.toString()));
                    getNextChar();
                } else if (Token.WORDS.containsKey(ident.toString())) {
                    token.setSymbol(Token.WORDS.get(ident.toString()));
                } else if (ident.length() == 1) {
                    token.setSymbol(LexerValues.ID);
                    token.setValue((int) ident.charAt(0));//TODO: check
                } else {
                    throw new IllegalArgumentException("Unknown identifier: " + ident);
                }
            } else {
                throw new IllegalArgumentException("Unexpected symbol: " + currentSymbol);
            }
        }
    }

    public static void main(String[] args) {
        String code = "a = 1;\n" +
                "if (a = 1) {\n" +
                "go;\n" +
                "}\n";
        Lexer lexer = new Lexer(code);
        while (lexer.token.getSymbol() != LexerValues.EOF) {
            try {
                lexer.nextToken();
                System.out.println(lexer.token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
