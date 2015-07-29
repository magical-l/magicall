package me.magicall.db.test;

interface C {

	/*
	 * select的语法：
	 * select [ all | distinct [ on ( expression [, ...] ) ] ] * | expression [ as output_name ] [, ...]
	 * [ into [ temporary | temp ] [ table ] new_table ]
	 * [ from from_item [, ...] ]
	 * [ where condition ]
	 * [ group by expression [, ...] ] [ having condition [, ...] ]
	 * [ { union | intersect | except [ all ] } select ]
	 * [ order by expression [ asc | desc | using operator ] [, ...] ]
	 * [ for update [ of class_name [, ...] ] ]
	 * [ limit { count | all } [ { offset | , } start ]]
	 */
	/**
	 * 我们使用简单select语法：
	 * select [ distinct ] field [, ...] [ as output_name ]
	 * from table [, ...]
	 * [ where condition ]
	 * [ group by expression [, ...] ]
	 * [ order by field [ asc | desc ] ]
	 * [ limit
	 */
	String SELECT = " SELECT ";
	String INSERT = " INSERT ";
	/*
	 * UPDATE{
	 * table_name WITH ( < * table_hint_limited > [ ...n ] )
	 * | view_name
	 * | rowset_function_limited
	 * }
	 * SET
	 * { column_name = { expression |
	 * DEFAULT | NULL }
	 * | @variable = expression
	 * | @variable = column = expression
	 * } [ ,...n ]
	 * { { [ FROM { < table_source > } [
	 * ,...n ] ]
	 * [ WHERE
	 * < search_condition > ] }
	 * |
	 * [ WHERE CURRENT OF
	 * { { [ GLOBAL ] cursor_name } |
	 * cursor_variable_name }
	 * ] }
	 * [ OPTION ( < query_hint > [
	 * ,...n ] ) ]
	 * < table_source > ::=
	 * table_name [ [ AS ] table_alias ] [
	 * WITH ( < table_hint > [ ,...n ] ) ]
	 * | view_name [ [ AS ] table_alias ]
	 * | rowset_function [ [ AS ] table_alias
	 * ]
	 * | derived_table [ AS ] table_alias [
	 * ( column_alias [ ,...n ] ) ]
	 * | < joined_table >
	 * < joined_table > ::=
	 * < table_source > < join_type > <
	 * table_source > ON < search_condition >
	 * | < table_source > CROSS JOIN <
	 * table_source >
	 * | < joined_table >
	 * < join_type > ::=
	 * [ INNER | { { LEFT | RIGHT | FULL
	 * } [OUTER] } ]
	 * [ < join_hint > ]
	 * JOIN
	 * < table_hint_limited > ::=
	 * { FASTFIRSTROW
	 * | HOLDLOCK
	 * | PAGLOCK
	 * | READCOMMITTED
	 * | REPEATABLEREAD
	 * | ROWLOCK
	 * | SERIALIZABLE
	 * | TABLOCK
	 * | TABLOCKX
	 * | UPDLOCK
	 * }
	 * < table_hint > ::=
	 * { INDEX ( index_val [ ,...n ] )
	 * | FASTFIRSTROW
	 * | HOLDLOCK
	 * | NOLOCK
	 * | PAGLOCK
	 * | READCOMMITTED
	 * | READPAST
	 * | READUNCOMMITTED
	 * | REPEATABLEREAD
	 * | ROWLOCK
	 * | SERIALIZABLE
	 * | TABLOCK
	 * | TABLOCKX
	 * | UPDLOCK
	 * }
	 * < query_hint > ::=
	 * { { HASH | ORDER } GROUP
	 * | { CONCAT | HASH | MERGE }
	 * UNION
	 * | {LOOP | MERGE | HASH } JOIN
	 * | FAST number_rows
	 * | FORCE ORDER
	 * | MAXDOP
	 * | ROBUST PLAN
	 * | KEEP PLAN
	 * }
	 */
	String UPDATE = " UPDATE ";
	/*
	 * delete 单表语法
	 * DELETE [LOW_PRIORITY] [QUICK] [IGNORE]
	 * FROM tbl_name
	 * [WHERE where_definition]
	 * [ORDER BY ...]
	 * [LIMIT row_count]
	 */
	String DELETE = " DELETE ";

	String FROM = " FROM ";

	String WHERE = " WHERE ";

	String ASC = " ASC ";
	String DESC = " DESC ";

	String LIMIT = " LIMIT ";

	String ORDER_BY = " ORDER BY ";

}
