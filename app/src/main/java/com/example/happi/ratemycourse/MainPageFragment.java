package com.example.happi.ratemycourse;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment
{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private static final String LOG_TAG = "MainPageFragment";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private View _view;
	private AutoCompleteTextView _searchTextView;
	private CourseDataHandler _dataHandler;

	public MainPageFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment MainPageFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static MainPageFragment newInstance( String param1, String param2 )
	{
		MainPageFragment fragment = new MainPageFragment();
		Bundle args = new Bundle();
		args.putString( ARG_PARAM1, param1 );
		args.putString( ARG_PARAM2, param2 );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		if ( getArguments() != null )
		{
			mParam1 = getArguments().getString( ARG_PARAM1 );
			mParam2 = getArguments().getString( ARG_PARAM2 );
		}
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		return inflater.inflate( R.layout.fragment_main_page, container, false );
	}

	@Override
	public void onAttach( Context context )
	{
		super.onAttach( context );
		//throw new RuntimeException( context.toString() + " must implement OnFragmentInteractionListener" );
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		if ( _dataHandler != null ) _dataHandler.close();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		Log.d( LOG_TAG, "onActivityCreated" );
		super.onActivityCreated(savedInstanceState);


		_view = getView();
        initScreenElements();
		initDatabase();
		testDB();
		buildAutoCompleteList();
	}

	public void initScreenElements()
	{
		_searchTextView = _view.findViewById( R.id.autoCompleteTextView );

        _searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TEST", parent.getItemAtPosition(position).toString());
            }
        });
	}

	private void initDatabase()
	{
		_dataHandler = new CourseDataHandler( getContext() );
	}

	private void buildAutoCompleteList()
	{
		ArrayList<String> entries = _dataHandler.getAllCourseIds();

		if ( entries == null )
		{
			return;
		}
		else
		{
			int ii = 0;
			Log.d( LOG_TAG, "Entries in DB:" );
			while ( ii < entries.size() )
			{
				Log.d( "entry" + ii, entries.get( ii ) );
				ii++;
			}

			ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>( getContext(), R.layout.fragment_main_page, R.id.autoCompleteTextView, entries );

			_searchTextView.setAdapter( searchAdapter );
		}
	}

	private void testDB()
	{
		CourseDataModel courseDataSent = new CourseDataModel(
				7051,
				2018,
				CourseDataModel.CourseSemester.Fall,
				CourseDataModel.CourseCode.COMP,
				"Borna",
				CourseDataModel.CourseMode.FT );

		_dataHandler.updateCourse( courseDataSent );

		CourseDataModel courseDataReceived = _dataHandler.getCourse( CourseDataModel.CourseCode.COMP, 7082 );
		if ( courseDataReceived != null )
		{
			Log.d( LOG_TAG + " data received", courseDataReceived.toString() );
		}
		else
		{
			Log.d( LOG_TAG, " data received is null" );
		}
	}
}
