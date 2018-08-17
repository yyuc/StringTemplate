package jc.st.el;

import jc.st.parser.CompositeExpression;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public class CustomExpressionFactoryImpl extends ExpressionFactory {
    @Override
    public ValueExpression createValueExpression(ELContext context, String expression, Class<?> expectedType) {
        return new CompositeExpression(expression, expectedType);
    }

    @Override
    public ValueExpression createValueExpression(Object instance, Class<?> expectedType) {
        return null;
    }

    @Override
    public MethodExpression createMethodExpression(ELContext context, String expression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes) {
        return null;
    }

    @Override
    public Object coerceToType(Object obj, Class<?> targetType) {
        return null;
    }
}
