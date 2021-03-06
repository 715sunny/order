package com.example.elva_yiwei.menu_order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by boyu on 15/8/4.
 */
public class OrderMenuDB {
    private static final String DATABASE_NAME = "OrderMenu.db";
    private static final String DATABASE_ORDERS_TABLE = "Orders";
    private static final String DATABASE_MENUS_TABLE = "Menus";
    private static final String DATABASE_CATEGORY_TABLE = "CategoryItem";

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String MENUSLIST = "menusList";
    public static final String TYPE = "type";
    public static final String PHONENUM = "phoneNum";
    public static final String ADDRESS = "address";

    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String SHORTCUTSKEY = "shortcutsKey";
    public static final String PRICE = "price";




    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_CREATE_ORDERS = "CREATE TABLE "
            + DATABASE_ORDERS_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATE                  + " TEXT NOT NULL, "
            + MENUSLIST             + " TEXT NOT NULL, "// MENUSIDLIST
            + TYPE                  + " INTEGER NOT NULL, "//1 not finish  0 finish
            + PHONENUM              + " TEXT NOT NULL, "
            + ADDRESS                + " TEXT NOT NULL"
            + ");" ;

    public static final String DATABASE_CREATE_MENUS = "CREATE TABLE "
            + DATABASE_MENUS_TABLE  + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME                  + " TEXT NOT NULL, "
            + IMAGE                 + " TEXT NOT NULL, "
            + SHORTCUTSKEY          + " TEXT NOT NULL, "
            + TYPE                  + " INTEGER NOT NULL, "//0 Chinses 1 US 2 3 4
            + PRICE                 + " TEXT NOT NULL "
            + ");" ;

    public static final String DATABASE_CREATE_GATEGORY = "CREATE TABLE "
            + DATABASE_CATEGORY_TABLE  + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME                  + " TEXT NOT NULL, "
            + TYPE                  + " INTEGER NOT NULL"
            + ");" ;

    private static final String SELECT_MENUS = " SELECT * FROM " + DATABASE_MENUS_TABLE + " ORDER BY "
            + NAME;



    private static final String SELECT_ORDER = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " ORDER BY "
            + DATE;

    private static final String SELECT_Category = " SELECT * FROM " + DATABASE_CATEGORY_TABLE + " ORDER BY "
            + TYPE;

    private static final String SELECT_UNFINISHORDER = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " WHERE "
            + TYPE +"=0";

    private static final String SELECT_FINISHORDER = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " WHERE "
            + TYPE +"=1";

    private static final String SELECT_CELLPHONE = " SELECT DISTINCT "+PHONENUM+ " FROM " + DATABASE_ORDERS_TABLE + " WHERE "
            + PHONENUM +"!= -1";



    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    private final Context orderMenusContext;

    public OrderMenuDB(Context ctx)
    {
        orderMenusContext = ctx;
        dbHelper = new DatabaseHelper(orderMenusContext);
    }

    public void updateDbbytime(String s) {
        ContentValues values2= new ContentValues();
        values2.put("type",1);
        db.update(DATABASE_ORDERS_TABLE, values2, "date=?", new String[]{s + " "});
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
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CATEGORY_TABLE );
            db.execSQL(DATABASE_CREATE_ORDERS);
            db.execSQL(DATABASE_CREATE_MENUS);
            db.execSQL(DATABASE_CREATE_GATEGORY);
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
    public Cursor fetchMenusById(String type) {
         String SELECT_MENUS_BY_ID = " SELECT * FROM " + DATABASE_MENUS_TABLE + " WHERE "+ " TYPE = "+ type +" GROUP BY "
                + NAME;
        return db.rawQuery(SELECT_MENUS_BY_ID, null);
    }

    public Cursor fetchCategoryByname(String name) {
        String SELECT_MENUS_BY_ID = " SELECT * FROM " + DATABASE_CATEGORY_TABLE + " WHERE "+ " NAME = '"+ name +"'";
        return db.rawQuery(SELECT_MENUS_BY_ID, null);
    }

    public Cursor fetchOrderByDate(String startdate, String enddate) {
        //String SELECT_ORDER_BY_DATE = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " WHERE "+ " DATE between" + startdate + "and enddate";
        String SELECT_ORDER_BY_DATE = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " WHERE date between '"+startdate+"' and '"+enddate+"' " ;

        //Cursor a= db.rawQuery(SELECT_ORDER_BY_DATE, new String[]{startdate,enddate});
        Cursor a= db.rawQuery(SELECT_ORDER_BY_DATE, null);

        //Log.v(v,a.getCount()+"[][][][][]=====================================================");

        return a;
        //return db.rawQuery(SELECT_ORDER_BY_DATE, new String[]{startdate,enddate});
    }

    public Cursor fetchDeliveryOrderByDate(String startdate, String enddate) {
        //String SELECT_ORDER_BY_DATE = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " WHERE "+ " DATE between" + startdate + "and enddate";
        String SELECT_Delivery_ORDER_BY_DATE = " SELECT * FROM " + DATABASE_ORDERS_TABLE + " WHERE "+ADDRESS+" != 'home' and date between '"+startdate+"' and '"+enddate+"' ";

        //Cursor a= db.rawQuery(SELECT_ORDER_BY_DATE, new String[]{startdate,enddate});
        Cursor a= db.rawQuery(SELECT_Delivery_ORDER_BY_DATE, null);

        //Log.v(v,a.getCount()+"[][][][][]=====================================================");

        return a;
        //return db.rawQuery(SELECT_ORDER_BY_DATE, new String[]{startdate,enddate});
    }



    public Cursor fetchAllOrder() {
        return db.rawQuery(SELECT_ORDER, null);
    }

    public Cursor fetchAllCategory() {
        return db.rawQuery(SELECT_Category, null);
    }

    public Cursor fetchCellPhone() {
        return db.rawQuery(SELECT_CELLPHONE, null);
    }

    public Cursor fetchUnfinishOrder() {
        return db.rawQuery(SELECT_UNFINISHORDER, null);
    }

    public Cursor fetchFinishOrder() {
        return db.rawQuery(SELECT_FINISHORDER, null);
    }



//    public String fetchDate() {
//         db.rawQuery(SELECT_FINISHORDER, null);
//        String data=db.rawQuery(SELECT_FINISHORDER, null).
//    }

    //menu
    public void persistM(String name, String image, String shortcuts, int type, String price) throws SQLException {
        ContentValues values = new ContentValues();
        MenusContract.putName(values, name);
        MenusContract.putImage(values, image);
        MenusContract.putShortcutKey(values, shortcuts);
        MenusContract.putType(values, type);
        MenusContract.putPrice(values, price);
        db.insert(DATABASE_MENUS_TABLE, null, values);
    }

    //order
    public void persistO(String date, String menusList, String type, String phoneNum, String address) throws SQLException {
        ContentValues values = new ContentValues();
        OrderContract.putDate(values, date);
        OrderContract.putType(values, type);
        OrderContract.putMenusList(values, menusList);
        OrderContract.putAddress(values, address);
        OrderContract.putPhoneNum(values, phoneNum);
        db.insert(DATABASE_ORDERS_TABLE, null, values);
    }

    //menu catigary
    public void persistC(String name, String type) throws SQLException {
        ContentValues values = new ContentValues();
        CategoryContract.putName(values, name);
        CategoryContract.putType(values, type);
        db.insert(DATABASE_CATEGORY_TABLE, null, values);
    }

    public void deleteCategory(String  id){

        db.delete( DATABASE_CATEGORY_TABLE,"id=?",new String[]{id});
    }

    public void updateCategory(String name){

    }

}
