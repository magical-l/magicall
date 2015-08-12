package me.magicall.sha;

import me.magicall.mark.Renamable;

public class Country implements Renamable {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}
}
