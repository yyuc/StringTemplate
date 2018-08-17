import java.util.ArrayList;

public class Parser {
    private final Scanner scanner;
    private Scanner.Token currentToken;
    private int offset;
    private ArrayList<Node> fields = new ArrayList<>();

    Parser(String expression) {
        this.scanner = new Scanner(expression);
    }

    public void Build() {
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

        //list.add(this.buildExpressionNode());

        while (!this.currentToken.isEOF()) {
            list.add(this.buildExpressionNode());

            text = buildTextNode();

            if (text != null) {
                list.add(text);
            }
        }
    }

    /**
     * Skip current token ange generate next token
     */
    private Scanner.Token skipToken() {
        Scanner.Token token = this.currentToken;
        this.currentToken = this.scanner.next();
        this.offset = this.scanner.getOffset();
        return token;
    }

    private Scanner.Token skipToken(Scanner.Symbol symbol) {
        if (this.currentToken.getSymbol() != symbol) {
            try {
                throw new Exception("Invalid token");
            } catch (Exception e) {

            }
        }

        return this.skipToken();
    }

    private Node buildTextNode() {
        TextNode node = null;
        if (this.currentToken.isText()) {
            node = new TextNode(this.currentToken.getContent());
            this.skipToken();
        }
        return node;
    }

    private Node buildExpressionNode() {
        Node node = null;
        if (this.currentToken.isExpStart()) {
            this.skipToken();
            node = new ExpressionNode(this.buildExp());
            this.skipToken(Scanner.Symbol.ExpEnd);
        }
        return node;
    }

    private FieldNode buildExp() {
        FieldNode node = new FieldNode(this.skipToken().getContent());

        while (true) {
            if (this.currentToken.isDot()) {
                this.skipToken();
                String fieldName = this.skipToken().getContent();
                node = new FieldNode(node, fieldName);

            } else {
                return node;
            }
        }
    }
}
