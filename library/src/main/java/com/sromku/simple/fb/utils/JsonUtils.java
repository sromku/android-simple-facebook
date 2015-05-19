package com.sromku.simple.fb.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class JsonUtils {

    private static Gson buildGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        return gson;
    }

	/**
	 * Parse Object to String in JSON format
	 * 
	 * @param obj
	 * @return String in JSON format
	 */
	public static String toJson(Object obj) {
		Gson gson = buildGson();
		return gson.toJson(obj);
	}

	/**
	 * Get JSON string and convert to T (Object) you need
	 * 
	 * @param json
	 * @return Object filled with JSON string data
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		Gson gson = buildGson();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);

		// check if the Class type is array but the Json is an object
		if (cls != null && cls.isArray() && element instanceof JsonArray == false) {
			JsonArray jsonArray = new JsonArray();
			jsonArray.add(element);

			Type listType = new TypeToken<T>() {
			}.getType();
			return gson.fromJson(jsonArray, listType);
		}

		return gson.fromJson(json, cls);
	}

	/**
	 * Get JSON string and convert to T (Object) you need
	 * 
	 * @param json
	 * @return Object filled with JSON string data
	 */
	public static <T> T fromJson(byte[] json, Class<T> cls) {
		try {
			String decoded = new String(json, "UTF-8");
			return fromJson(decoded, cls);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get JSON string and convert to T (Object) you need
	 * 
	 * @return Object filled with JSON string data
	 */
	public static <T> T fromJson(byte[] bytes, Type type) {
		try {
			String decoded = new String(bytes, "UTF-8");
			return fromJson(decoded, type);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get JSON string and convert to T (Object) you need. <br>
	 * <br>
	 * Using generic class type example:<br>
	 * <code>
	 * Type collectionType = new TypeToken{@code <}APIResponse{@code <}DateInfo>>() {}.getType();
	 * </code>
	 * 
	 * @param json
	 * @return Object filled with JSON string data
	 */
	public static <T> T fromJson(String json, Type type) {
		Gson gson = buildGson();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);

		return gson.fromJson(element, type);
	}

	/**
	 * Build object from json and inoke to fields that are marked with @Expose
	 * annotation
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T fromJsonExcludeFields(String json, Class<T> cls) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);

		// check if the Class type is array but the Json is an object
		if (cls.isArray() && element instanceof JsonArray == false) {
			JsonArray jsonArray = new JsonArray();
			jsonArray.add(element);

			Type listType = new TypeToken<T>() {
			}.getType();
			return gson.fromJson(jsonArray, listType);
		}

		return gson.fromJson(json, cls);
	}

    private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private final DateFormat dateFormat1;
        private final DateFormat dateFormat2;

        private DateTypeAdapter() {
            dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            dateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));

            dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            dateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override public synchronized JsonElement serialize(Date date, Type type,
                                                            JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat2.format(date));
        }

        @Override public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                                       JsonDeserializationContext jsonDeserializationContext) {
            try {
                return dateFormat2.parse(jsonElement.getAsString());
            } catch (ParseException e) {
                try {
                    return dateFormat1.parse(jsonElement.getAsString());
                } catch (ParseException e1) {
                    throw new JsonParseException(e1);
                }
            }
        }
    }

}
