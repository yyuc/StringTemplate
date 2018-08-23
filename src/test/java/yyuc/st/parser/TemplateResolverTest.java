package yyuc.st.parser;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.UUID;

public class TemplateResolverTest {

    @Test(expected = InvalidParameterException.class)
    public void ThrowParameterExceptionWhenEntityObjectNull() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(null);
        resolver.resolve("{{hei}}");
    }

    @Test(expected = TokenParseException.class)
    public void ThrowTokenParseExceptionWhenExpressionWithoutEndTag() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        resolver.resolve("{{id");
    }

    @Test(expected = TokenParseException.class)
    public void ThrowTokenParseExceptionWhenExpressionWithWrongEndTag() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        resolver.resolve("{{id}");
    }

    @Test(expected = TokenParseException.class)
    public void ThrowTokenParseExceptionWhenExpressionWithoutField() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        resolver.resolve("{{}}");
    }

    @Test
    public void ReturnTextWhenExpressionIsText() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        String result = resolver.resolve("this is a test");
        Assert.assertEquals("this is a test", result);
    }

    @Test
    public void ReturnReplacedTextWhenExpressionIsInheritanceField() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        String result = resolver.resolve("Hi, {{name}}. Your id is {{id}}");
        Assert.assertEquals("Hi, rick.zhao. Your id is f3d84ae5-3431-4385-b83f-1385b73446e4", result);
    }


    @Test
    public void ReturnReplacedTextWhenExpressionIsFieldx() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        String result = resolver.resolve("My address is {{address.city}} {{address.addressLine}}");
        Assert.assertEquals("My address is ?? ???????161?20?201?", result);
    }

    public TemplateResolverTest() {
        this.person.setId("f3d84ae5-3431-4385-b83f-1385b73446e4");
        this.person.setName("rick.zhao");
        this.person.getAddress().setId(UUID.randomUUID().toString());
        this.person.getAddress().setCountry("??");
        this.person.getAddress().setCity("??");
        this.person.getAddress().setAddressLine("???????161?20?201?");
    }

    private Person person = new Person();

    @Data
    public class Person {
        Person() {
            this.address = new Address();
        }

        private String id;
        private String name;
        private Address address;
    }

    @Data
    public class Address {
        private String id;
        private String country;
        private String city;
        private String addressLine;
    }
}