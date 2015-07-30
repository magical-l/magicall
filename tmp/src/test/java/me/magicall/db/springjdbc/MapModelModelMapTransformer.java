package me.magicall.db.springjdbc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.db.model.BaseModel.MapModel;
import me.magicall.db.model.BaseModel.MapModel.IntIdMapModel;
import me.magicall.db.model.BaseModel.MapModel.LongIdMapModel;
import me.magicall.db.model.BaseModel.MapModel.StrIdMapModel;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.util.DbUtil;

public enum MapModelModelMapTransformer implements ModelMapTransformer<MapModel<?>> {

	INSTANCE;
	@Override
	public Map<String, Object> modelToMap(final MapModel<?> model) {
		return model;
	}

	@Override
	public MapModel<?> mapToModel(final Map<String, Object> resultMap, final String mainModelName) {
		final Map<String, MapModel<?>> modelNameToModel = new HashMap<>();
//		final int mainModelNameDotLen = mainModelName.length() + 1;
		//new and set id
		for (final Entry<String, Object> e : resultMap.entrySet()) {
			final String fieldName = e.getKey();
			if (fieldName.endsWith('.' + DbUtil.COMMON_ID_FIELD_NAME)) {
				final MapModel<?> model = newMapModelFromId(e.getValue());
				final int dotIndex = fieldName.lastIndexOf('.');
				final String modelName = fieldName.substring(0, dotIndex);
				modelNameToModel.put(modelName, model);
			}
		}
		//other fields
		for (final Entry<String, Object> e : resultMap.entrySet()) {
			final String fieldName = e.getKey();
			if (fieldName.endsWith('.' + DbUtil.COMMON_ID_FIELD_NAME) || fieldName.equals(DbUtil.COMMON_ID_FIELD_NAME)) {
				continue;
			}
			final int dotIndex = fieldName.lastIndexOf('.');
			assert dotIndex >= 0;
			final String modelName = fieldName.substring(0, dotIndex);
			final String shortFieldName = fieldName.substring(dotIndex + 1);
			final MapModel<?> model = modelNameToModel.get(modelName);
			model.set(shortFieldName, e.getValue());

//			final int dotIndex2 = fieldName.lastIndexOf('.', dotIndex - 1);
//			final String parentModelName = dotIndex2 >= 0 ? fieldName.substring(0, dotIndex2) : "";
//			final MapModel<?> parentModel = modelNameToModel.get(parentModelName);
//			if (parentModel != null) {
//				parentModel.set(modelName, model);
//			}
		}
		//层级关系
		for (final Entry<String, MapModel<?>> e : modelNameToModel.entrySet()) {
			final String modelName = e.getKey();
			//service.outDevice service.outDevice.deviceProduct service.outDevice.deviceProduct.deviceType
			final int dotIndex = modelName.lastIndexOf('.');
			if (dotIndex >= 0) {//not mainModel
				final String shortModelName = modelName.substring(dotIndex + 1);//deviceType
				final String parentModelName = modelName.substring(0, dotIndex);//service service.outDevice.deviceProduct
				final MapModel<?> mapModel = modelNameToModel.get(parentModelName);
				mapModel.set(shortModelName, e.getValue());
			}//esle is mainModel
		}
		return modelNameToModel.get(mainModelName);
	}

	private MapModel<?> newMapModelFromId(final Object value) {
		if (value instanceof Integer) {
			final IntIdMapModel model = new IntIdMapModel();
			model.setId((Integer) value);
			return model;
		} else if (value instanceof String) {
			final StrIdMapModel model = new StrIdMapModel();
			model.setId((String) value);
			return model;
		} else if (value instanceof Long) {
			final LongIdMapModel model = new LongIdMapModel();
			model.setId((Long) value);
			return model;
		} else {
			throw new IllegalStateException("id '" + value + "' is not int/long/string");
		}
	}
}
