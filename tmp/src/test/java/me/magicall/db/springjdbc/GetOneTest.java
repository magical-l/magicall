package me.magicall.db.springjdbc;

import java.util.Map;

import me.magicall.db.Condition;
import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.model.BaseModel.MapModel.IntIdMapModel;
import me.magicall.db.outsea.GetOneSqlConfig;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.outsea.springjdbc.GetOne;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class GetOneTest {
	@Test
	public void b() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/anosi_asis?characterEncoding=gbk", "root",
				"1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "anosi_asis");
		tableMetaAccessor.build();

		final GetOne<MapModel<?>> getOneDao = new GetOne<>(jdbc, tableMetaAccessor);
		getOneDao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		final GetOneSqlConfig<MapModel<?>> sqlConfig = getOneDao.createSqlConfig("service");
		sqlConfig.addOtherModelsNames("service.outDevice",//
				"service.outDevice.deviceProduct",//
				"service.outDevice.deviceProduct.deviceType",//
				"service.outDevice.deviceProduct.deviceBrand",//
				"service.inDevice",//
				"service.inDevice.deviceProduct",//
				"service.inDevice.deviceProduct.deviceType",//
				"service.inDevice.deviceProduct.deviceBrand"//
		);
		sqlConfig.addConditions(new Condition("service.id", 1));

		final MapModel<?> model = getOneDao.exe(sqlConfig);
		System.out.println("@@@@@@GetOneTest.b():" + model);
	}

//	@Test
	public void a() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test?characterEncoding=gbk", "root", "1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "test");
		tableMetaAccessor.build();

		final GetOne<MapModel<?>> getOneDao = new GetOne<>(jdbc, tableMetaAccessor);
		getOneDao.setModelMapTransformer(new ModelMapTransformer<MapModel<?>>() {

			@Override
			public Map<String, Object> modelToMap(final MapModel<?> model) {
				return model;
			}

			@Override
			public MapModel<?> mapToModel(final Map<String, Object> map, final String mainModelName) {
				return new IntIdMapModel(map);
			}
		});

		final GetOneSqlConfig<MapModel<?>> sqlConfig = getOneDao.createSqlConfig("ts");
		sqlConfig.addConditions(new Condition("id", 1));

		final MapModel<?> model = getOneDao.exe(sqlConfig);
		System.out.println("@@@@@@GetOneTest.a():" + model);
	}
}
