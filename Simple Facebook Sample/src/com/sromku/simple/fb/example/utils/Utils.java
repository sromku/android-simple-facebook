package com.sromku.simple.fb.example.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;

public class Utils
{
	/**
	 * Take screenshot of the activity including the action bar
	 * 
	 * @param activity
	 * @return The screenshot of the activity including the action bar
	 */
	public static Bitmap takeScreenshot(Activity activity)
	{
		ViewGroup decor = (ViewGroup)activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup)decor.getChildAt(0);
		decorChild.setDrawingCacheEnabled(true);
		decorChild.buildDrawingCache();
		Bitmap drawingCache = decorChild.getDrawingCache(true);
		Bitmap bitmap = Bitmap.createBitmap(drawingCache);
		decorChild.setDrawingCacheEnabled(false);
		return bitmap;
	}

	/**
	 * Print hash key
	 */
	public static void printHashKey(Context context)
	{
		try
		{
			String TAG = "com.sromku.simple.fb.example";
			PackageInfo info = context.getPackageManager().getPackageInfo(TAG,
				PackageManager.GET_SIGNATURES);
			for (Signature signature: info.signatures)
			{
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.d(TAG, "keyHash: " + keyHash);
			}
		}
		catch (NameNotFoundException e)
		{

		}
		catch (NoSuchAlgorithmException e)
		{

		}
	}

	/**
	 * Update language
	 * 
	 * @param code The language code. Like: en, cz, iw, ...
	 */
	public static void updateLanguage(Context context, String code)
	{
		Locale locale = new Locale(code);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}

	/**
	 * Build alert dialog with properties and data
	 * 
	 * @param pairs
	 * @return {@link AlertDialog}
	 */
	public static AlertDialog buildProfileResultDialog(Activity activity, Pair<String, String>... pairs)
	{
		StringBuilder stringBuilder = new StringBuilder();

		for (Pair<String, String> pair: pairs)
		{
			stringBuilder.append(String.format("<h3>%s</h3>", pair.first));
			stringBuilder.append(String.valueOf(pair.second));
			stringBuilder.append("<br><br>");
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(Html.fromHtml(stringBuilder.toString()))
			.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
				}
			});
		AlertDialog alertDialog = builder.create();
		return alertDialog;
	}
}
