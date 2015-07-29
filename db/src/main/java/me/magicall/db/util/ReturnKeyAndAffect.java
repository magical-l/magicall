package me.magicall.db.util;

public class ReturnKeyAndAffect {
	public static final ReturnKeyAndAffect EMPTY = new ReturnKeyAndAffect(null, 0);

	private final Number returnKey;
	private final int affect;

	public ReturnKeyAndAffect(final Number returnKey, final int affect) {
		super();
		this.returnKey = returnKey;
		this.affect = affect;
	}

	public Number getReturnKey() {
		return returnKey;
	}

	public int getAffect() {
		return affect;
	}

	public boolean success() {
		return affect > 0;
	}

	@Override
	public String toString() {
		return "ReturnKeyAndAffect [returnKey=" + returnKey + ", affect=" + affect + ']';
	}
}
