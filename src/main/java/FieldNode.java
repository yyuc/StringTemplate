public class FieldNode extends Node {
    private FieldNode parent;

    public FieldNode(String content) {
        this(null, content);
    }

    public FieldNode(FieldNode parent, String content) {
        super(content);
        this.parent = parent;
    }

    @Override
    public Node getChild() {
        return null;
    }

    @Override
    public String toString() {
        if (this.parent == null) {
            return this.getContent();
        }
        return this.parent.toString() + "." + this.getContent();
    }
}
