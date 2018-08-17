public class ExpressionNode extends Node {
    private FieldNode node;

    public ExpressionNode(FieldNode node) {
        super(null);
        this.node = node;
    }

    @Override
    public Node getChild() {
        return this.node;
    }

    @Override
    public String toString() {
        return this.node.toString();
    }
}
