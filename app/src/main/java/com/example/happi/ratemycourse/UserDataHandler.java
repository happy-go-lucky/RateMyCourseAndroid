package com.example.happi.ratemycourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.EnumMap;

public class UserDataHandler extends SQLiteOpenHelper
{
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "rate_my_course";
	private static final String TABLE_NAME = "user_data";
	private static final String LOG_TAG = "UserDataHandler";

	public enum DBCols
	{
		USER_EMAIL,
		PASSWORD,
		FIRST_NAME,
		LAST_NAME,
		SCHOOL_NAME
	};

	private EnumMap<DBCols, EnumHelper> _dbColNames;
	private SQLiteDatabase _db;

	private static final String DATATYPE_INT = " INTEGER ";
	private static final String DATATYPE_TEXT = " TEXT ";
	private static final String DATATYPE_BLOB = " BLOB ";
	private static final String SEPARATOR_COMMA = ",";

	public UserDataHandler( Context context )
	{
		super( context, DB_NAME, null, DB_VERSION );

		_dbColNames = new EnumMap<DBCols, EnumHelper>( DBCols.class );
		int index = 0;
		_dbColNames.put( DBCols.USER_EMAIL, new EnumHelper( "email", index++ ) );
		_dbColNames.put( DBCols.PASSWORD, new EnumHelper( "password", index++ ) );
		_dbColNames.put( DBCols.FIRST_NAME, new EnumHelper( "first_name", index++ ) );
		_dbColNames.put( DBCols.LAST_NAME, new EnumHelper( "last_name", index++ ) );
		_dbColNames.put( DBCols.SCHOOL_NAME, new EnumHelper( "school_name", index++ ) );

		_db = getWritableDatabase();
		onCreate( _db );
	}

	@Override
	public void onCreate( SQLiteDatabase db )
	{
		Log.d( LOG_TAG, "onCreate" );
		String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
				+ _dbColNames.get( DBCols.USER_EMAIL ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
				+ _dbColNames.get( DBCols.PASSWORD ).name() + DATATYPE_BLOB + SEPARATOR_COMMA
				+ _dbColNames.get( DBCols.FIRST_NAME ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
				+ _dbColNames.get( DBCols.LAST_NAME ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
				+ _dbColNames.get( DBCols.SCHOOL_NAME ).name() + DATATYPE_TEXT + SEPARATOR_COMMA
				+ "PRIMARY KEY (" + _dbColNames.get( DBCols.USER_EMAIL ).name() + ")" + ")";

		db.execSQL( CREATE_USER_TABLE );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		// Drop older table if existed
		db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );

		// Create tables again
		onCreate( db );
	}

	private void addUser( UserDataModel userData )
	{
		//_db = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( _dbColNames.get( DBCols.USER_EMAIL ).name(), userData.getUserEmail() );
		values.put( _dbColNames.get( DBCols.PASSWORD ).name(), userData.getPassword() );
		values.put( _dbColNames.get( DBCols.FIRST_NAME ).name(), userData.getFirstName() );
		values.put( _dbColNames.get( DBCols.LAST_NAME ).name(), userData.getLastName() );
		values.put( _dbColNames.get( DBCols.SCHOOL_NAME ).name(), userData.getSchoolName() );

		_db.insert( TABLE_NAME, null, values );
	}

	public UserDataModel getUser( String userEmail )
	{
		//_db = this.getReadableDatabase();

		String[] columns = new String[_dbColNames.size()];
		EnumHelper value = _dbColNames.get( DBCols.USER_EMAIL );
		columns[value.index()] = value.name();
		value = _dbColNames.get( DBCols.PASSWORD );
		columns[value.index()] = value.name();
		value = _dbColNames.get( DBCols.FIRST_NAME );
		columns[value.index()] = value.name();
		value = _dbColNames.get( DBCols.LAST_NAME );
		columns[value.index()] = value.name();
		value = _dbColNames.get( DBCols.SCHOOL_NAME );
		columns[value.index()] = value.name();

		String whereClause = _dbColNames.get( DBCols.USER_EMAIL ).name() + " = ?";

		String[] whereArgs =
				{
						userEmail
				};

		Cursor cursor = _db.query( TABLE_NAME, columns, whereClause,
				whereArgs, null, null, null, null );

		if ( isValidCursor( cursor ) )
		{
			cursor.moveToFirst();
		}

		return extractAllDataFromCursor( cursor );
	}

	public boolean isDuplicateData( UserDataModel userData )
	{
		//_db = getReadableDatabase();

		String whereClause = _dbColNames.get( DBCols.USER_EMAIL ).name() + " = ?";

		String[] whereArgs = { userData.getUserEmail() };

		Cursor cursor = _db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + whereClause, whereArgs );

		return isValidCursor( cursor );
	}

	public void updateUser( UserDataModel userData )
	{
		//_db = getWritableDatabase();

		if ( isDuplicateData( userData ) )
		{
			ContentValues values = new ContentValues();
			values.put( _dbColNames.get( DBCols.PASSWORD ).name(), userData.getPassword() );
			values.put( _dbColNames.get( DBCols.FIRST_NAME ).name(), userData.getFirstName() );
			values.put( _dbColNames.get( DBCols.LAST_NAME ).name(), userData.getLastName() );
			values.put( _dbColNames.get( DBCols.SCHOOL_NAME ).name(), userData.getSchoolName() );

			String whereClause = _dbColNames.get( DBCols.USER_EMAIL ).name() + " = ?";

			String[] whereArgs = { userData.getUserEmail() };

			_db.update( TABLE_NAME, values, whereClause, whereArgs );
		}
		else
		{
			addUser( userData );
		}
	}

	public void dropTable( String tableName, boolean confirmation )
	{
		if ( confirmation )
		{
			//_db = getWritableDatabase();
			_db.execSQL( "DROP TABLE IF EXISTS " + tableName );
		}
	}

	public ArrayList<UserDataModel> getAllEntries()
	{
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
