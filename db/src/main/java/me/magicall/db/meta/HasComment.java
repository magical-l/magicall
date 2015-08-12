package me.magicall.db.meta;

/**
 * 在数据库中有"注释"的东东
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface HasComment {

	String getComment();
}
