package me.magicall.db.springjdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.coll.ElementTransformer;
import me.magicall.db.Condition;
import me.magicall.db.ConditionOperator;
import me.magicall.db.OneFieldComparator;
import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.outsea.DelSqlConfig;
import me.magicall.db.outsea.ListSqlConfig;
import me.magicall.db.outsea.springjdbc.Del;
import me.magicall.db.outsea.springjdbc.GetList;
import me.magicall.db.outsea.springjdbc.Update;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;
import me.magicall.db.util.DbOrder;
import me.magicall.db.util.PageInfo;
import me.magicall.util.kit.Kits;
import me.magicall.util.time.TimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class GetListTest {

	GetList<MapModel<?>> dao;
	Update<MapModel<?>> update;
	Del<MapModel<?>> del;

	@Before
	public void before() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/anosi_asis?characterEncoding=gbk", "root",
				"1qazse4mysql");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		final NamedParameterJdbcOperations jdbc = new NamedParameterJdbcTemplate(dataSource);
		final SpringJdbcTableMetaAccessor tableMetaAccessor = new SpringJdbcTableMetaAccessor(dataSource, "anosi_asis");
		tableMetaAccessor.build();
		dao = new GetList<>(jdbc, tableMetaAccessor);
		dao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		update = new Update<>(jdbc, tableMetaAccessor);
		update.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		del = new Del<>(jdbc, tableMetaAccessor);
	}

	@Test
	public void c() {
		final List<MapModel<?>> devices = dao.exe("device");
		final Map<String, List<MapModel<?>>> anosiCodeToDeviceMap = new HashMap<>();
		for (final MapModel<?> device : devices) {
			final String anosiCode = device.get("anosiCode");
			final String trim = anosiCode.trim();
			List<MapModel<?>> list = anosiCodeToDeviceMap.get(trim);
			if (list == null) {
				list = new ArrayList<>();
				anosiCodeToDeviceMap.put(trim, list);
			}
			list.add(device);

			if (!anosiCode.equals(trim)) {
//				System.out.println("@@@@@@GetListTest.c():" + anosiCode);
			}
		}

		final Map<String, List<MapModel<?>>> map2 = new HashMap<>();
		final Map<Long, MapModel<?>> idToDevice = new HashMap<>();
		for (final Entry<String, List<MapModel<?>>> e : anosiCodeToDeviceMap.entrySet()) {
			final List<MapModel<?>> list = e.getValue();
			if (list.size() > 1) {
				map2.put(e.getKey(), list);
				for (final MapModel<?> device : list) {
					idToDevice.put(((Number) device.getId()).longValue(), device);
				}
			}
		}
		System.out.println("@@@@@@GetListTest.c():" + map2.size() + ' ' + idToDevice.size());

		_2(idToDevice, "service", "outDeviceId");
		_2(idToDevice, "repairTask", "deviceId");
		_2(idToDevice, "storeIn", "deviceId");
		_2(idToDevice, "storeOut", "deviceId");

		final Map<String, List<MapModel<?>>> notDel = new HashMap<>();
		final Collection<MapModel<?>> needToDel = new ArrayList<>();
		for (final Entry<Long, MapModel<?>> e : idToDevice.entrySet()) {
			final MapModel<?> device = e.getValue();
			final String anosiCode = device.get("anosiCode");
			final String trim = anosiCode.trim();

			final int servicesCount = Kits.COLL.checkToEmptyValue((Collection<?>) device.get("services")).size();
			final int repairTasksCount = Kits.COLL.checkToEmptyValue((Collection<?>) device.get("repairTasks")).size();
			if (servicesCount == 0 && repairTasksCount == 0 && !anosiCode.equals(trim)) {
				needToDel.add(device);
			} else {
				List<MapModel<?>> list = notDel.get(trim);
				if (list == null) {
					list = new ArrayList<>();
					notDel.put(trim, list);
				}
				list.add(device);
			}
		}

		System.out.println("@@@@@@GetListTest.c():" + notDel.size() + ' ' + needToDel.size());

		for (final Entry<String, List<MapModel<?>>> e : notDel.entrySet()) {
			if (e.getValue().size() > 1) {
				System.out.println("@@@@@@GetListTest.c():" + e.getKey() + ' ' + e.getValue().size());
			}
		}

		for (final MapModel<?> device : needToDel) {
			final String anosiCode = device.get("anosiCode");
			final String trim = anosiCode.trim();
			final List<MapModel<?>> list = notDel.get(trim);
			if (Kits.LIST.isEmpty(list)) {
				System.out.println("@@@@@@GetListTest.c():no del:" + device.getId() + ' ' + device.get("anosiCode"));
			}
			final Collection<MapModel<?>> storeIns = device.get("storeIns");
			if (!Kits.COLL.isEmpty(storeIns)) {
				final DelSqlConfig<MapModel<?>> sqlConfig = del.createSqlConfig("storeIn");
				sqlConfig.addConditions(new Condition("id", Kits.COLL.transform(storeIns,
						(index, element) -> element.getId())));
				System.out.println("@@@@@@GetListTest.c():delete storeIn:" + del.exe(sqlConfig));
			}
			final Collection<MapModel<?>> storeOuts = device.get("storeOuts");
			if (!Kits.COLL.isEmpty(storeOuts)) {
				final DelSqlConfig<MapModel<?>> sqlConfig = del.createSqlConfig("storeOut");
				sqlConfig.addConditions(new Condition("id", Kits.COLL.transform(storeOuts,
						(index, element) -> element.getId())));
				System.out.println("@@@@@@GetListTest.c():delete storeOut:" + del.exe(sqlConfig));
			}

			final DelSqlConfig<MapModel<?>> sqlConfig = del.createSqlConfig("device");
			sqlConfig.addConditions(new Condition("id", device.getId()));
			System.out.println("@@@@@@GetListTest.c():delete device:" + device.getId() + ' '
					+ device.get("anosiCode") + ' ' + del.exe(sqlConfig));
		}
	}

	private void _2(final Map<Long, MapModel<?>> map3, final String modelName, final String deviceIdField) {
		final ListSqlConfig<MapModel<?>> serviceSqlConfig = dao.createSqlConfig(modelName);
		serviceSqlConfig.addConditions(new Condition(deviceIdField, map3.keySet()));
		final List<MapModel<?>> models = dao.exe(serviceSqlConfig);
		_1(map3, models, modelName, deviceIdField);
	}

	private void _1(final Map<Long, MapModel<?>> map3, final List<MapModel<?>> models, final String modelsName, final String deviceIdField) {
		for (final MapModel<?> model : models) {
			final MapModel<?> device = map3.get(((Number) model.get(deviceIdField)).longValue());
			if (device == null) {
				System.out.println("@@@@@@GetListTest.a():");
			}
			List<MapModel<?>> list = device.get(modelsName + 's');
			if (list == null) {
				list = new ArrayList<>();
				device.set(modelsName + 's', list);
			}
			list.add(model);
		}
	}

//	@Test
	public void a() {
		dao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		final ListSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("service");
//		sqlConfig.addOtherModelsNames("service.outDevice",//
//				"service.outDevice.deviceProduct",//
//				"service.outDevice.deviceProduct.deviceType",//
//				"service.outDevice.deviceProduct.deviceBrand",//
//				"service.inDevice",//
//				"service.inDevice.deviceProduct",//
//				"service.inDevice.deviceProduct.deviceType",//
//				"service.inDevice.deviceProduct.deviceBrand"//
//		);
		sqlConfig.addConditions(new Condition("baseStationId", 65262));
		System.out.println("@@@@@@GetListTest.a():" + dao.exe(sqlConfig));
	}

//	@Test
	public void a2() {
		dao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		final ListSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("service");
//		sqlConfig.addOtherModelsNames("service.outDevice",//
//				"service.outDevice.deviceProduct",//
//				"service.outDevice.deviceProduct.deviceType",//
//				"service.outDevice.deviceProduct.deviceBrand",//
//				"service.inDevice",//
//				"service.inDevice.deviceProduct",//
//				"service.inDevice.deviceProduct.deviceType",//
//				"service.inDevice.deviceProduct.deviceBrand"//
//		);
		sqlConfig.addConditions(new Condition("service.baseStationId", 65262));
		System.out.println("@@@@@@GetListTest.a():" + dao.exe(sqlConfig));
	}

//	@Test
	public void b() {
		dao.setModelMapTransformer(MapModelModelMapTransformer.INSTANCE);

		final ListSqlConfig<MapModel<?>> sqlConfig = dao.createSqlConfig("service");
		sqlConfig.addOtherModelsNames("service.outDevice",//
				"service.outDevice.deviceProduct",//
				"service.outDevice.deviceProduct.deviceType",//
				"service.outDevice.deviceProduct.deviceBrand",//
				"service.inDevice",//
				"service.inDevice.deviceProduct",//
				"service.inDevice.deviceProduct.deviceType",//
				"service.inDevice.deviceProduct.deviceBrand"//
		);
		sqlConfig.addConditions(new Condition("service.addTime", ConditionOperator.BETWEEN, TimeFormatter.Y2_M2_D2_H2_MIN2_S2.parse("2012-12-28 12:00:00"),
				TimeFormatter.Y2_M2_D2_H2_MIN2_S2.parse("2012-12-29 00:00:00")));
		sqlConfig.addConditions(new Condition("service.id", 415, 416, 420));
		sqlConfig.setFieldComparator(new OneFieldComparator<>("service.id", DbOrder.DESC));
		sqlConfig.setPageInfo(PageInfo.FIRST_ONE);

		final List<MapModel<?>> models = dao.exe(sqlConfig);
		System.out.println("@@@@@@GetListTest.b():" + models.size());
		for (final MapModel<?> model : models) {
			System.out.println("@@@@@@GetListTest.b():" + model);
		}
	}
}
