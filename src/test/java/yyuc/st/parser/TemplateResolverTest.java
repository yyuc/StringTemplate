package yyuc.st.parser;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    public void ReturnReplacedTextWhenExpressionIsField() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        String result = resolver.resolve("Hi, {{name}}. Your id is {{id}}");
        Assert.assertEquals("Hi, rick.zhao. Your id is f3d84ae5-3431-4385-b83f-1385b73446e4", result);
    }

    @Test
    public void ReturnReplacedTextWhenExpressionIsInheritanceField() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        String result = resolver.resolve("My address is {{address.city}} {{address.addressLine}}");
        Assert.assertEquals("My address is 上海 浦东新区严杨路161弄20号201室", result);
    }

    @Test
    public void ReturnReplacedTextWhenExpressionIsFieldx() throws TokenParseException {
        TemplateResolver resolver = new TemplateResolver(this.person);
        String result = resolver.resolve("My salary is {{salary ,###\\.##}}");
        Assert.assertEquals("My salary is 2,228,523.90", result);
    }

    public TemplateResolverTest() {
        this.person.setId("f3d84ae5-3431-4385-b83f-1385b73446e4");
        this.person.setName("rick.zhao");
        this.person.setSalary(BigDecimal.valueOf(2228523.90));
        this.person.setBirth(ZonedDateTime.now(ZoneOffset.UTC));
        this.person.getAddress().setId(UUID.randomUUID().toString());
        this.person.getAddress().setCountry("中国");
        this.person.getAddress().setCity("上海");
        this.person.getAddress().setAddressLine("浦东新区严杨路161弄20号201室");
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
        private BigDecimal salary;
        private ZonedDateTime birth;
    }

    @Data
    public class Address {
        private String id;
        private String country;
        private String city;
        private String addressLine;
    }
}