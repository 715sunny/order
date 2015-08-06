package com.example.elva_yiwei.menu_order;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by boyu on 15/8/4.
 */
public class MenusContract {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_SHORTCUTSKEY = "shortcutsKey";
    public static final String KEY_TYPE = "type";


    public static int getId(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
    }
    public static void putId(ContentValues values, int key_id) {
        values.put(KEY_ID, key_id);
    }

    public static String getType(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
    }
    public static void putType(ContentValues values, int type) {
        values.put(KEY_TYPE, type);
    }

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
    }
    public static void putName(ContentValues values, String name) {
        values.put(KEY_NAME, name);
    }

    public static String getImage(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));
    }
    public static void putImage(ContentValues values, String image) {
        values.put(KEY_IMAGE, image);
    }

    public static String getShortcutKey(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_SHORTCUTSKEY));
    }
    public static void putShortcutKey(ContentValues values, String shortcutKey) {
        values.put(KEY_SHORTCUTSKEY, shortcutKey);
    }
}
