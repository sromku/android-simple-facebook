package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/reference/fql/user/
 */
public class Education {

    private static final String SCHOOL = "school";
    private static final String DEGREE = "degree";
    private static final String YEAR = "year";
    private static final String CONCENTRATION = "concentration";
    private static final String TYPE = "type";
    private static final String WITH = "with";

    @SerializedName(SCHOOL)
    private IdName mSchool;

    @SerializedName(DEGREE)
    private IdName mDegree;

    @SerializedName(YEAR)
    private IdName mYear;

    @SerializedName(CONCENTRATION)
    private List<IdName> mConcentration;

    @SerializedName(WITH)
    private List<User> mWith;

    @SerializedName(TYPE)
    private String mType;

    public String getSchool() {
        return mSchool.getName();
    }

    public String getDegree() {
        return mDegree == null ? null : mDegree.getName();
    }

    public String getYear() {
        return mYear.getName();
    }

    public List<String> getConcentrations() {
        return Utils.extract(mConcentration);
    }

    public List<User> getWith() {
        return mWith;
    }

    public String getType() {
        return mType;
    }

}
