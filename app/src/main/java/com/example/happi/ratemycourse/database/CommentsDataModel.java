package com.example.happi.ratemycourse.database;

//import android.util.Log;


public class CommentsDataModel {
    private final String LOG_TAG = "UserDataModel";
    private int _commentId;
    private int _userID; // The Author
    private CourseDataModel.CourseCode _courseCode;
    private int _courseNumber;
    private String _comments;


    public CommentsDataModel(int commentId, int userID, CourseDataModel.CourseCode courseCode, int courseNumber, String comments) {
        _commentId = commentId;
        _userID = userID;
        _courseCode = courseCode;
        _courseNumber = courseNumber;
        _comments = comments;
    }

    public int get_CommentId() {
        return _commentId;
    }

    public String get_Comments() {
        return _comments;
    }

    public void set_CommentId(int commentId) {
        _commentId =commentId;}

    public void set_Comments(String comments) {
        _comments =comments;}

    public int get_userID() {
        return _userID;
    }

    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public CourseDataModel.CourseCode get_courseCode() {
        return _courseCode;
    }

    public void set_courseCode(CourseDataModel.CourseCode _courseCode) {
        this._courseCode = _courseCode;
    }

    public int get_courseNumber() {
        return _courseNumber;
    }

    public void set_courseNumber(int _courseNumber) {
        this._courseNumber = _courseNumber;
    }
}


