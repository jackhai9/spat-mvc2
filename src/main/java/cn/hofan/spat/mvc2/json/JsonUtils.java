package cn.hofan.spat.mvc2.json;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class JsonUtils {
	
	private static Log log = LogFactory.getLog(JsonUtils.class);

	public static <K, V> String map2Json(Map<K, V> obj) {
		String jsonString = "";
		try {
			if (obj == null)
				return "";
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(gen, obj);
			jsonString = sw.toString();
			sw.close();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}

		return jsonString;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> void json2Map(String json, Map<K, V> hm) {
		try {
			if (json == null || "".equals(json))
				return;
			ObjectMapper objectMapper = new ObjectMapper();
			Map<K, V> maps = objectMapper.readValue(json, Map.class);
			if (maps != null) {
				hm.putAll(maps);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
	}

	public static String object2Json(Object object) {
		String json = "";
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			ObjectMapper mapper = new ObjectMapper();
			mapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.writeValue(gen, object);

			json = sw.toString();
			sw.close();
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
		return json;
	}

	public static <T> T json2Object(String json, Class<T> clazz) {
		T obj = null;
		try {
			if(json == null){
				return null;
			}
			if(json.isEmpty()){
				return null;
			}
			JsonFactory jsonFactory = new MappingJsonFactory();
			JsonParser jsonParser = jsonFactory.createJsonParser(json);
			ObjectMapper mapper = new ObjectMapper();
			mapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			obj = mapper.readValue(jsonParser, clazz);

		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
		return obj;

	}
	
	public static <T>  List<T> json2List(String json, Class<T> clazz) {
		List<T> list =  null;
		try {
			JsonFactory jsonFactory = new MappingJsonFactory();
			JsonParser jsonParser = jsonFactory.createJsonParser(json);
			ObjectMapper mapper = new ObjectMapper();
			mapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			list = mapper.readValue(jsonParser, TypeFactory.collectionType(ArrayList.class, clazz));
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
		if(list == null){
			list = new ArrayList<T>();
		}
		return list;

	}
}
