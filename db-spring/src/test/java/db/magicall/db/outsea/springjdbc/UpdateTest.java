package db.magicall.db.outsea.springjdbc;

import java.util.Date;

import me.magicall.db.Condition;
import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.model.BaseModel.MapModel.IntIdMapModel;
import me.magicall.db.outsea.UpdateSqlConfig;
import me.magicall.db.outsea.springjdbc.Update;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class UpdateTest {

	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "test");
		tableMetaAccessor.build();

		final Update<MapModel<?>> dao = new Update<>(jdbc, tableMetaAccessor);
		dao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		final UpdateSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("ts");
		sqlConfig.addConditions(new Condition("id", 1));
		final IntIdMapModel newValue = new IntIdMapModel();
		newValue.setId(1);
		newValue.set("now", new Date());
		sqlConfig.setRefedModel(newValue);

		System.out.println("@@@@@@UpdateTest.a():" + dao.exe(sqlConfig));
	}
}
