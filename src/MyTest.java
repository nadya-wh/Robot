import lexer_analyser.Lexer;
import lexer_analyser.Token;
import parser.Node;
import parser.Parser;

import java.io.IOException;

/**
 * Created by User on 18.10.2015.
 */
public class MyTest {
    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer( "{ turn_left; go; }");
        Parser parser = new Parser(lexer);
        Node programNode = parser.parse();
        System.out.println(programNode);

    }
}
