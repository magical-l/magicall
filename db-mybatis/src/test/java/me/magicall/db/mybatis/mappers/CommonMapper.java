package me.magicall.db.mybatis.mappers;

import java.util.List;
import java.util.Map;

import me.magicall.db.meta.TableMeta;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CommonMapper {
	@Select("show create table ${table}")
	Map<String, Object> showCreateTable(@Param("table") String tableName);

	@Select("select * from ${table.name} where id=#{id}")
	Map<String, Object> getById(@Param("table") TableMeta table, @Param("id") Number id);

	@Select("select * from ${table.name}")
	List<Map<String, Object>> getAll(@Param("table") TableMeta table);

}
