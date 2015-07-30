package me.magicall.game.xiangqi;

import me.magicall.game.player.PlayerRole;
import me.magicall.game.sub.chess.BaseChessman;

public class ChessmanFactory {

	public static class XiangqiChessman extends BaseChessman {

		private XiangqiChessman(final PlayerRole owner) {
			super(owner.getName());
		}

		private XiangqiChessman(final String name, final PlayerRole owner) {
			super(name, null);//TODO
		}
	}

//	public static class 帥 extends XiangqiChessman {
//	}
//
//	public static class 将 extends 帥 {
//	}
//
//	public static class 士 extends XiangqiChessman {
//	}
//
//	public static class 相 extends XiangqiChessman {
//	}
//
//	public static class 象 extends 相 {
//	}
//
//	public static class 車 extends XiangqiChessman {
//	}
//
//	public static class 馬 extends XiangqiChessman {
//	}
//
//	public static class 炮 extends XiangqiChessman {
//	}
//
//	public static class 兵 extends XiangqiChessman {
//	}
//
//	public static class 卒 extends 兵 {
//	}
}
