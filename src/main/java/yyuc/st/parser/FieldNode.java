package yyuc.st.parser;

import java.lang.reflect.Field;

final class FieldNode extends Node {
    private FieldNode parent;

    FieldNode(String content) {
        this(null, content);
    }

    FieldNode(FieldNode parent, String content) {
        super(content);
        this.parent = parent;
    }

    @Override
    protected Object resolveValue(Object object) {
        if (this.parent == null) {
            try {
                Field field = object.getClass().getDeclaredField(this.getContent());
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException ex) {
                return null;
            } catch (IllegalAccessException ex) {
                return null;
            }
        }
        try {
            Object parent = this.parent.resolveValue(object);
            if (parent == null) {
                return null;
            }

            Field field = parent.getClass().getDeclaredField(this.getContent());
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return field.get(parent);
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        if (this.parent == null) {
            return this.getContent();
        }
        return this.parent.toString() + "." + this.getContent();
    }
}
