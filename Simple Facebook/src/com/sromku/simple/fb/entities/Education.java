package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/fql/user/
 */
public class Education {

	private static final String SCHOOL = "school";
	private static final String DEGREE = "degree";
	private static final String YEAR = "year";
	private static final String CONCENTRATION = "concentration";
	private static final String TYPE = "type";
	private static final String NAME = "name";
	private static final String WITH = "with";

	private String mSchool;
	private String mDegree;
	private String mYear;
	private List<String> mConcentration;
	private List<User> mWith;
	private String mType;

	private Education(GraphObject graphObject) {

		// school
		mSchool = Utils.getPropertyInsideProperty(graphObject, SCHOOL, NAME);

		// degree
		mDegree = Utils.getPropertyInsideProperty(graphObject, DEGREE, NAME);

		// year
		mYear = Utils.getPropertyInsideProperty(graphObject, YEAR, NAME);

		/*
		 * concentration
		 */
		mConcentration = Utils.createList(graphObject, CONCENTRATION, new Converter<String>() {
			@Override
			public String convert(GraphObject graphObject) {
				return Utils.getPropertyString(graphObject, NAME);
			}
		});

		// with
		mWith = Utils.createList(graphObject, WITH, new Converter<User>() {
			@Override
			public User convert(GraphObject graphObject) {
				return Utils.createUser(graphObject);
			}
		});

		// type
		mType = Utils.getPropertyString(graphObject, TYPE);
	}

	public static Education create(GraphObject graphObject) {
		return new Education(graphObject);
	}

	public String getSchool() {
		return mSchool;
	}

	public String getDegree() {
		return mDegree;
	}

	public String getYear() {
		return mYear;
	}

	public List<String> getConcentrations() {
		return mConcentration;
	}

	public List<User> getWith() {
		return mWith;
	}

	public String getType() {
		return mType;
	}
}
