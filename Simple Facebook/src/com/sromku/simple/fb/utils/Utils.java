package com.sromku.simple.fb.utils;

import java.lang.reflect.Field;

public class Utils
{
	public String getFacebookSDKVersion()
	{
		String sdkVersion = null;
		ClassLoader classLoader = getClass().getClassLoader();
		Class<?> cls;
		try
		{
			cls = classLoader.loadClass("com.facebook.FacebookSdkVersion");
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
}
