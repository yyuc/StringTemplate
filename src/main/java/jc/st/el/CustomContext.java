package jc.st.el;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

public class CustomContext extends ELContext {
    public CustomContext(Object object) {

        super.putContext(object.getClass(), object);
    }

    @Override
    public ELResolver getELResolver() {
        return new CustomResolver();
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return null;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return null;
    }
}
