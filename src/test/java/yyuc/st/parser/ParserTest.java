package yyuc.st.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ParserTest {
    @Test
    public void someMethod() throws TokenParseException {
        Parser parser = new Parser("亲爱的{{message.customerName}}，感谢您在Farfetch购物，我们正在处理您的订单。");
        ArrayList<Node> nodes = parser.build();
        Assert.assertEquals(1, nodes.size());
    }
}