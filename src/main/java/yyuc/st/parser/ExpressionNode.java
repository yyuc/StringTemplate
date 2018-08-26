package yyuc.st.parser;

final class ExpressionNode extends Node {
    private FieldNode node;

    ExpressionNode(FieldNode node) {
        super(null, null, null);
        this.node = node;
    }

    @Override
    protected Object resolveValue(Object object) {
        return this.node.resolveValue(object);
    }

    @Override
    public String toString() {
        return this.node.toString();
    }
}
