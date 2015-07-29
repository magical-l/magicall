package me.magicall.db.outsea.springjdbc;

import org.springframework.dao.DataAccessException;

public class EmptyValueException extends DataAccessException {

	private static final long serialVersionUID = -4149138921170993933L;

	public final Object item;

	public EmptyValueException(final Object item) {
		super(item + "不能为空");
		this.item = item;
	}

	public EmptyValueException(final Object item, final Throwable cause) {
		super(item + "不能为空", cause);
		this.item = item;
	}
}
