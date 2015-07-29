package me.magicall.web.html;

public interface Node {

	String toHtml();

	StringBuilder toHtml(StringBuilder sb);

	Node appendTo(Node node);

	Node append(Node node);
}
