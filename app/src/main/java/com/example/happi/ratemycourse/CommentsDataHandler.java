package com.example.happi.ratemycourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.EnumMap;

public class CommentsDataHandler extends SQLiteOpenHelper {

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

        _dbColNames = new EnumMap<DBCols, EnumHelper>(DBCols.class);
        int index = 0;

        _dbColNames.put( DBCols.COMMENT_ID, new EnumHelper( "course_mode", index++ ) );
        _dbColNames.put( DBCols.COMMENT, new EnumHelper( "course_number", index++ ) );


        _db = getWritableDatabase();
        onCreate( _db );
    }

    // if the DB exists already, this will not be called automatically
    @Override
    public void onCreate( SQLiteDatabase db )
    {
        String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _dbColNames.get( DBCols.COMMENT_ID ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COMMENT ).name() + DATATYPE_INT + SEPARATOR_COMMA

                + "PRIMARY KEY (" + _dbColNames.get( DBCols.COMMENT_ID ).name() + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COMMENT_ID ).name() + ")" + ")";

        db.execSQL( CREATE_COURSE_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        // Drop older table if existed
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );

        // Create tables again
        onCreate( db );
    }
}
