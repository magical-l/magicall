package me.magicall.lang.exception;

public class ThrowableTranslateHandler<T extends Throwable> implements ThrowableHandler<T> {

	private String message;

	public ThrowableTranslateHandler(final String msg) {
		super();
		message = msg;
	}

	@Override
	public void handle(final T e) throws RuntimeException {
		throw new RuntimeException(message, e);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
