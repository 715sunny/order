package com.example.elva_yiwei.menu_order;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by boyu on 15/8/4.
 */
public class OrderContract {
    public static final String KEY_ID = "id";
    public static final String KEY_DATE = "date";
    public static final String KEY_MENUSLIST = "menusList";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PHONENUM = "phoneNum";

    public static int getId(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
    }
    public static void putId(ContentValues values, int key_id) {
        values.put(KEY_ID, key_id);
    }
    public static String getDate(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE));
    }
    public static void putDate(ContentValues values, String key_date) {
        values.put(KEY_DATE, key_date);
    }
    public static String getMenusList(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_MENUSLIST));
    }
    public static void putMenusList(ContentValues values, String key_menusList) {
        values.put(KEY_MENUSLIST, key_menusList);
    }
    public static String getType(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
    }
    public static void putType(ContentValues values, String type) {
        values.put(KEY_TYPE, type);
    }
    public static String getPhoneNum(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONENUM));
    }
    public static void putPhoneNum(ContentValues values, String phoneNum) {
        values.put(KEY_PHONENUM, phoneNum);
    }

}
