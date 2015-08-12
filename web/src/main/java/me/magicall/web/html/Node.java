package me.magicall.web.html;

import me.magicall.mark.Named;

import java.util.List;

/**
 * 一个DOM节点。
 *
 * @author Liang Wenjian
 */
public interface Node extends Named {

    String toHtml();

    default StringBuilder toHtml(final StringBuilder sb) {
        return sb.append(toHtml());
    }

    Node appendTo(Node node);

    Node append(Node node);

    List<Node> getChildren();
}
