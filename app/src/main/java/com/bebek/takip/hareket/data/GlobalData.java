package com.bebek.takip.hareket.data;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yasemin on 5/2/14.
 */
public abstract class GlobalData {
    private GlobalData() { };

    private static final AtomicBoolean phoneInMotion = new AtomicBoolean(false);

    public static boolean isPhoneInMotion() {
        return phoneInMotion.get();
    }
    public static void setPhoneInMotion(boolean bool) {
        phoneInMotion.set(bool);
    }
}

