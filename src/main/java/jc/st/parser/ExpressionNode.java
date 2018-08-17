package jc.st.parser;

import javax.el.ELContext;

public class ExpressionNode extends Node {
    private FieldNode node;

    public ExpressionNode(FieldNode node) {
        super(null);
        this.node = node;
    }

    @Override
    public Object getValue(Object object) {
        return this.node.getValue(object);
    }

    @Override
    public String toString() {
        return this.node.toString();
    }
}
