package com.example.elva_yiwei.menu_order;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by boyu on 15/8/19.
 */
public class CategoryContract {
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
    }
    public static void putName(ContentValues values, String name) {
        values.put(KEY_NAME, name);
    }

    public static String getType(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
    }
    public static void putType(ContentValues values, String type) {
        values.put(KEY_TYPE, type);
    }
}
