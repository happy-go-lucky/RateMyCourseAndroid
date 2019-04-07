package com.example.happi.ratemycourse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.happi.ratemycourse.database.CourseDataHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseFragment extends Fragment
{
    private final String LOG_TAG = "AddCourseFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View _view;
    private EditText _courseName;
    private EditText _courseNumber;
    private Button _addButton;
    private CourseDataHandler _dataHandler;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        init();
    }

    @Override
    public void onAttach( Context context )
    {
        super.onAttach( context );
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        if ( _dataHandler != null ) _dataHandler.close();
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState )
    {
        super.onActivityCreated( savedInstanceState );
    }

    private void init()
    {
        _view = getView();

        _dataHandler = new CourseDataHandler( getContext() );

        _courseName = _view.findViewById( R.id.text_courseName );
        _courseNumber = _view.findViewById( R.id.text_courseNumber );
        _addButton = _view.findViewById( R.id.button_saveCourse );

        // setup the virtual keyboard next button action
        _courseName.setImeOptions( EditorInfo.IME_ACTION_DONE );
        _courseNumber.setImeOptions( EditorInfo.IME_ACTION_DONE );

        _addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v )
            {
                onAddButtonClick( v );
            }
        } );
    }

    private void onAddButtonClick( View v )
    {
        Log.d( LOG_TAG, "onLoginButtonClick" );
        String courseName = _courseName.getText().toString().toUpperCase();
        int courseNumber = Integer.parseInt( _courseNumber.getText().toString() );

        validateUserInput( courseName, courseNumber );
    }

    private void alertUser( int Message, int title )
    {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage( Message ).setTitle( title );
        builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User clicked OK button
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void validateUserInput( String courseName, int courseNumber )
    {
        /*CourseDataModel courseData = _dataHandler.getCourse( courseName, courseNumber );
        if ( courseData != null && java.util.Arrays.equals( userData.getPassword(), _textEncoder.getEncodedText( password ) ) )
        {
            alertUser( R.string.courseMessageTitle, R.string.courseAddSuccess );
        }
        else
        {
            alertUser( R.string.courseMessageTitle, R.string.courseAddFail );
        }
        */
    }
}
