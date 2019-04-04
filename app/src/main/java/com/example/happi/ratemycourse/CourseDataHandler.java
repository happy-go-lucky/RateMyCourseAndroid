package com.example.happi.ratemycourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;


import java.util.ArrayList;
import java.util.EnumMap;

public class CourseDataHandler extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "rate_my_course";
    private static final String TABLE_NAME = "course_data";
    private static final String LOG_TAG = "CourseDataHandler";


    public enum DBCols
    {
        COURSE_ID,
        COURSE_MODE,
        COURSE_NUMBER,
        COURSE_CODE,
        YEAR_OFFERED,
        INSTRUCTOR_LASTNAME,
        INSTRUCTOR_FIRSTNAME;
    }

    private EnumMap<DBCols, EnumHelper> _dbColNames;
    private SQLiteDatabase _db;

    private static final String DATATYPE_INT = " INTEGER ";
    private static final String DATATYPE_TEXT = " TEXT ";
    private static final String SEPARATOR_COMMA = ",";

    public CourseDataHandler( Context context )
    {
        // build the DB
        super( context, DB_NAME, null, DB_VERSION );

        // build the enum map for later use
        _dbColNames = new EnumMap<DBCols, EnumHelper>( DBCols.class );
        int index = 0;


        _dbColNames.put( DBCols.COURSE_ID, new EnumHelper( "course_ID", index++ ) );
        _dbColNames.put( DBCols.COURSE_MODE, new EnumHelper( "course_mode", index++ ) );
        _dbColNames.put( DBCols.COURSE_NUMBER, new EnumHelper( "course_number", index++ ) );
        _dbColNames.put( DBCols.COURSE_CODE, new EnumHelper( "course_code", index++ ) );
        _dbColNames.put( DBCols.YEAR_OFFERED, new EnumHelper( "year_offered", index++ ) );
//        _dbColNames.put( DBCols.SEMESTER_OFFERED, new EnumHelper( "semester_offered", index++ ) );
        _dbColNames.put( DBCols.INSTRUCTOR_LASTNAME, new EnumHelper( "instructor lastname", index++ ) );
        //_dbColNames.put( DBCols.SEMESTER_OFFERED, new EnumHelper( "semester_offered", index++ ) );
        _dbColNames.put( DBCols.INSTRUCTOR_FIRSTNAME, new EnumHelper( "instructor firstname", index++ ) );


        _db = getWritableDatabase();
        onCreate( _db );
    }

    // if the DB exists already, this will not be called automatically
    @Override
    public void onCreate( SQLiteDatabase db )
    {
        String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _dbColNames.get( DBCols.COURSE_ID ).name() + DATATYPE_INT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COURSE_MODE ).name() + DATATYPE_INT + SEPARATOR_COMMA
                //+ _dbColNames.get( DBCols.COURSE_NUMBER ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COURSE_CODE ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.YEAR_OFFERED ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.INSTRUCTOR_LASTNAME ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.INSTRUCTOR_FIRSTNAME ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
                + "PRIMARY KEY (" + _dbColNames.get( DBCols.COURSE_CODE ).name() + SEPARATOR_COMMA
                + _dbColNames.get( DBCols.COURSE_NUMBER ).name() + ")" + ")";

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

    private void addCourse( CourseDataModel course )
    {
        //_db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( _dbColNames.get( DBCols.COURSE_ID ).name(), course.get_Courseid() );
        values.put( _dbColNames.get( DBCols.COURSE_MODE ).name(), course.get_CourseMode().toString() );
        values.put( _dbColNames.get( DBCols.COURSE_NUMBER).name(), course.get_CourseNumber().toString() );
        values.put( _dbColNames.get( DBCols.COURSE_CODE ).name(), course.get_CourseCode().toString() );
        values.put( _dbColNames.get( DBCols.YEAR_OFFERED ).name(), course.get_YearOffered() );
        //values.put( _dbColNames.get( DBCols.SEMESTER_OFFERED ).name(), course.getSemesterOffered().toString() );
        values.put( _dbColNames.get( DBCols.INSTRUCTOR_LASTNAME ).name(), course.get_InstructorLastName() );
        values.put( _dbColNames.get( DBCols.INSTRUCTOR_FIRSTNAME ).name(), course.get_InstructorFirstName() );

        _db.insert( TABLE_NAME, null, values );
    }

    public CourseDataModel getCourse( CourseDataModel.CourseCode courseCode , int courseNumber )
    {
        //_db = getReadableDatabase();

        String[] columns = new String[_dbColNames.size()];
        EnumHelper value = _dbColNames.get( DBCols.COURSE_NUMBER );

        columns[value.index()] = value.name();
        value = _dbColNames.get( DBCols.COURSE_CODE );
        columns[value.index()] = value.name();

        value = _dbColNames.get( DBCols.YEAR_OFFERED );
        columns[value.index()] = value.name();

        value = _dbColNames.get( DBCols.COURSE_MODE );
        columns[value.index()] = value.name();

//        value = _dbColNames.get( DBCols.SEMESTER_OFFERED );
//        columns[value.index()] = value.name();

        value = _dbColNames.get( DBCols.INSTRUCTOR_FIRSTNAME );
        columns[value.index()] = value.name();

        String whereClause = _dbColNames.get( DBCols.COURSE_CODE ).name()
                + " = ? AND "
                + _dbColNames.get( DBCols.COURSE_NUMBER ).name() + " = ?";

        String[] whereArgs =
                {
                        courseCode.toString(),
                        String.valueOf( courseNumber )
                };

        Cursor cursor = _db.query( TABLE_NAME, columns, whereClause,
                whereArgs, null, null, null, null );

        if ( isValidCursor( cursor ) )
        {
            cursor.moveToFirst();
        }

        return extractAllDataFromCursor( cursor );
    }

    public boolean isDuplicateData( CourseDataModel courseData )
    {
        //_db = getReadableDatabase();

        String whereClause = _dbColNames.get( DBCols.COURSE_CODE ).name()
                + " = ? AND "
                + _dbColNames.get( DBCols.COURSE_NUMBER ).name() + " = ?";

        String[] whereArgs =
                {
                        courseData.getCourseCode().toString(),
                        String.valueOf( courseData.getCourseNumber() )
                };

        Cursor cursor = _db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + whereClause, whereArgs );
        boolean state = isValidCursor( cursor );

        return state;
    }

    public void updateCourse( CourseDataModel courseData )
    {
        //_db = getWritableDatabase();

        if ( isDuplicateData( courseData ) )
        {
            ContentValues values = new ContentValues();
            values.put( _dbColNames.get( DBCols.YEAR_OFFERED ).name(), courseData.get_YearOffered() );
            values.put( _dbColNames.get( DBCols.COURSE_MODE ).name(), courseData.get_CourseMode().toString() );
            //values.put( _dbColNames.get( DBCols.SEMESTER_OFFERED ).name(), courseData.getSemesterOffered().toString() );
            values.put( _dbColNames.get( DBCols.INSTRUCTOR_LASTNAME ).name(), courseData.get_InstructorLastName() );
            values.put( _dbColNames.get( DBCols.INSTRUCTOR_FIRSTNAME ).name(), courseData.get_InstructorFirstName() );

            String whereClause = _dbColNames.get( DBCols.COURSE_CODE ).name()
                    + " = ? AND "
                    + _dbColNames.get( DBCols.COURSE_NUMBER ).name() + " = ?";

            String[] whereArgs =
                    {
                            courseData.get_CourseCode().toString(),
                            String.valueOf( courseData.get_CourseNumber() )
                    };

            _db.update( TABLE_NAME, values, whereClause, whereArgs );
        }
        else
        {
            addCourse( courseData );
        }
    }

    public void dropTable( String tableName, boolean confirmation )
    {
        if ( confirmation )
        {
            //SQLiteDatabase db = getWritableDatabase();
            _db.execSQL( "DROP TABLE IF EXISTS " + tableName );
            _db.close();
        }
    }

    public ArrayList<CourseDataModel> getAllEntries()
    {
        ArrayList<CourseDataModel> resultList = null;

        //_db = getWritableDatabase();

        String[] columns = new String[_dbColNames.size()];
        EnumHelper value = _dbColNames.get( DBCols.COURSE_NUMBER );
        columns[value.index()] = value.name();
        value = _dbColNames.get( DBCols.COURSE_CODE );
        columns[value.index()] = value.name();

        Cursor cursor = _db.query( TABLE_NAME, columns, null, null, null, null, null, null );

        if ( isValidCursor( cursor ) )
        {
            resultList = new ArrayList<CourseDataModel>();
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

    public ArrayList<String> getAllCourseIds()
    {
        //_db = getWritableDatabase();

        ArrayList<String> courseIdList = null;
        Cursor cursor = _db.rawQuery( "SELECT " + _dbColNames.get( DBCols.COURSE_CODE ).name() + ", "
                + _dbColNames.get( DBCols.COURSE_NUMBER ).name() + " FROM " + TABLE_NAME , null );

        if ( isValidCursor( cursor ) )
        {
            courseIdList = new ArrayList<String>();
            String[] columnList = { _dbColNames.get( DBCols.COURSE_CODE ).name(), _dbColNames.get( DBCols.COURSE_NUMBER ).name() };
            int ii = 0;
            while ( ii < cursor.getCount() )
            {
                cursor.moveToPosition( ii );
                courseIdList.add( extractDataFromCursor( cursor, columnList ) );
                ii++;
            }
        }

        return courseIdList;
    }

    public CourseDataModel extractAllDataFromCursor( Cursor rowCursor )
    {
        if ( !isValidCursor( rowCursor ) )
        {
            return null;
        }

        int db_courseNumber = rowCursor.getInt( _dbColNames.get( DBCols.COURSE_NUMBER ).index() );
        CourseDataModel.CourseCode db_courseCode = CourseDataModel.CourseCode.valueOf( rowCursor.getString( _dbColNames.get( DBCols.COURSE_CODE ).index() ) );
        int db_yearOffered = rowCursor.getInt( _dbColNames.get( DBCols.YEAR_OFFERED ).index() );
        CourseDataModel.CourseSemester db_semesterOffered = CourseDataModel.CourseSemester.valueOf( rowCursor.getString( _dbColNames.get( DBCols.SEMESTER_OFFERED ).index() ) );
        String db_instructorName = rowCursor.getString( _dbColNames.get( DBCols.INSTRUCTOR ).index() );
        CourseDataModel.CourseMode db_courseMode = CourseDataModel.CourseMode.valueOf( rowCursor.getString( _dbColNames.get( DBCols.COURSE_MODE ).index() ) );

        CourseDataModel courseData = new CourseDataModel
                (
                        db_courseNumber,
                        db_yearOffered,
                        db_semesterOffered,
                        db_courseCode,
                        db_instructorName,
                        db_courseMode
                );

        return courseData;
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
				/*
				if ( rowCursor.getType( colIndex ) == Cursor.FIELD_TYPE_INTEGER )
				{
					retVal = rowCursor.getString( colIndex );
				}
				else if ( rowCursor.getType( colIndex ) == Cursor.FIELD_TYPE_STRING )
				{
					retVal = rowCursor.getString( colIndex );
				}
				*/
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

	public static String getTableName() {
	    return TABLE_NAME;
    }
	/*
	public boolean checkDataBaseExists()
	{
		try
		{
			_db.
		}
	}
	*/
}
