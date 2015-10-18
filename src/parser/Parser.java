package parser;

import lexer_analyser.Lexer;

import java.io.IOException;

/**
 * Created by User on 13.10.2015.
 */
public class Parser {
    private Lexer lexer;
    private Node node;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Node getNode() {
        return node;
    }

    public Node parse() throws IOException {
        lexer.nextToken();
        node = new Node(Node.NodeType.PROG);
        node.setOp1(statement());
        node.setNodeType(Node.NodeType.PROG);
        return node;
    }

    public Node statement() throws IOException {
        Node n = new Node();
        if (lexer.token.getSymbol() == Lexer.LexerValues.IF) {
            n.setNodeType(Node.NodeType.IF1);
            lexer.nextToken();
            n.setOp1(getParenthesisExpression());
            n.setOp2(statement());
            if (lexer.token.getSymbol() == Lexer.LexerValues.ELSE) {
                n.setNodeType(Node.NodeType.IF2);
                lexer.nextToken();
                n.setOp3(statement());
            }
        } else if (lexer.token.getSymbol() == Lexer.LexerValues.WHILE) {
            n.setNodeType(Node.NodeType.WHILE);
            lexer.nextToken();
            n.setOp1(getParenthesisExpression());
            n.setOp2(statement());
        } else if (lexer.token.getSymbol() == Lexer.LexerValues.DO) {
            n = new Node(Node.NodeType.DO);
            lexer.nextToken();
            n.setOp1(statement());
            if (lexer.token.getSymbol() != Lexer.LexerValues.WHILE) {
                throw new IllegalArgumentException("'While' expected");
            }
            lexer.nextToken();
            n.setOp2(getParenthesisExpression());
            if (lexer.token.getSymbol() != Lexer.LexerValues.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
        } else if (lexer.token.getSymbol() == Lexer.LexerValues.SEMICOLON) {
            n = new Node(Node.NodeType.EMPTY);
            lexer.nextToken();
        } else if (lexer.token.getSymbol() == Lexer.LexerValues.LBRA) {

            n = new Node(Node.NodeType.EMPTY);
            lexer.nextToken();
            while (lexer.token.getSymbol() != Lexer.LexerValues.RBRA) {
                Node op1 = n;
                n = new Node(Node.NodeType.SEQ, op1, statement());
            }
            lexer.nextToken();
        } else {
            n = new Node(Node.NodeType.EXPR);
            n.setOp1(getExpression());
            if (lexer.token.getSymbol() != Lexer.LexerValues.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
            lexer.nextToken();
        }
        return n;
    }

    public Node getParenthesisExpression() throws IOException {
        Node n;
        if (lexer.token.getSymbol() != Lexer.LexerValues.LPAR) {
            throw new IllegalArgumentException("'(' expected");
        }
        lexer.nextToken();
        n = getExpression();
        if (lexer.token.getSymbol() != Lexer.LexerValues.RPAR) {
            throw new IllegalArgumentException("')' expected");
        }
        lexer.nextToken();
        return n;
    }

    public Node getExpression() throws IOException {
        if (lexer.token.getSymbol() != Lexer.LexerValues.ID) {
            return test();
        }
        Node n = test();
        if (n.getNodeType() == Node.NodeType.VAR && lexer.token.getSymbol() == Lexer.LexerValues.EQUAL) {
            lexer.nextToken();
            Node op1 = n;
            n = new Node(Node.NodeType.SET, op1, getExpression());
        }
        return n;
    }

    private Node test() throws IOException {
        Node n = sum();
        if (lexer.token.getSymbol() == Lexer.LexerValues.LESS) {
            lexer.nextToken();
            Node op1 = n;
            n = new Node(Node.NodeType.SET, op1, getExpression());
        }
        return n;
    }

    public Node sum() throws IOException {
        Node n = term();
        Node.NodeType type;
        while (lexer.token.getSymbol() == Lexer.LexerValues.PLUS ||
                lexer.token.getSymbol() == Lexer.LexerValues.MINUS) {
            if (lexer.token.getSymbol() == Lexer.LexerValues.PLUS) {
                type = Node.NodeType.ADD;
            } else {
                type = Node.NodeType.SUB;
            }
            lexer.nextToken();
            Node op1 = n;
            n = new Node();
            n.setNodeType(type);
            n.setOp1(op1);
            n.setOp2(term());
        }
        return n;
    }

    public Node term() throws IOException {
        Node n = new Node();
        if (lexer.token.getSymbol() == Lexer.LexerValues.ID) {
            n.setNodeType(Node.NodeType.VAR);
            n.setValue(lexer.token.getValue());
            lexer.nextToken();
            return n;
        } else if (lexer.token.getSymbol() == Lexer.LexerValues.NUM) {
            n.setNodeType(Node.NodeType.CONST);
            n.setValue(lexer.token.getValue());
            lexer.nextToken();
            return n;
        } else {
            return getParenthesisExpression();
            //TODO: WHYYYY???
        }
    }

}
