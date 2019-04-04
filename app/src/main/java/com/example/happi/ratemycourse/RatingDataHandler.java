package com.example.happi.ratemycourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.EnumMap;

public class RatingDataHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "rate_my_course";
    private static final String TABLE_NAME = "rating_data";
    private static final String LOG_TAG = "RatingDataHandler";

    public enum DBCols {
        RATING_ID,
        HOMEWORK_AMOUNT,
        READING_AMOUNT,
        USEFULNESS,
        STRESS_LEVEL
    }

    private EnumMap<DBCols, EnumHelper> _dbColNames;
    private SQLiteDatabase _db;

    private static final String DATATYPE_INT = " INTEGER ";
    private static final String DATATYPE_TEXT = " TEXT ";
    private static final String SEPARATOR_COMMA = ",";


    public RatingDataHandler ( Context context ) {

        super(context, DB_NAME, null, DB_VERSION);

        _dbColNames = new EnumMap<DBCols, EnumHelper>( DBCols.class );

        int index = 0;

        _dbColNames.put( DBCols.HOMEWORK_AMOUNT, new EnumHelper( "homework_amount", index++ ));
        _dbColNames.put( DBCols.READING_AMOUNT, new EnumHelper( "reading_amount", index++ ));
        _dbColNames.put( DBCols.USEFULNESS, new EnumHelper( "usefulness", index++ ));
        _dbColNames.put( DBCols.STRESS_LEVEL, new EnumHelper( "homework_amount", index++ ));

        _db = getWritableDatabase();
        onCreate( _db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _dbColNames.get( DBCols.HOMEWORK_AMOUNT ).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.READING_AMOUNT ).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.USEFULNESS ).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.STRESS_LEVEL ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + "PRIMARY KEY (" + _dbColNames.get( DBCols.RATING_ID).name() + ")" + ")";

        db.execSQL( CREATE_COURSE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );

        // Create tables again
        onCreate( db );
    }

    /**
     * Add rating of each criteria to database.
     *
     * @param rating instance of user course ratings per criteria
     */
    private void addRating( RatingDataModel rating) {
        //_db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( _dbColNames.get( DBCols.HOMEWORK_AMOUNT ).name(),
                rating.getHomework() );
        values.put( _dbColNames.get( DBCols.READING_AMOUNT ).name(),
                rating.getReading() );
        values.put( _dbColNames.get( DBCols.USEFULNESS ).name(),
                rating.getUsefulness() );
        values.put( _dbColNames.get( DBCols.STRESS_LEVEL ).name(),
                rating.getStress() );

        _db.insert( TABLE_NAME, null, values );
    }

    private void getRating ( RatingDataModel.RatingModel) {
    }

    public boolean isDuplicateData ( RatingDataModel ratingData) {
    }

    public void updateRating ( RatingDataModel ratingData) {

    }

    public void dropTable (String tableName, boolean confirmation) {
        if (confirmation) {
            //SQLiteDatabase db = getWritableDatabase();
            _db.execSQL( "DROP TABLE IF EXISTS " + tableName );
            _db.close();
        }
    }

    public ArrayList<RatingDataModel> getAllEntries() {

        ArrayList<UserDataModel> resultList = null;

        //_db = getWritableDatabase();

        Cursor cursor = _db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );

        if ( isValidCursor( cursor ) )
        {
            resultList = new ArrayList<UserDataModel>();
            int ii = 0;
            while ( ii < cursor.getCount() )
            {
                cursor.moveToPosition( ii );
                resultList.add( extractAllDataFromCursor( cursor ) );
                ii++;
            }
        }

        return resultList;
    }

    public UserDataModel extractAllDataFromCursor( Cursor rowCursor )
    {
        if ( !isValidCursor( rowCursor ) )
        {
            return null;
        }

        String db_userEmail = rowCursor.getString( _dbColNames.get( DBCols.USER_EMAIL ).index() );
        byte[] db_password =rowCursor.getBlob( _dbColNames.get( DBCols.PASSWORD ).index() );
        String db_firstName = rowCursor.getString( _dbColNames.get( DBCols.FIRST_NAME ).index() );
        String db_lastName = rowCursor.getString( _dbColNames.get( DBCols.LAST_NAME).index() );
        String db_schoolName = rowCursor.getString( _dbColNames.get( DBCols.SCHOOL_NAME ).index() );

        UserDataModel userData = new UserDataModel
                (
                        db_userEmail,
                        db_password,
                        db_firstName,
                        db_lastName,
                        db_schoolName
                );

        return userData;
    }

    public String extractDataFromCursor( Cursor rowCursor, String[] columnsToExtract )
    {
        String retVal = "";
        if ( isValidCursor( rowCursor ) && columnsToExtract.length > 0 )
        {
            int ii = 0;
            while ( ii < columnsToExtract.length )
            {
                int colIndex = rowCursor.getColumnIndex( columnsToExtract[ii] );
                retVal += rowCursor.getString( colIndex );
                ii++;
            }
        }

        return retVal;
    }

    private void debugPrintRowCursor( Cursor rowData )
    {
        if ( isValidCursor( rowData ) )
        {
            int ii = 0;
            Log.d( LOG_TAG, "testRowCursor" );
            while ( ii < rowData.getColumnCount() )
            {
                Log.d( "row " + ii + ": ", rowData.getString( ii ) );
                ii++;
            }
        }
    }

    private boolean isValidCursor( Cursor cursor )
    {
        return ( cursor != null ) && ( cursor.getCount() > 0 ) && ( cursor.getColumnCount() > 0 );
    }

}
