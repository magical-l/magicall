package me.magicall.db.test;

import me.magicall.db.test.sql.annotations.Table;
import me.magicall.db.test.sql.annotations.TypeRef;
import me.magicall.db.test.sql.annotations.UniqKey;

@Table("")
@UniqKey( { "id", "name,birthday" })
@TypeRef(dbFieldName = "", classFieldName = "")
public interface StrangeDao {

}
