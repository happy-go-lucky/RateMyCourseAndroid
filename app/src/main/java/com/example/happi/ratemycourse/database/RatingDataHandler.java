package com.example.happi.ratemycourse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.happi.ratemycourse.util.EnumHelper;

import java.util.ArrayList;
import java.util.EnumMap;

//TODO: Complete getAverage method?

/**
 * Rating Data Handler.
 *
 * @author Brodie Heywood
 * @version 1.0
 */
public class RatingDataHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "rate_my_course";
    private static final String TABLE_NAME = "rating_data";
    private static final String LOG_TAG = "RatingDataHandler";

    private EnumMap<DBCols, EnumHelper> _dbColNames;
    private SQLiteDatabase _db;
    public enum DBCols {
        RATING_ID,
        COURSE_CODE,
        COURSE_NUMBER,
        USER_EMAIL,
        HOMEWORK_AMOUNT,
        READING_AMOUNT,
        USEFULNESS,
        STRESS_LEVEL
    }

    public RatingDataHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

        _dbColNames = new EnumMap<>(DBCols.class);
        int index = 0;

        _dbColNames.put(DBCols.RATING_ID,
                new EnumHelper("rating_id", index++));
        _dbColNames.put(DBCols.COURSE_CODE,
                new EnumHelper("course_code", index++));
        _dbColNames.put(DBCols.COURSE_NUMBER,
                new EnumHelper("course_number", index++));
        _dbColNames.put(DBCols.USER_EMAIL,
                new EnumHelper("user_email", index++));
        _dbColNames.put(DBCols.HOMEWORK_AMOUNT,
                new EnumHelper("homework_amount", index++));
        _dbColNames.put(DBCols.READING_AMOUNT,
                new EnumHelper("reading_amount", index++));
        _dbColNames.put(DBCols.USEFULNESS,
                new EnumHelper("usefulness", index++));
        _dbColNames.put(DBCols.STRESS_LEVEL,
                new EnumHelper("stress_level", index));

        _db = getWritableDatabase();
        onCreate(_db);

    }

    private static final String DATATYPE_TEXT = " TEXT ";
    private static final String DATATYPE_INT = " INTEGER ";
    private static final String SEPARATOR_COMMA = ",";

/*    private static final String AUTO_INC = " AUTOINCREMENT ";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")"; */

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "("

                + _dbColNames.get(DBCols.RATING_ID).name()
                + DATATYPE_INT
                + " PRIMARY KEY "
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.USER_EMAIL).name()
                + DATATYPE_TEXT
                + "REFERENCES "
                + UserDataHandler.getTableName()
                + "("
                + _dbColNames.get(DBCols.USER_EMAIL).name()
                + ")"
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.COURSE_CODE).name()
                + DATATYPE_TEXT
                + "REFERENCES "
                + CourseDataHandler.getTableName()
                + "("
                + _dbColNames.get(DBCols.COURSE_CODE).name()
                + ")"
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.COURSE_NUMBER).name()
                + DATATYPE_INT
                + "REFERENCES "
                + CourseDataHandler.getTableName()
                + "("
                + _dbColNames.get(DBCols.COURSE_NUMBER).name()
                + ")"
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.HOMEWORK_AMOUNT).name()
                + DATATYPE_INT
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.READING_AMOUNT).name()
                + DATATYPE_INT
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.USEFULNESS).name()
                + DATATYPE_INT
                + SEPARATOR_COMMA

                + _dbColNames.get(DBCols.STRESS_LEVEL).name()
                + DATATYPE_INT
                + ")";

        db.execSQL(CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRating(RatingDataModel rating,
                           CourseDataModel course, UserDataModel user) {

        ContentValues values = new ContentValues();

        values.put(_dbColNames.get(DBCols.RATING_ID).name(),
                rating.getRatingID());
        values.put(_dbColNames.get(DBCols.COURSE_CODE).name(),
                course.getCourseCode().toString());
        values.put(_dbColNames.get(DBCols.COURSE_NUMBER).name(),
                course.getCourseNumber());
        values.put(_dbColNames.get(DBCols.USER_EMAIL).name(),
                user.getUserEmail());
        values.put(_dbColNames.get(DBCols.HOMEWORK_AMOUNT).name(),
                rating.getHomework());
        values.put(_dbColNames.get(DBCols.READING_AMOUNT).name(),
                rating.getReading());
        values.put(_dbColNames.get(DBCols.USEFULNESS).name(),
                rating.getUsefulness());
        values.put(_dbColNames.get(DBCols.STRESS_LEVEL).name(),
                rating.getStress());

        _db.insert(TABLE_NAME, null, values);

    }

    public RatingDataModel getRating(int ratingID) {

        String[] columns = new String[_dbColNames.size()];

        EnumHelper value = _dbColNames.get(DBCols.RATING_ID);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_CODE);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_NUMBER);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USER_EMAIL);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.HOMEWORK_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.READING_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USEFULNESS);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.STRESS_LEVEL);
        columns[value.index()] = value.name();

        String whereClause = _dbColNames.get(DBCols.RATING_ID).name() + " = ?";
        String[] whereArgs = {
                String.valueOf(ratingID)
        };

        Cursor cursor = _db.query(TABLE_NAME, columns, whereClause, whereArgs,
                null, null, null, null);

        if (isValidCursor(cursor)) {
            cursor.moveToFirst();
        }

        return extractAllDataFromCursor(cursor);
    }

    public ArrayList<RatingDataModel> getAllRatingsForCourse(
            CourseDataModel.CourseCode courseCode , int courseNumber) {

        String[] columns = new String[_dbColNames.size()];

        EnumHelper value = _dbColNames.get(DBCols.RATING_ID);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_CODE);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_NUMBER);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USER_EMAIL);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.HOMEWORK_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.READING_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USEFULNESS);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.STRESS_LEVEL);
        columns[value.index()] = value.name();

        String whereClause = _dbColNames.get(DBCols.COURSE_CODE).name()
                + " = ? AND "
                + _dbColNames.get(DBCols.COURSE_NUMBER).name()
                + " = ?";

        String[] whereArgs = {
                        courseCode.toString(),
                        String.valueOf(courseNumber)
                };

        Cursor cursor = _db.query(TABLE_NAME, columns, whereClause, whereArgs,
                null, null, null, null);

        ArrayList<RatingDataModel> resultList = new ArrayList<>();

        if (isValidCursor(cursor)) {
            resultList = new ArrayList<>();
            int ii = 0;
            while (ii < cursor.getCount()) {
                cursor.moveToPosition(ii);
                resultList.add(extractAllDataFromCursor(cursor));
                ii++;
            }
        }

        return resultList;
    }

    // This function may be unnecessary (can use course accessors in main code)
    public ArrayList<RatingDataModel> getAllRatingsForCourse(CourseDataModel course) {

        String[] columns = new String[_dbColNames.size()];

        EnumHelper value = _dbColNames.get(DBCols.RATING_ID);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_CODE);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_NUMBER);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USER_EMAIL);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.HOMEWORK_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.READING_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USEFULNESS);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.STRESS_LEVEL);
        columns[value.index()] = value.name();

        String whereClause = _dbColNames.get(DBCols.COURSE_CODE).name()
                + " = ? AND "
                + _dbColNames.get(DBCols.COURSE_NUMBER).name()
                + " = ?";

        String[] whereArgs =
                {
                        course.getCourseCode().toString(),
                        String.valueOf(course.getCourseNumber())
                };

        Cursor cursor = _db.query(TABLE_NAME, columns, whereClause, whereArgs,
                null, null, null, null);

        ArrayList<RatingDataModel> resultList = new ArrayList<>();

        if (isValidCursor(cursor)) {
            resultList = new ArrayList<>();
            int ii = 0;
            while (ii < cursor.getCount()) {
                cursor.moveToPosition(ii);
                resultList.add(extractAllDataFromCursor(cursor));
                ii++;
            }
        }

        return resultList;
    }

    public ArrayList<RatingDataModel> getAllRatingsByUser(String userID) {

        String[] columns = new String[_dbColNames.size()];

        EnumHelper value = _dbColNames.get(DBCols.RATING_ID);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_CODE);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_NUMBER);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USER_EMAIL);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.HOMEWORK_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.READING_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USEFULNESS);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.STRESS_LEVEL);
        columns[value.index()] = value.name();

        String whereClause = _dbColNames.get(DBCols.USER_EMAIL).name()
                + " = ?";

        String[] whereArgs =
                {
                        String.valueOf(userID)
                };

        Cursor cursor = _db.query(TABLE_NAME, columns, whereClause, whereArgs,
                null, null, null, null);

        ArrayList<RatingDataModel> resultList = new ArrayList<>();

        if (isValidCursor(cursor)) {
            resultList = new ArrayList<>();
            int ii = 0;
            while (ii < cursor.getCount()) {
                cursor.moveToPosition(ii);
                resultList.add(extractAllDataFromCursor(cursor));
                ii++;
            }
        }

        return resultList;
    }

    public RatingDataModel getCourseRatingByUser(String userID,
                                                    CourseDataModel.CourseCode courseCode,
                                                    int courseNumber) {

        String[] columns = new String[_dbColNames.size()];

        EnumHelper value = _dbColNames.get(DBCols.RATING_ID);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_CODE);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.COURSE_NUMBER);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USER_EMAIL);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.HOMEWORK_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.READING_AMOUNT);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.USEFULNESS);
        columns[value.index()] = value.name();
        value = _dbColNames.get(DBCols.STRESS_LEVEL);
        columns[value.index()] = value.name();

        String whereClause = _dbColNames.get(DBCols.COURSE_CODE).name()
                + " = ? AND "
                + _dbColNames.get(DBCols.COURSE_NUMBER).name()
                + " = ? AND "
                + _dbColNames.get(DBCols.USER_EMAIL).name()
                + " = ?";

        String[] whereArgs =
                {
                        courseCode.toString(),
                        String.valueOf(courseNumber),
                        String.valueOf(userID)
                };

        Cursor cursor = _db.query(TABLE_NAME, columns, whereClause, whereArgs,
                null, null, null, null);

        if (isValidCursor(cursor)) {
            cursor.moveToFirst();
        }

        return extractAllDataFromCursor(cursor);
    }

    //get user rating for course
    public int getAverageForCriteria() {

        return 0;
    }

    public void dropTable (String tableName, boolean confirmation) {
        if (confirmation) {
            _db.execSQL("DROP TABLE IF EXISTS " + tableName);
            _db.close();
        }
    }

    public ArrayList<RatingDataModel> getAllEntries() {

        ArrayList<RatingDataModel> resultList = null;
        Cursor cursor = _db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (isValidCursor(cursor)) {
            resultList = new ArrayList<>();
            int ii = 0;
            while (ii < cursor.getCount()) {
                cursor.moveToPosition(ii);
                resultList.add(extractAllDataFromCursor(cursor));
                ii++;
            }
        }

        return resultList;
    }

    public RatingDataModel extractAllDataFromCursor(Cursor rowCursor) {

        if (!isValidCursor(rowCursor)) {
            return null;
        }

        int db_homeworkAmount = rowCursor.getInt(
                _dbColNames.get(DBCols.HOMEWORK_AMOUNT).index());
        int db_readingAmount = rowCursor.getInt(
                _dbColNames.get(DBCols.READING_AMOUNT).index());
        int db_usefulness = rowCursor.getInt(
                _dbColNames.get(DBCols.USEFULNESS).index());
        int db_stressLevel = rowCursor.getInt(
                _dbColNames.get(DBCols.STRESS_LEVEL).index());

        RatingDataModel ratingData = new RatingDataModel(
                        db_homeworkAmount,
                        db_readingAmount,
                        db_usefulness,
                        db_stressLevel
        );

        return ratingData;
    }

    public String extractDataFromCursor(Cursor rowCursor,
                                        String[] columnsToExtract) {

        String retVal = "";

        if (isValidCursor(rowCursor) && columnsToExtract.length > 0) {
            int ii = 0;
            while (ii < columnsToExtract.length) {
                int colIndex = rowCursor.getColumnIndex(columnsToExtract[ii]);
                retVal += rowCursor.getString(colIndex);
                ii++;
            }
        }

        return retVal;
    }

    private void debugPrintRowCursor(Cursor rowData) {
        if (isValidCursor(rowData)) {
            int ii = 0;
            Log.d(LOG_TAG, "testRowCursor");
            while (ii < rowData.getColumnCount()) {
                Log.d("row " + ii + ": ", rowData.getString(ii));
                ii++;
            }
        }
    }

    private boolean isValidCursor(Cursor cursor) {
        return (cursor != null)
                && (cursor.getCount() > 0) && (cursor.getColumnCount() > 0);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }
}

