package jc.st.parser;

public class TextNode extends Node {

    public TextNode(String content) {
        super(content);
    }

    @Override
    public Object getValue(Object object) {
        return this.getContent();
    }
}
