public class Scanner {

    public static class Token {
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

        public boolean isText() {
            return this.getSymbol() == Symbol.Text;
        }

        public boolean isEOF() {
            return this.getSymbol() == Symbol.EOF;
        }

        public boolean isExp() {
            return this.getSymbol() == Symbol.Exp;
        }

        public boolean isDot() {
            return this.getSymbol() == Symbol.Dot;
        }

        public boolean isExpStart() {
            return this.getSymbol() == Symbol.ExpStart;
        }

        public boolean isExpEnd() {
            return this.getSymbol() == Symbol.ExpEnd;
        }

        @Override
        public String toString() {
            return String.format("%s: [%s]", symbol, this.getContent());
        }
    }

    public enum Symbol {
        EOF,
        Text,
        Exp,
        Dot("."),
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
    private final static Token Token_Dot = new Token(Symbol.Dot, ".", 1);

    private String expression;
    private int offset;
    private Token currentToken;

    public Scanner(String expression) {
        this.expression = expression;
    }

    public Token next() {
        if (this.currentToken != null) {
            this.offset = this.offset + this.currentToken.length;
        }

        int length = this.expression.length();

        if (this.offset == length) {
            return Token_EOF;
        }

        return currentToken = nextToken();
    }

    public Token nextToken() {
        if (this.currentToken != null && !this.currentToken.isText() && !this.currentToken.isExpEnd()) {
            if (this.offset < this.expression.length() - 1
                    && this.expression.charAt(this.offset) == '}'
                    && this.expression.charAt(this.offset + 1) == '}') {
                return Token_ExpEnd;
            }
            // Handle onely one } situation.
            return this.nextExp();
        }
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

    public Token nextExp() {
        char current = this.expression.charAt(this.offset);
        if (current == '.') {
            return Token_Dot;
        }

        int i = this.offset + 1;
        int length = this.expression.length();

        while (i < length && this.isPartOfField(this.expression.charAt(i))) {
            i++;
        }

        String content = this.expression.substring(this.offset, i);

        return new Token(Symbol.Exp, content, i - this.offset);
    }

    public int getOffset() {
        return this.offset;
    }

    private boolean isPartOfField(char c) {
        return c != '.' && c != '}' && c != '{';
    }

    private boolean isEvaluate() {
        return this.currentToken != null && this.currentToken.getSymbol() == Symbol.ExpStart;
    }
}
