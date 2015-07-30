package me.magicall.db.springjdbc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.magicall.consts.StrConst.EncodingConst;
import me.magicall.db.dbms.CommonDBMS;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.springjdbc.SpringJdbcTableMetaAccessor;
import me.magicall.util.kit.Kits;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class TestSpringJdbcTableMetaAccessor {

	@Test
	public void test() {
		final DriverManagerDataSource ds = new DriverManagerDataSource();
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(CommonDBMS.MYSQL.getDriverClassName());
		dataSource.setPassword("1qazse4mysql");
		dataSource.setUrl("jdbc:mysql://localhost:3306/anosi_asis?characterEncoding=gbk");
		dataSource.setUsername("root");
		final SpringJdbcTableMetaAccessor accessor = new SpringJdbcTableMetaAccessor(dataSource, "anosi_asis");
		{//连接数据库
			ds.setDriverClassName(CommonDBMS.MYSQL.getDriverClassName());
			ds.setUsername(dataSource.getUsername());
			ds.setPassword(dataSource.getPassword());
			final String url = dataSource.getUrl();
			final int slashIndex = url.indexOf("//");
			final int colonIndex = url.indexOf(":", slashIndex);
			final int slash2Index = url.indexOf("/", colonIndex);
			int portEndIndex;
			if (slash2Index < 0) {
				portEndIndex = url.indexOf("?", colonIndex);
				if (portEndIndex < 0) {
					portEndIndex = url.length();
				}
			} else {
				portEndIndex = slash2Index;
			}
			ds.setUrl(CommonDBMS.MYSQL.formatUrl(url.substring(slashIndex + 2, colonIndex),//
					Kits.INT.fromString(url.substring(colonIndex + 1, portEndIndex)),//port
					"information_schema",//database name
					Collections.singletonMap("characterEncoding", EncodingConst.GBK)));
		}
		final Map<String, TableMeta> tableMap = new HashMap<>();
		accessor.buildView(new JdbcTemplate(ds), tableMap);
	}

	static final Pattern VIEW_DEFINITION_PATTERN = Pattern.compile(//
			"\\(select\\s+(.+)\\s*"//
					+ "\\s+from\\s+(.+)\\s*"//
					+ "(?:(?:\\s+where\\s+\\((.+)\\))+)*"//
					+ "\\)"//
			,//
			Pattern.CASE_INSENSITIVE);
	static final Pattern JOIN_PATTERN = Pattern.compile("(?:join\\s+)?`[a-zA-Z0-9_]+`\\.`([a-zA-Z0-9_]+)`\\s+`([a-zA-Z0-9_]+)`\\s*", Pattern.CASE_INSENSITIVE)//
	;

	static void testSelect() {
		final String s = "(select `p`.`id` AS `id`,`p`.`xinghao` AS `xinghao`,`b`.`name` AS `device_brand_name`,`t`.`name` AS `device_type_name` from `anosi_asis`.`device_product` `p` join `anosi_asis`.`device_brand` `b` join `anosi_asis`.`device_type` `t` where ((`p`.`device_type_id` = `t`.`id`) and (`p`.`device_brand_id` = `b`.`id`)))";
//		final String s = "(select `anosi_asis`.`staff`.`id` AS `id`,`anosi_asis`.`staff`.`name` AS `name`,`anosi_asis`.`staff`.`role_id` AS `role_id`,`anosi_asis`.`staff`.`region_id` AS `region_id`,`anosi_asis`.`staff`.`phone` AS `phone`,`anosi_asis`.`staff`.`email` AS `email` from `anosi_asis`.`staff`)";
		final Matcher matcher = VIEW_DEFINITION_PATTERN.matcher(s);
		System.out.println("@@@@@@TestSpringJdbcTableMetaAccessor.testSelect():" + matcher.matches());
		System.out.println("@@@@@@TestSpringJdbcTableMetaAccessor.testSelect():" + matcher.group(1));
		System.out.println("@@@@@@TestSpringJdbcTableMetaAccessor.testSelect():" + matcher.group(2));
	}

	public static void main(final String... args) {
		testSelect();
//		testFrom();
	}

	/**
	 * 
	 */
	protected static void testFrom() {
		final String s = "from `anosi_asis`.`device_product` `p` "//
				+ "join `anosi_asis`.`device_brand` `b` "//
				+ "join `anosi_asis`.`device_type` `t`"//
		;
		final Matcher matcher = JOIN_PATTERN.matcher(s);
		while (matcher.find()) {
			System.out.println("@@@@@@TestSpringJdbcTableMetaAccessor.main():find");
//			System.out.println("@@@@@@TestSpringJdbcTableMetaAccessor.main():" + s.substring(matcher.start(), matcher.end()));
			for (int i = 1; i <= matcher.groupCount(); ++i) {
				System.out.println("@@@@@@TestSpringJdbcTableMetaAccessor.main():" + i + ' ' + matcher.group(i));
			}
		}
	}
}
