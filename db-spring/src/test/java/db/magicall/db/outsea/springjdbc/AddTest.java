package db.magicall.db.outsea.springjdbc;

import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.model.BaseModel.MapModel.IntIdMapModel;
import me.magicall.db.outsea.AddSqlConfig;
import me.magicall.db.outsea.springjdbc.Add;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;
import me.magicall.lang.bean.FieldValueAccessor;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class AddTest {

	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "test");
		tableMetaAccessor.build();

		final IntIdMapModel newValue = new IntIdMapModel();

		final Add<MapModel<?>> dao = new Add<>(jdbc, tableMetaAccessor);
		dao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);
		dao.setFieldValueAccessor(FieldValueAccessor.MAP_VALUE_ACCESSOR);

		final AddSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("ts");
		sqlConfig.setRefedModel(newValue);

		System.out.println("@@@@@@AddTest.a():" + dao.exe(sqlConfig));
		System.out.println("@@@@@@AddTest.a():" + newValue);
	}
}
