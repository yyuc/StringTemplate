package yyuc.st.parser;

abstract class Node {
    private String content;
    private String format;
    private String extension;

    Node() {
    }

    Node(String content, String format, String extension) {
        this.content = content;
        this.format = format;
        this.extension = extension;
    }

    String getContent() {
        return this.content;
    }

    String getFormat() { return this.format; }

    String getExtension() { return this.extension; }

    @Override
    public String toString() {
        return this.content;
    }

    protected abstract Object resolveValue(Object obj);
}
