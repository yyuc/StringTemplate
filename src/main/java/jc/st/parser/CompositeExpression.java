package jc.st.parser;

import javax.el.ELContext;
import javax.el.ValueExpression;
import java.util.ArrayList;

public class CompositeExpression extends ValueExpression {
    private String expression;
    private ArrayList<Node> nodes;
    private Class<?> expectedType;

    public CompositeExpression(String expression, Class<?> expectedType) {
        this.expression = expression;
        this.expectedType = expectedType;
        try {
            this.nodes = new Parser(expression).build();
        } catch (Exception ex) {

        }
    }

    @Override
    public Object getValue(ELContext context) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            stringBuilder.append(nodes.get(i).getValue(context.getContext(this.expectedType)));
        }
        return stringBuilder.toString();
    }

    @Override
    public void setValue(ELContext context, Object value) {

    }

    @Override
    public boolean isReadOnly(ELContext context) {
        return false;
    }

    @Override
    public Class<?> getType(ELContext context) {
        return null;
    }

    @Override
    public Class<?> getExpectedType() {
        return null;
    }

    @Override
    public String getExpressionString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isLiteralText() {
        return false;
    }
}
