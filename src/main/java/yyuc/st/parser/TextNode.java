package yyuc.st.parser;

final class TextNode extends Node {

    TextNode(String content) {
        super(content);
    }

    @Override
    protected Object resolveValue(Object object) {
        return this.getContent();
    }
}
