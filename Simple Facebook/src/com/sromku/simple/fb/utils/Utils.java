package com.sromku.simple.fb.utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;

import com.facebook.Response;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.sromku.simple.fb.entities.User;

public class Utils {
    public static final String EMPTY = "";

    public String getFacebookSDKVersion() {
	String sdkVersion = null;
	ClassLoader classLoader = getClass().getClassLoader();
	try {
	    Class<?> cls = classLoader.loadClass("com.facebook.FacebookSdkVersion");
	    Field field = cls.getField("BUILD");
	    sdkVersion = String.valueOf(field.get(null));
	}
	catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	catch (NoSuchFieldException e) {
	    e.printStackTrace();
	}
	catch (IllegalArgumentException e) {
	    e.printStackTrace();
	}
	catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
	return sdkVersion;
    }

    public static String getHashKey(Context context) {
	// Add code to print out the key hash
	try {
	    PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
	    for (Signature signature : info.signatures) {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(signature.toByteArray());
		return Base64.encodeToString(md.digest(), Base64.DEFAULT);
	    }
	}
	catch (NameNotFoundException e) {

	}
	catch (NoSuchAlgorithmException e) {

	}
	return null;
    }

    /**
     * <p>
     * Joins the elements of the provided {@code Iterator} into a single String
     * containing the provided elements.
     * </p>
     * 
     * <p>
     * No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.
     * </p>
     * 
     * @param iterator
     *            the {@code Iterator} of values to join together, may be null
     * @param separator
     *            the separator character to use
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(Iterator<?> iterator, char separator) {
	if (iterator == null) {
	    return null;
	}
	if (!iterator.hasNext()) {
	    return EMPTY;
	}
	Object first = iterator.next();
	if (!iterator.hasNext()) {
	    return first == null ? EMPTY : first.toString();
	}
	StringBuilder buf = new StringBuilder(256);
	if (first != null) {
	    buf.append(first);
	}
	while (iterator.hasNext()) {
	    buf.append(separator);
	    Object obj = iterator.next();
	    if (obj != null) {
		buf.append(obj);
	    }
	}
	return buf.toString();
    }

    public static String join(Map<?, ?> map, char separator, char valueStartChar, char valueEndChar) {

	if (map == null) {
	    return null;
	}
	if (map.size() == 0) {
	    return EMPTY;
	}
	StringBuilder buf = new StringBuilder(256);
	boolean isFirst = true;
	for (Entry<?, ?> entry : map.entrySet()) {
	    if (isFirst) {
		buf.append(entry.getKey());
		buf.append(valueStartChar);
		buf.append(entry.getValue());
		buf.append(valueEndChar);
		isFirst = false;
	    }
	    else {
		buf.append(separator);
		buf.append(entry.getKey());
		buf.append(valueStartChar);
		buf.append(entry.getValue());
		buf.append(valueEndChar);
	    }
	}

	return buf.toString();
    }

    public static <T extends GraphObject> List<T> typedListFromResponse(Response response, Class<T> clazz) {
	GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
	if (multiResult == null) {
	    return null;
	}
	GraphObjectList<GraphObject> data = multiResult.getData();
	if (data == null) {
	    return null;
	}
	return data.castToListOf(clazz);
    }

    public static <T> List<T> createList(GraphObject graphObject, String property, Converter<T> converter) {
	List<T> result = new ArrayList<T>();
	if (graphObject == null) {
	    return result;
	}
	
	GraphObjectList<GraphObject> graphObjects = graphObject.getPropertyAsList(property, GraphObject.class);
	if (graphObjects == null || graphObjects.size() == 0) {
	    return result;
	}
	
	ListIterator<GraphObject> iterator = graphObjects.listIterator();
	while (iterator.hasNext()) {
	    GraphObject graphObjectItr = iterator.next();
	    T t = converter.convert(graphObjectItr);
	    result.add(t);
	}
	return result;
    }
    
    public static <T> List<T> createList(GraphObject graphObject, String property, String rootCollectionJsonProperty, Converter<T> converter) {
	List<T> result = new ArrayList<T>();
	if (graphObject == null) {
	    return result;
	}
	
	GraphObject collectionGraph = getPropertyGraphObject(graphObject, property);
	if (collectionGraph == null) {
	    return result;
	}
	
	GraphObjectList<GraphObject> graphObjects = collectionGraph.getPropertyAsList(rootCollectionJsonProperty, GraphObject.class);
	if (graphObjects == null || graphObjects.size() == 0) {
	    return result;
	}
	
	ListIterator<GraphObject> iterator = graphObjects.listIterator();
	while (iterator.hasNext()) {
	    GraphObject graphObjectItr = iterator.next();
	    T t = converter.convert(graphObjectItr);
	    result.add(t);
	}
	return result;
    }

    public interface Converter<T> {
	T convert(GraphObject graphObject);
    }

    public static String getPropertyInsideProperty(GraphObject graphObject, String parent, String child) {
	if (graphObject == null) {
	    return null; 
	}
	
	JSONObject jsonObject = (JSONObject) graphObject.getProperty(parent);
	if (jsonObject != null) {
	    return String.valueOf(jsonObject.opt(child));
	}
	return null;
    }
    
    public static String getPropertyString(GraphObject graphObject, String property) {
	if (graphObject == null) {
	    return null; 
	}
	return String.valueOf(graphObject.getProperty(property));
    }
    
    public static Long getPropertyLong(GraphObject graphObject, String property) {
	if (graphObject == null) {
	    return null; 
	}
	Object value = graphObject.getProperty(property);
	if (value == null || value.equals(EMPTY)) {
	    return null;
	}
	return Long.valueOf(String.valueOf(value));
    }
    
    public static Boolean getPropertyBoolean(GraphObject graphObject, String property) {
	if (graphObject == null) {
	    return null; 
	}
	Object value = graphObject.getProperty(property);
	if (value == null || value.equals(EMPTY)) {
	    return null;
	}
	return Boolean.valueOf(String.valueOf(value));
    }
    
    public static Integer getPropertyInteger(GraphObject graphObject, String property) {
	if (graphObject == null) {
	    return null; 
	}
	Object value = graphObject.getProperty(property);
	if (value == null || value.equals(EMPTY)) {
	    return null;
	}
	return Integer.valueOf(String.valueOf(value));
    }
    
    public static Double getPropertyDouble(GraphObject graphObject, String property) {
	if (graphObject == null) {
	    return null; 
	}
	Object value = graphObject.getProperty(property);
	if (value == null || value.equals(EMPTY)) {
	    return null;
	}
	return Double.valueOf(String.valueOf(value));
    }
    
    public static GraphObject getPropertyGraphObject(GraphObject graphObject, String property) {
	if (graphObject == null) {
	    return null; 
	}
	return graphObject.getPropertyAs(property, GraphObject.class);
    }

    public static User createUser(GraphObject graphObject, String parent) {
	if (graphObject == null) {
	    return null; 
	}
	GraphObject userGraphObject = getPropertyGraphObject(graphObject, parent);
	if (userGraphObject == null) {
	    return null; 
	}
	return createUser(userGraphObject);
    }
    
    public static User createUser(GraphObject graphObject) {
	final String id = String.valueOf(graphObject.getProperty("id"));
	final String name = String.valueOf(graphObject.getProperty("name"));
	
	User user = new User() {
	    @Override
	    public String getName() {
		return name;
	    }

	    @Override
	    public String getId() {
		return id;
	    }
	};
	
	return user;
    }
    
}
