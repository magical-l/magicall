package me.magicall.db.outsea;

import me.magicall.db.Condition;

public interface ParamParser {

	boolean accept(String paramName, Object paramValue);

	Condition parse(String paramName, Object paramValue);
}
