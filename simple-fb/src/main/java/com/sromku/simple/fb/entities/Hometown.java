package com.sromku.simple.fb.entities;

import java.util.Locale;

/**
 * Created by sromku on 7/3/15.
 */
@Deprecated
public class Hometown extends IdName {

    @Override
    public String toString() {
        return String.format(Locale.US, "id=%s,name=%s", mId, mName);
    }
}
