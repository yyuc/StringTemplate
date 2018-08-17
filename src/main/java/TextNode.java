public class TextNode extends Node {

    public TextNode(String content) {
        super(content);
    }

    @Override
    public Node getChild() {
        return null;
    }
}
