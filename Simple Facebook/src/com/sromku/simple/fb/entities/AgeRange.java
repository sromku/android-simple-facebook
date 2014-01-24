package com.sromku.simple.fb.entities;

public class AgeRange {

    private final String mMin;
    private final String mMax;
    
    public AgeRange(String min, String max) {
	mMin = min;
	mMax = max;
    }
    
    public String getMin() {
	return mMin;
    }
    
    public String getMax() {
	return mMax;
    }
}
