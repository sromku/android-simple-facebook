package com.sromku.simple.fb.utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;

public class Utils
{
	public static final String EMPTY = "";

	public String getFacebookSDKVersion()
	{
		String sdkVersion = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try
		{
			Class<?> cls = classLoader.loadClass("com.facebook.FacebookSdkVersion");
			Field field = cls.getField("BUILD");
			sdkVersion = String.valueOf(field.get(null));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return sdkVersion;
	}

	public static String getHashKey(Context context)
	{
		// Add code to print out the key hash
		try
		{
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature: info.signatures)
			{
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				return Base64.encodeToString(md.digest(), Base64.DEFAULT);
			}
		}
		catch (NameNotFoundException e)
		{

		}
		catch (NoSuchAlgorithmException e)
		{

		}
		return null;
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String containing the provided
	 * elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings within the iteration are
	 * represented by empty strings.
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 * 
	 * @param iterator the {@code Iterator} of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.0
	 */
	public static String join(Iterator<?> iterator, char separator)
	{

		// handle null, zero and one elements before building a buffer
		if (iterator == null)
		{
			return null;
		}
		if (!iterator.hasNext())
		{
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext())
		{
			return first == null? "": first.toString();
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
		if (first != null)
		{
			buf.append(first);
		}

		while (iterator.hasNext())
		{
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null)
			{
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	public static String join(Map<?, ?> map, char separator, char valueStartChar, char valueEndChar)
	{

		// handle null, zero and one elements before building a buffer
		if (map == null)
		{
			return null;
		}
		if (map.size() == 0)
		{
			return EMPTY;
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small

		boolean isFirst = true;
		for (Entry<?, ?> entry: map.entrySet())
		{
			if (isFirst)
			{
				buf.append(entry.getKey());
				buf.append(valueStartChar);
				buf.append(entry.getValue());
				buf.append(valueEndChar);
				isFirst = false;
			}
			else
			{
				buf.append(separator);
				buf.append(entry.getKey());
				buf.append(valueStartChar);
				buf.append(entry.getValue());
				buf.append(valueEndChar);
			}
		}

		return buf.toString();
	}
}
