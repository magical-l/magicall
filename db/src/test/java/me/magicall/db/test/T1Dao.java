/**
 * 
 */
package me.magicall.db.test;

import java.util.Collection;
import java.util.List;

import me.magicall.db.test.sql.annotations.Delete;
import me.magicall.db.test.sql.annotations.Insert;
import me.magicall.db.test.sql.annotations.InsertOnDuplicateKey;
import me.magicall.db.test.sql.annotations.Select;
import me.magicall.db.test.sql.annotations.SelectCount;
import me.magicall.db.test.sql.annotations.Table;
import me.magicall.db.test.sql.annotations.Update;

@Table("t1")
public interface T1Dao {
	@Select("id=:1")
	T1Interface getByKey(final int id);

	@Select("name=:1")
	List<T1Interface> getList(String name);

	@SelectCount
	int getAllCount();

	@SelectCount("id>=:1")
	int getCount(int smallestId);

	@Update
	int update(T1Interface bean);

	@Insert
	int add(T1Interface bean);

	@Insert
	int add(Collection<T1Interface> beans);

	@Insert
	int add(T1Interface... beans);

	@InsertOnDuplicateKey
	int addOrUpdate(T1Interface bean);

	@InsertOnDuplicateKey
	int addOrUpdate(T1Interface... beans);

	@InsertOnDuplicateKey
	int addOrUpdate(Collection<T1Interface> bean);

	@Delete("id=:1.id")
	int del(T1Interface bean);

	/**
	 * 仅根据key删除记录
	 * 
	 * @param id
	 * @return
	 */
	@Delete("id=:1")
	int del(int id);

	@Delete("id in (:1)")
	int delByIdIn(int... id);

	@Delete("id in (:1)")
	int delByIdIn(Collection<Integer> id);
}