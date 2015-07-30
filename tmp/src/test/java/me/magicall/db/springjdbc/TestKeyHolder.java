package me.magicall.db.springjdbc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class TestKeyHolder {

	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final Map<String, Object> params = new HashMap<>();
		params.put("a", 9);
		params.put("b", null);
		params.put("c", null);
		params.put("d", null);
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update("insert into ts(id,now)values(:a,:b),(:c,:d)on duplicate key update `now`=values(`now`)",//
				new MapSqlParameterSource(params), keyHolder);
		System.out.println("@@@@@@Add.main():" + keyHolder.getKeyList());

	}
}
