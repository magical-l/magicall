package db.magicall.db.outsea.springjdbc;

import me.magicall.db.Condition;
import me.magicall.db.ConditionOperator;
import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.outsea.CountSqlConfig;
import me.magicall.db.outsea.springjdbc.Count;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;
import me.magicall.util.time.TimeFormatter;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CountTest {
	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "test");
		tableMetaAccessor.build();

		final Count<MapModel<?>> dao = new Count<>(jdbc, tableMetaAccessor);

		final CountSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("ts");
		sqlConfig.addConditions(new Condition("now", ConditionOperator.BETWEEN,//
				TimeFormatter.Y2_M2_D2_H2_MIN2_S2.parse("2013-02-08 00:00:00"), TimeFormatter.Y2_M2_D2_H2_MIN2_S2.parse("2013-02-09 00:00:00")));

		System.out.println("@@@@@@CountTest.a():" + dao.exe(sqlConfig));
	}
}
