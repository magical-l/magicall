/**
 *
 */
package me.magicall.web.html;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个很简单很简单的html表格,简单到令人发笑!
 *
 * @author Liang Wenjian
 */
public class Table {

    private String title;
    private final List<List<Object>> table;
    private List<Object> curRow;

    public Table() {
        table = new ArrayList<>();
    }

    public Table addRow() {
        curRow = new ArrayList<>();
        table.add(curRow);
        return this;
    }

    public Table addCell(final Object element) {
        curRow.add(element);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (title != null) {
            sb.append(title).append("<br/>");
        }
        sb.append("<table border='1'>");
        for (final List<Object> row : table) {
            sb.append("<tr>");
            for (final Object cell : row) {
                sb.append("<td>").append(cell).append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>").append("<br/>");
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public Table setTitle(final String title) {
        this.title = title;
        return this;
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }
}
