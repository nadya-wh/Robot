package node_parser;

/**
 * Created by User on 13.10.2015.
 */
public class Node {
    public enum NodeType {VAR, CONST, ADD, SUB, LESS, MORE, SET, IF1, IF2, WHILE, DO, EMPTY, SEQ, EXPR, PROG, TURN_RIGHT, TURN_LEFT, GO}

    private NodeType nodeType;
    private int value;
    private Node op1;
    private Node op2;
    private Node op3;


    public Node(NodeType nodeType, Node op3, Node op2, Node op1, int value) {
        this.nodeType = nodeType;
        this.op3 = op3;
        this.op2 = op2;
        this.op1 = op1;
        this.value = value;
    }

    public Node(NodeType nodeType, int value, Node op1, Node op2) {
        this.nodeType = nodeType;
        this.value = value;
        this.op1 = op1;
        this.op2 = op2;
    }


    public Node(NodeType nodeType, int value) {
        this.nodeType = nodeType;
        this.value = value;
    }

    public Node(NodeType nodeType, Node op1, Node op2) {
        this.nodeType = nodeType;
        this.op1 = op1;
        this.op2 = op2;
    }

    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Node() {};

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getOp1() {
        return op1;
    }

    public void setOp1(Node op1) {
        this.op1 = op1;
    }

    public Node getOp3() {
        return op3;
    }

    public void setOp3(Node op3) {
        this.op3 = op3;
    }

    public Node getOp2() {
        return op2;
    }

    public void setOp2(Node op2) {
        this.op2 = op2;
    }

    @Override
    public String toString() {
        return "\nNode{" +
                "nodeType=" + nodeType +
                ", value=" + value +
                ", \nop1=" + op1 +
                ", \nop2=" + op2 +
                ", \nop3=" + op3 +
                '}';
    }
}

