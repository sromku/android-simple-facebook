package com.sromku.simple.fb.listeners;

public interface OnActionListener<T> extends OnThinkingListetener {

    void onComplete(T response);
}
