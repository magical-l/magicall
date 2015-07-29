package me.magicall.net.surf;

public class UserNamePassword {

	private String userName;
	private String password;

	public UserNamePassword() {
		super();
	}

	public UserNamePassword(final String userName, final String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
