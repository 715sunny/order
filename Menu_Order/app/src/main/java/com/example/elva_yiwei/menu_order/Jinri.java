package com.example.elva_yiwei.menu_order;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Jinri extends AppCompatActivity {

    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jinri);
    }

    //public Cursor query(String DATABASE_ORDERS_TABLE,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);

}
