package com.example.happi.ratemycourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.happi.ratemycourse.database.CourseDataHandler;
import com.example.happi.ratemycourse.database.CourseDataModel;
import com.example.happi.ratemycourse.database.RatingDataHandler;
import com.example.happi.ratemycourse.database.RatingDataModel;
import com.example.happi.ratemycourse.database.UserDataHandler;
import com.example.happi.ratemycourse.database.UserDataModel;

import java.util.ArrayList;


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
	private CourseDataHandler _courseDataHandler;
	private RatingDataHandler _ratingDataHandler;
	private UserDataHandler _userDataHandler;

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
		if ( getArguments() != null ) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
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
		if ( _courseDataHandler != null ) _courseDataHandler.close();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		Log.d( LOG_TAG, "onActivityCreated" );
		super.onActivityCreated(savedInstanceState);


		_view = getView();
		initDatabase();
		testDB();
		initScreenElements();
	}

	public void initScreenElements()
	{
		_searchTextView = _view.findViewById( R.id.autoCompleteTextView );

		_searchTextView.setThreshold(2);

		ArrayList<String> entries = _courseDataHandler.getAllCourseCodesAndNumbers();

		if (entries == null) {
			Log.e("COURSE LIST ENTRIES", "Failed to fetch course listing");
			return;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, entries);
		_searchTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

		_searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getContext(), CourseRatingActivity.class);
				intent.putExtra("COURSE_NAME", parent.getItemAtPosition(position).toString());
				startActivity(intent);
			}
		});
	}

	private void initDatabase()
	{
		_courseDataHandler = new CourseDataHandler( getContext() );
		_ratingDataHandler = new RatingDataHandler(this.getContext());
        _userDataHandler = new UserDataHandler(this.getContext());
    }

	private void testDB() {

		// Add course data
		CourseDataModel courseDataSent = new CourseDataModel(
				CourseDataModel.CourseCode.COMP,
				8051,
				"Advanced game development",
				CourseDataModel.CourseMode.FT,
				CourseDataModel.CourseSemester.Winter,
				2019,
				"Noureddin",
				"Bourna"
				);
		_courseDataHandler.updateCourse( courseDataSent );

		// Add User Data
        byte[] password = {0};
        UserDataModel userDataSent = new UserDataModel(
            "A12345678",
                "peterriviera",
                "peterriviera@gmail.com",
                password,
                "Peter",
                "Riviera",
                "BCIT"
        );
        _userDataHandler.updateUser( userDataSent );

        // Add Rating Data
		RatingDataModel ratingDataSent = new RatingDataModel(5, 4, 3, 7);
		_ratingDataHandler.addRating(ratingDataSent, courseDataSent, userDataSent);

		// try to get it back
		CourseDataModel courseDataReceived = _courseDataHandler.getCourse( CourseDataModel.CourseCode.COMP, 8051 );
		RatingDataModel ratingDataRecieved = _ratingDataHandler.getAllRatingsForCourse(courseDataReceived);

	}
}
