package com.code12.anycast.View.auxiliary;

import android.view.View;

public class ThemeChangeEvent {
    public static final int INIT_CHANGE = 1;
    public static final int GLOBLE_CHANGE = 2;
    public static final int SPECIFIC_CHANGE =3;

    public int eventType;
    public View view;

    public ThemeChangeEvent(int eventType){
        this.eventType =eventType;
    }

    public ThemeChangeEvent(int eventType , View view){
        this.eventType = eventType;
        this.view = view;
    }

}
