package com.example.happi.ratemycourse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.happi.ratemycourse.util.EnumHelper;

import java.util.EnumMap;

public class CommentsDataHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "rate_my_course";
    private static final String TABLE_NAME = "comments_data";
    private static final String LOG_TAG = "CommentsDataHandler";

    public enum DBCols {

        COMMENT_ID,
        USER_ID,
        COURSE_CODE,
        COURSE_NUMBER,
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

        _dbColNames.put( DBCols.COMMENT_ID, new EnumHelper( "comment_id", index++ ));
        _dbColNames.put( DBCols.USER_ID, new EnumHelper("user_id", index++));
        _dbColNames.put( DBCols.COURSE_CODE, new EnumHelper( "course_code", index++ ));
        _dbColNames.put( DBCols.COURSE_NUMBER, new EnumHelper( "course_number", index++ ));
        _dbColNames.put( DBCols.COMMENT, new EnumHelper( "comment", index++ ));

        _db = getWritableDatabase();
        onCreate( _db );
    }

    // if the DB exists already, this will not be called automatically
    @Override
    public void onCreate( SQLiteDatabase db )
    {
        String CREATE_COMMENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _dbColNames.get( DBCols.COMMENT_ID ).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.USER_ID).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COURSE_CODE).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COURSE_NUMBER).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COMMENT ).name() + DATATYPE_TEXT + SEPARATOR_COMMA

                + "PRIMARY KEY (" + _dbColNames.get( DBCols.COMMENT_ID ).name() + SEPARATOR_COMMA + _dbColNames.get( DBCols.COMMENT_ID ).name() + ")" + SEPARATOR_COMMA

                + "FOREIGN KEY (" + _dbColNames.get( DBCols.COURSE_CODE ).name() + SEPARATOR_COMMA + _dbColNames.get( DBCols.COURSE_NUMBER).name() + ")"
                + "REFERENCES " + CourseDataHandler.getTableName() + "(" + _dbColNames.get( DBCols.COURSE_CODE ).name() + SEPARATOR_COMMA + _dbColNames.get( DBCols.COURSE_NUMBER).name() + ")" + SEPARATOR_COMMA

                + "FOREIGN KEY (" + _dbColNames.get( DBCols.USER_ID ).name() + ")"
                + "REFERENCES " + UserDataHandler.getTableName() + "(" + _dbColNames.get( DBCols.USER_ID ).name() + ")" + SEPARATOR_COMMA


                + ")";

        db.execSQL( CREATE_COMMENTS_TABLE );
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
