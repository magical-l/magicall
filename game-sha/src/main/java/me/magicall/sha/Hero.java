package me.magicall.sha;

public class Hero {

	public static enum Gender {
		MALE, FEMALE;
	}

	private int hp;
	private Country country;
	private Gender gender;

	public int getHp() {
		return hp;
	}

	public void setHp(final int hp) {
		this.hp = hp;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}
}
