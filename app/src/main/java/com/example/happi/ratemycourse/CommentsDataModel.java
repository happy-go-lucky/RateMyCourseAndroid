package com.example.happi.ratemycourse;

//import android.util.Log;


public class CommentsDataModel {
    private final String LOG_TAG = "UserDataModel";
    private int _commentId;
    private String _comments;


    public CommentsDataModel(int commentId, String comments) {
        _commentId = commentId;
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

}


