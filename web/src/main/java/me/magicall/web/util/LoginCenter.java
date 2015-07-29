package me.magicall.web.util;

public interface LoginCenter<T> {

	boolean isLogin(String sessionId);

	T getAnosiLoginInfo(String sessionId);

	void login(String sessionId, T loginInfo);

	T logout(String sessionId);
}
