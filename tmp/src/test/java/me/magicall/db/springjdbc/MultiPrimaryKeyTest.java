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

public class MultiPrimaryKeyTest {
	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final Map<String, Object> params = new HashMap<>();
		params.put("a", null);
		params.put("b", "a");
		params.put("c", null);
		params.put("d", "b");
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		System.out.println("@@@@@@MultiPrimaryKeyTest.a():" + jdbc.update("insert into multi_primary_key(id,name)values(:a,:b),(:c,:d)",//
				new MapSqlParameterSource(params), keyHolder));
		System.out.println("@@@@@@Add.main():" + keyHolder.getKeyList());

	}
}
