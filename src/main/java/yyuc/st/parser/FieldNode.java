package yyuc.st.parser;

import com.google.common.base.Strings;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

final class FieldNode extends Node {
    private FieldNode parent;

    FieldNode(FieldNode parent, String fieldName, String format, String extension) {
        super(fieldName, format, extension);
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
                return this.format(field.get(object));
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
            return this.format(field.get(parent));
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

    private Object format(Object object) {
        if (object == null || Strings.isNullOrEmpty(this.getFormat())) {
            return object;
        }

        String format = this.getFormat();

        if (object instanceof BigDecimal) {
            return new DecimalFormat(format).format(object);
        } else if (object instanceof ZonedDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            if (!Strings.isNullOrEmpty(this.getExtension())) {
                object = ((ZonedDateTime) object).withZoneSameInstant(ZoneId.of(this.getExtension()));
            }
            return formatter.format((ZonedDateTime) object);
        } else {
            return object;
        }
    }
}
