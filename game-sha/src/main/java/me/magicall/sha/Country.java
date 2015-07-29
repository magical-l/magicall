package me.magicall.sha;

import me.magicall.tagInterfaces.HasName;

public class Country implements HasName {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
