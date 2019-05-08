package com.yeputra.moviecatalogue.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class InfoUtils {
    static void toast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    static void snackbar(Activity ctx, String message) {
        Snackbar.make(ctx.getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT).show();
    }
}
