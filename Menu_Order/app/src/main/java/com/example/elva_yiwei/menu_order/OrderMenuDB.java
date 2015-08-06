package com.example.elva_yiwei.menu_order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by boyu on 15/8/4.
 */
public class OrderMenuDB {
    private static final String DATABASE_NAME = "OrderMenu.db";
    private static final String DATABASE_ORDERS_TABLE = "Orders";
    private static final String DATABASE_MENUS_TABLE = "Menus";

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String MENUSLIST = "menusList";
    public static final String TYPE = "type";
    public static final String PHONENUM = "phoneNum";
    public static final String ADRESS = "adress";

    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String SHORTCUTSKEY = "shortcutsKey";


    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_CREATE_ORDERS = "CREATE TABLE "
            + DATABASE_ORDERS_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATE                  + " TEXT NOT NULL, "
            + MENUSLIST             + " TEXT NOT NULL, "// MENUSIDLIST
            + TYPE                  + " INTEGER NOT NULL, "//1 not finish  0 finish
            + PHONENUM              + " TEXT NOT NULL, "
            + ADRESS                + " TEXT NOT NULL"
            + ");" ;

    public static final String DATABASE_CREATE_MENUS = "CREATE TABLE "
            + DATABASE_MENUS_TABLE  + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME                  + " TEXT NOT NULL, "
            + IMAGE                 + " TEXT NOT NULL, "
            + SHORTCUTSKEY          + " TEXT NOT NULL, "
            + TYPE                  + " INTEGER NOT NULL "//1 Chinses 2 US 3 4 5
            + ");" ;

    private static final String SELECT_MENUS = " SELECT * FROM " + DATABASE_MENUS_TABLE + " GROUP BY "
            + NAME;



    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    private final Context orderMenusContext;

    public OrderMenuDB(Context ctx)
    {
        orderMenusContext = ctx;
        dbHelper = new DatabaseHelper(orderMenusContext);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ORDERS_TABLE );
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_MENUS_TABLE );
            db.execSQL(DATABASE_CREATE_ORDERS);
            db.execSQL(DATABASE_CREATE_MENUS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ORDERS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_MENUS_TABLE);
            onCreate(db);
        }
    }

    public OrderMenuDB open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    public Cursor fetchAllMenus() {
        return db.rawQuery(SELECT_MENUS, null);
    }

    public void persist(String name, String image, String shortcuts, int type) throws SQLException {
        ContentValues values = new ContentValues();
//        MenusContract.putName(values, name);
//        MenusContract.putImage(values, image);
//        MenusContract.putShortcutKey(values, shortcuts);
//        MenusContract.putType(values,type);
        db.insert(DATABASE_MENUS_TABLE, null, values);
    }

}
