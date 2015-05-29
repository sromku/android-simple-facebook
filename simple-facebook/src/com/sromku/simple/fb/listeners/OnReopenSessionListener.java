package com.sromku.simple.fb.listeners;

import com.sromku.simple.fb.Permission;

public interface OnReopenSessionListener {
    void onSuccess();

    void onNotAcceptingPermissions(Permission.Type type);
}
