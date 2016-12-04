package kbl.chelbi.amawal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DicHandler {

    protected SQLiteDatabase mDb = null;
    protected DicoDataBase mHandler = null;

    public DicHandler(Context context)  {
        mHandler = new DicoDataBase(context);
    }

    public SQLiteDatabase open(){

        mDb = mHandler.getWritableDatabase();
        return  mDb;
    }

    public void close(){
        mDb.close();
    }

    public Cursor selectionner(){
        open();
        return mDb.rawQuery("select distinct * from dico d where d.id > ? ORDER BY nomkbl ASC", new String[]{"0"});

    }




}
