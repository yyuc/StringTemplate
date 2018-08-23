package yyuc.st.parser;

import java.util.ArrayList;

final class CompositeNode extends Node {
    private ArrayList<Node> nodes;

    CompositeNode(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    String eval(Object object) {
        return this.resolveValue(object).toString();
    }

    @Override
    protected Object resolveValue(Object object) {
        if (this.nodes == null || this.nodes.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (Node node : nodes) {
            builder.append(node.resolveValue(object));
        }

        return builder.toString();
    }
}
