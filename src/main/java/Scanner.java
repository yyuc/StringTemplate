public class Scanner {

    public class Token {
        private Symbol symbol;
        private String content;
        private int length;

        public Token(Symbol symbol, String content, int length) {
            this.symbol = symbol;
            this.content = content;
            this.length = length;
        }

        public Symbol getSymbol() {
            return this.symbol;
        }

        public String getContent() {
            return this.content;
        }

        public int getLength() {
            return this.length;
        }
    }

    public enum Symbol {
        EOF,
        Text,
        ExpStart("{{"),
        ExpEnd("}}");

        private Symbol() {
            this(null);
        }

        private Symbol(String desc) {
            this.desc = desc;
        }

        private String desc;
    }

    private final static Token Token_Start = new Token(Symbol.ExpStart, "{{", 2);
    private final static Token Token_ExpEnd = new Token(Symbol.ExpEnd, "}}", 2);
    private final static Token Token_EOF = new Token(Symbol.EOF, "", 0);

    private String expression;
    private int offset;
    private Token currentToken;

    public Scanner(String expression) {
        this.expression = expression;
    }

    public Token nextToken() {
        if (this.offset < this.expression.length() - 1
                && this.expression.charAt(this.offset) == '{'
                && this.expression.charAt(this.offset + 1) == '{') {
            return Token_Start;
        }

        return nextText();
    }

    public Token nextText() {
        int i = this.offset;
        StringBuilder builder = new StringBuilder();

        while (i < this.expression.length()) {
            char c = this.expression.charAt(i);
            if (c == '{' && this.expression.charAt(i + 1) == '{') {
                return new Token(Symbol.Text, builder.toString(), i - this.offset);
            } else {
                builder.append(c);
                i++;
            }
        }

        return new Token(Symbol.Text, builder.toString(), i - this.offset);
    }
}
