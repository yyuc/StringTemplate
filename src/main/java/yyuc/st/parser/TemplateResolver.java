package yyuc.st.parser;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class TemplateResolver {
    private Object object;
    private Map<String, String> cache;

    public TemplateResolver(Object object) throws InvalidParameterException {
        if (object == null) {
            throw new InvalidParameterException("object is required");
        }
        this.object = object;
        this.cache = new HashMap<>();
    }

    public String resolve(String template) throws TokenParseException {
        boolean hasCache = this.cache.containsKey(template);
        if (hasCache) {
            return this.cache.get(template);
        }
        CompositeNode compositeNode = new Parser(template).build();
        String result = compositeNode.eval(this.object);
        cache.put(template, result);
        return result;
    }
}
