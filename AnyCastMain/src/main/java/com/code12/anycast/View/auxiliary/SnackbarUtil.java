package com.code12.anycast.View.auxiliary;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil
{
    public static void showMessage(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
