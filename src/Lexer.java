import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by User on 06.10.2015.
 */
public class Lexer {
    private String input;
    private int i;
    private char currentSymbol;

    public enum LexerValues {
        NUM, ID, IF, ELSE, WHILE, DO, LBRA, RBRA, LPAR, RPAR, PLUS, MINUS, LESS,
        EQUAL, SEMICOLON, EOF, MORE
    }


    public Lexer(String input) {
        this.input = input;
        i = 0;
        currentSymbol = ' ';
    }

    private void getNextChar() {
        if (i < input.length())
            currentSymbol = input.charAt(i);
        i++;
    }

    public Token nextToken() throws IOException {
        Token token = new Token();

        while (token.getSymbol() == null && token.getSymbol() != LexerValues.EOF) {
            if (i > input.length()) {
                token.setSymbol(LexerValues.EOF);
                break;
            }
            //getNextChar();
            if (Character.isWhitespace(currentSymbol)) {
                getNextChar();
            } else if (Token.SYMBOLS.containsKey(String.valueOf(currentSymbol))) {
                token.setSymbol(Token.SYMBOLS.get(String.valueOf(currentSymbol)));
                getNextChar();
            } else if (Character.isDigit(currentSymbol)) {
                int intval = 0;
                while (Character.isDigit(currentSymbol)) {
                    intval = intval * 10 + Character.getNumericValue(currentSymbol);
                    getNextChar();
                }
                token.setValue(intval);
                token.setSymbol(LexerValues.NUM);
            } else if (Character.isAlphabetic(currentSymbol)) { //TODO:tests
                StringBuilder ident = new StringBuilder("");
                while (Character.isAlphabetic(currentSymbol)) {
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
                    token.setValue(ident.charAt(0));
                } else {
                    throw new IllegalArgumentException("Unknown identifier: " + ident);
                }
            } else {
                throw new IllegalArgumentException("Unexpected symbol: " + currentSymbol);
            }
        }

        return token;
    }

    public static void main(String[] args) {
        String code = "if();";
        Lexer lexer = new Lexer(code);
        Token token = new Token();
        while (token.getSymbol() != LexerValues.EOF) {
            try {
                token = lexer.nextToken();
                System.out.println(token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
