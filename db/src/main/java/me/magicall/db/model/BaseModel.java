package me.magicall.db.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.magicall.db.util.DbUtil;
import me.magicall.mark.HasId;
import me.magicall.util.kit.Kits;

public abstract class BaseModel<I extends Comparable<I>> //
		implements Serializable, HasId<I>, Comparable<BaseModel<I>> {
	private BaseModel() {
		//只有本类中的子类能访问这个构造方法，因此保证本类只有下面这几个直接子类
	}

	@Override
	public int compareTo(final BaseModel<I> o) {
		final I myId = getId();
		final I oid = o.getId();
		if (myId == null) {
			if (oid == null) {
				return 0;
			}
			return -1;
		} else if (oid == null) {
			return 1;
		}
		return myId.compareTo(oid);
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (!(o instanceof BaseModel<?>)) {
			return false;
		}
		final BaseModel<?> m = (BaseModel<?>) o;
		final Object mid = m.getId();
		final I id = getId();
		if (id == null || mid == null) {
			return equalsWithoutId(m);
		}
		return id.equals(mid);
	}

	protected boolean equalsWithoutId(final BaseModel<?> o) {
		return false;
	}

	@Override
	public int hashCode() {
		final I id = getId();
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '_' + getId();
	}

//====================================vvv子类vvv
	private static abstract class ModelWithId<I extends Comparable<I>> extends BaseModel<I> {
		private static final long serialVersionUID = -8183920527657006525L;
		private I id;

		@Override
		public I getId() {
			return id;
		}

		@Override
		public void setId(final I id) {
			this.id = id;
		}
	}

	public static abstract class BaseModelInt extends ModelWithId<Integer> implements HasIntId {
		private static final long serialVersionUID = -3335060812677347294L;
	}

	public static abstract class BaseModelLong extends ModelWithId<Long> implements HasLongId {
		private static final long serialVersionUID = 880270866630158580L;
	}

	public static abstract class BaseModelStr extends ModelWithId<String> implements HasStrId {
		private static final long serialVersionUID = -2208156128375444345L;
	}

	public static class MapModel<I extends Comparable<I>> extends BaseModel<I> //
			implements Map<String, Object> {

		public static class IntIdMapModel extends MapModel<Integer> implements HasIntId {
			private static final long serialVersionUID = -641555134146233285L;

			public IntIdMapModel() {
				super();
			}

			public IntIdMapModel(final Map<String, Object> map) {
				super(map);
			}
		}

		public static class LongIdMapModel extends MapModel<Long> implements HasLongId {
			private static final long serialVersionUID = 9080094429820352252L;

			public LongIdMapModel(final Map<String, Object> map) {
				super(map);
			}

			public LongIdMapModel() {
				super();
			}
		}

		public static class StrIdMapModel extends MapModel<String> implements HasStrId {
			private static final long serialVersionUID = 3707560512269832411L;

			public StrIdMapModel(final Map<String, Object> map) {
				super(map);
			}

			public StrIdMapModel() {
				super();
			}
		}

		private static final long serialVersionUID = 6617409820590353108L;

		private final Map<String, Object> map;

		private MapModel() {
			this(new HashMap<>());
		}

		private MapModel(final Map<String, Object> map) {
			this.map = new HashMap<>(map);
		}

		@SuppressWarnings("unchecked")
		@Override
		public I getId() {
			return (I) map.get(idFieldName());
		}

		@Override
		public void setId(final I id) {
			map.put(idFieldName(), id);
		}

		public void copyField(final MapModel<?> otherMapModel, final String fieldName) {
			set(fieldName, otherMapModel.get(fieldName));
		}

		/**
		 * @return
		 */
		protected String idFieldName() {
			return DbUtil.COMMON_ID_FIELD_NAME;
		}

		@Override
		public Object get(final Object key) {
			if (key instanceof Enum<?>) {
				return map.get(((Enum<?>) key).name());
			}
			return map.get(key);
		}

		@SuppressWarnings("unchecked")
		public <T> T get(final String key) {
			return (T) map.get(key);
		}

		@Override
		public Object put(final String key, final Object value) {
			return map.put(key, value);
		}

		public Object set(final String key, final Object value) {
			return put(key, value);
		}

		public <E extends Enum<E>> Object set(final E key, final Object value) {
			return set(key.name(), value);
		}

		@Override
		public String toString() {
			return Kits.OBJ.deepToString(map);
		}

		@Override
		public void clear() {
			map.clear();
		}

		@Override
		public boolean containsKey(final Object key) {
			return map.containsKey(key);
		}

		@Override
		public boolean containsValue(final Object value) {
			return map.containsValue(value);
		}

		@Override
		public boolean isEmpty() {
			return size() == 0;
		}

		@Override
		public Object remove(final Object key) {
			return map.remove(key);
		}

		@Override
		public int size() {
			return map.size();
		}

		@Override
		public Collection<Object> values() {
			return map.values();
		}

		@Override
		public Set<Entry<String, Object>> entrySet() {
			return map.entrySet();
		}

		@Override
		public Set<String> keySet() {
			return map.keySet();
		}

		@Override
		public void putAll(final Map<? extends String, ? extends Object> m) {
			map.putAll(m);
		}
	}

//==================================^^^子类^^^
	private static final long serialVersionUID = -6416503568303970452L;
}
