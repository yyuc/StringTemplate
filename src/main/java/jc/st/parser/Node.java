package jc.st.parser;

public abstract class Node {
    private String content;

    public Node() {

    }

    public Node(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return this.content;
    }

    public abstract Object getValue(Object obj);
}
