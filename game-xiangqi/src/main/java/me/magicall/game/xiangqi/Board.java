package me.magicall.game.xiangqi;

import me.magicall.game.sub.chess.CommonChessBoard;
import me.magicall.game.xiangqi.ChessmanFactory.XiangqiChessman;

public class Board extends CommonChessBoard<Xiangqi, Board, XiangqiChessman> {

	public Board() {
		super(Util.MAX_PLAYERS_COUNT, Util.ROWS, Util.COLUMNS);
	}
}
