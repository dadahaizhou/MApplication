package com.lzy.app.database;

import android.content.Context;

import com.lzy.app.R;

/**
 * Created by Administrator on 2016/10/11.
 */

public class MDBHelper extends DataBaseHelper {

    private static MDBHelper mTestDBHelper;

    private MDBHelper(Context context){
        super(context);
    }

    public static MDBHelper getInstance(Context context){
        if (mTestDBHelper==null){
            synchronized (DataBaseHelper.class){
                if (mTestDBHelper==null){
                    mTestDBHelper = new MDBHelper(context);
                    if (mTestDBHelper.getDB()==null||!mTestDBHelper.getDB().isOpen()){
                        mTestDBHelper.open();
                    }
                }
            }
        }
        return mTestDBHelper;
    }

    @Override
    protected int getMDbVersion(Context context) {
        return Integer.valueOf(context.getResources().getStringArray(R.array.DATABASE_INFO)[1]);
    }

    @Override
    protected String getDbName(Context context) {
        return context.getResources().getStringArray(R.array.DATABASE_INFO)[0];
    }

    @Override
    protected String[] getDbCreateSql(Context context) {
        return context.getResources().getStringArray(R.array.CREATE_TABLE_SQL);
    }

    @Override
    protected String[] getDbUpdateSql(Context context) {
        return context.getResources().getStringArray(R.array.UPDATE_TABLE_SQL);
    }
}
