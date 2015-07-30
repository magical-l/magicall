package me.magicall.sha;

import me.magicall.mark.HasName;

public class Country implements HasName {

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
