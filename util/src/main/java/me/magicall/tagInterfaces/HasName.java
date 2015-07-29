package me.magicall.tagInterfaces;

/**
 * 实现此接口,表示model是有名字字段的
 * 
 * @author MaGiCalL
 * @email wenjian.liang@opi-corp.com
 * @version Jun 17, 2011 9:48:04 PM
 */
public interface HasName extends HasNameGetter {

	void setName(String name);
}
