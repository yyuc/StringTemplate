package yyuc.st.parser;

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
    public Object getValue(Object object) {
        if (this.parent == null) {
            try {
                return object.getClass().getField(this.getContent()).get(object);
            } catch (NoSuchFieldException ex) {

            } catch (IllegalAccessException ex) {
            }
        }
        try {
            Object parent = this.parent.getValue(object);
            if (parent == null) {
                return null;
            }
            return parent.getClass().getField(this.getContent()).get(parent);
        } catch (NoSuchFieldException ex) {

        } catch (IllegalAccessException ex) {

        }
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
