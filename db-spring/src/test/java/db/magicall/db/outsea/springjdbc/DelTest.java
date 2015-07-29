package db.magicall.db.outsea.springjdbc;

import me.magicall.db.Condition;
import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.outsea.DelSqlConfig;
import me.magicall.db.outsea.springjdbc.Del;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DelTest {

	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "test");
		tableMetaAccessor.build();

		final Del<MapModel<?>> dao = new Del<>(jdbc, tableMetaAccessor);
		final DelSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("ts");
		sqlConfig.addConditions(new Condition("id", 9));
		System.out.println("@@@@@@DelTest.a():" + dao.exe(sqlConfig));
	}
}
