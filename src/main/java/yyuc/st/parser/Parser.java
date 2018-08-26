package yyuc.st.parser;

import com.google.common.base.Strings;

import java.util.ArrayList;

class Parser {
    private final Scanner scanner;
    private Scanner.Token currentToken;
    private int offset;
    private ArrayList<Node> fields = new ArrayList<>();

    Parser(String expression) {
        this.scanner = new Scanner(expression);
    }

    public CompositeNode build() throws TokenParseException {
        this.skipToken();
        Node text = buildTextNode();
        if (this.currentToken.isEOF()) {
            if (text == null) {
                text = new TextNode("");
            }
        }

        ArrayList<Node> list = new ArrayList<Node>();
        if (text != null) {
            list.add(text);
        }

        while (!this.currentToken.isEOF()) {
            list.add(this.buildExpressionNode());

            text = buildTextNode();

            if (text != null) {
                list.add(text);
            }
        }

        return new CompositeNode(list);
    }

    /**
     * Skip current token ange generate next token
     */
    private Scanner.Token skipToken() throws TokenParseException {
        Scanner.Token token = this.currentToken;
        this.currentToken = this.scanner.next();
        this.offset = this.scanner.getOffset();
        return token;
    }

    private Scanner.Token skipToken(Scanner.Symbol symbol) throws TokenParseException {
        if (this.currentToken.getSymbol() != symbol) {
            throw new TokenParseException("syntax error at position " + this.offset + ", expected symbol " + symbol);
        }

        return this.skipToken();
    }

    private Node buildTextNode() throws TokenParseException {
        TextNode node = null;
        if (this.currentToken.isText()) {
            node = new TextNode(this.currentToken.getContent());
            this.skipToken();
        }
        return node;
    }

    private Node buildExpressionNode() throws TokenParseException {
        Node node = null;
        if (this.currentToken.isExpStart()) {
            this.skipToken();
            node = new ExpressionNode(this.buildExpression());
            this.skipToken(Scanner.Symbol.ExpEnd);
        }
        return node;
    }

    private FieldNode buildExpression() throws TokenParseException {
        FieldNode node = this.buildFieldNode(null, this.skipToken().getContent());

        while (true) {
            if (this.currentToken.isDot()) {
                this.skipToken();
                String expression = this.skipToken().getContent();
                node = this.buildFieldNode(node, expression);

            } else {
                return node;
            }
        }
    }

    private FieldNode buildFieldNode(FieldNode parent, String content) {
        String[] expressions = content.trim().split("\\|");
        String fieldName = expressions[0];
        String format = null;
        String extension = null;

        if (expressions.length > 1) {
            format = expressions[1];
        }
        if (expressions.length > 2) {
            extension = expressions[2];
        }

        return new FieldNode(parent, fieldName, format, extension);
    }
}
