package yyuc.st.parser;

abstract class Node {
    private String content;

    Node() {
    }

    Node(String content) {
        this.content = content;
    }

    String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return this.content;
    }

    protected abstract Object resolveValue(Object obj);
}
