package me.magicall.game.sub.chess.util;

import me.magicall.game.sub.chess.ChessBoard;
import me.magicall.util.kit.StrKit;

public class ChessBoardDisplayer {
	public static final ChessBoardDisplayer INSTANCE = new ChessBoardDisplayer();

	public static final String NEW_LINE = StrKit.newLine();

	public static final char SPACE = '　';//全角的

	public static final char VERTICAL_LINE = '│';
	public static final char HORIZONAL_LINE = '─';

	public static final char LEFT_HEAD = '┌';
	public static final char RIGHT_HEAD = '┐';
	public static final char LEFT_BOTTOM = '└';
	public static final char RIGHT_BOTTOM = '┘';

	public static final char HEAD_CROSS = '┬';
	public static final char LEFT_CROSS = '├';
	public static final char RIGHT_CROSS = '┤';
	public static final char BOTTOM_CROSS = '┴';
	public static final char MIDDLE_CROSS = '┼';

	public String toString(final ChessBoard board) {
		return toString(toCharSquare(board));
	}

	public String toString(final char[][] charSquare) {
		return toStringBuilder(charSquare).toString();
	}

	public StringBuilder toStringBuilder(final char[][] charSquare) {
		final StringBuilder sb = new StringBuilder();
		for (final char[] cs : charSquare) {
			for (final char c : cs) {
				sb.append(c);
			}
			sb.append(NEW_LINE);
		}
		return sb;
	}

	public char[][] toCharSquare(final ChessBoard board) {
		final int rowCount = getRowCount(board);
		final int columnCount = getColumnCount(board);

		final char[][] c = new char[rowCount][columnCount];

		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
				c[rowIndex][columnIndex] = charOfPosition(board, rowIndex, columnIndex);
			}//for j
		}//for i
		return c;
	}

	protected int getMaxRowIndex(final ChessBoard board) {
		return getRowCount(board) - 1;
	}

	protected int getMaxColumnIndex(final ChessBoard board) {
		return getColumnCount(board) - 1;
	}

	protected int getRowCount(final ChessBoard board) {
		return board.getRowsCount();
	}

	protected int getColumnCount(final ChessBoard board) {
		return board.getColumnsCount();
	}

	protected char charOfPosition(final ChessBoard board, final int rowIndex, final int columnIndex) {
		final int maxRowIndex = getMaxRowIndex(board);
		final int maxColumnIndex = getMaxColumnIndex(board);

		final char ch;
		if (rowIndex == 0) {
			if (columnIndex == 0) {
				ch = getLeftHeadChar(board, rowIndex, columnIndex);
			} else if (columnIndex == maxColumnIndex) {
				ch = getRightHeadChar(board, rowIndex, columnIndex);
			} else if (isColumnIndexInCross(board, columnIndex)) {
				ch = getHeadCrossChar(board, rowIndex, columnIndex);
			} else {
				ch = getHorizonalLineChar(board, rowIndex, columnIndex);
			}
		} else if (rowIndex == maxRowIndex) {
			if (columnIndex == 0) {
				ch = getLeftBottomChar(board, rowIndex, columnIndex);
			} else if (columnIndex == maxColumnIndex) {
				ch = getRightBottomChar(board, rowIndex, columnIndex);
			} else if (isColumnIndexInCross(board, columnIndex)) {
				ch = getBottomCrossChar(board, rowIndex, columnIndex);
			} else {
				ch = getHorizonalLineChar(board, rowIndex, columnIndex);
			}
		} else if (isRowIndexInCross(board, rowIndex)) {
			if (columnIndex == 0) {
				ch = getLeftCrossChar(board, rowIndex, columnIndex);
			} else if (columnIndex == maxColumnIndex) {
				ch = getRightCrossChar(board, rowIndex, columnIndex);
			} else if (isColumnIndexInCross(board, columnIndex)) {
				ch = getMiddleCrossChar(board, rowIndex, columnIndex);
			} else {
				ch = getHorizonalLineChar(board, rowIndex, columnIndex);
			}
		} else {
			if (isColumnIndexInCross(board, columnIndex)) {
				ch = getVerticalLineChar(board, rowIndex, columnIndex);
			} else {
				ch = getSpaceChar(board, rowIndex, columnIndex);
			}
		}
		return ch;
	}

	protected char getSpaceChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return SPACE;
	}

	protected char getVerticalLineChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return VERTICAL_LINE;
	}

	protected char getMiddleCrossChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return MIDDLE_CROSS;
	}

	protected char getRightCrossChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return RIGHT_CROSS;
	}

	protected char getLeftCrossChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return LEFT_CROSS;
	}

	protected char getBottomCrossChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return BOTTOM_CROSS;
	}

	protected char getRightBottomChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return RIGHT_BOTTOM;
	}

	protected char getLeftBottomChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return LEFT_BOTTOM;
	}

	protected char getHorizonalLineChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return HORIZONAL_LINE;
	}

	protected char getHeadCrossChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return HEAD_CROSS;
	}

	protected char getRightHeadChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return RIGHT_HEAD;
	}

	protected char getLeftHeadChar(final ChessBoard board, final int rowIndex, final int columnIndex) {
		return LEFT_HEAD;
	}

	protected boolean isColumnIndexInCross(final ChessBoard board, final int columnIndex) {
		return true;
	}

	protected boolean isRowIndexInCross(final ChessBoard board, final int rowIndex) {
		return true;
	}

}
