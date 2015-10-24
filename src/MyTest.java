import lexer_analyser.Lexer;
import parser.Parser;

import java.io.IOException;

/**
 * Created by User on 18.10.2015.
 */
public class MyTest {
    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer("a = 2; b = 1; c = 0; if(a > b and b > c){}");
        Parser parser = new Parser(lexer);
        boolean flag = true;
        while (flag) {
            Lexer.LexerValues result = parser.parse();
            System.out.println(result);
            if (result == Lexer.LexerValues.EOF)
                flag = false;
        }

//        NodeParser node_parser = new NodeParser(lexer);
//        node_parser.parse();
//        System.out.print(node_parser.getNode());



    }
}
