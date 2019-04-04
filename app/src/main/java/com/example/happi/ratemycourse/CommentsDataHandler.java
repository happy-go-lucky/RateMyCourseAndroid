package com.example.happi.ratemycourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.EnumMap;

public class CommentsDataHandler extends SQLiteDatabase {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "rate_my_course";
    private static final String TABLE_NAME = "comments_data";
    private static final String LOG_TAG = "CommentsDataHandler";

    public enum DBCols {


        COMMENT_ID,
        COMMENT

    }

    private EnumMap<DBCols, EnumHelper> _dbColNames;
    private SQLiteDatabase _db;

    private static final String DATATYPE_INT = " INTEGER ";
    private static final String DATATYPE_TEXT = " TEXT ";
    private static final String SEPARATOR_COMMA = ",";

    public CommentsDataHandler (Context context) {

        super(context, DB_NAME, null, DB_VERSION);


    }
}
