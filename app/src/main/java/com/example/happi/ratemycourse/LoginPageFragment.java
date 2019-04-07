package com.example.happi.ratemycourse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.happi.ratemycourse.database.UserDataHandler;
import com.example.happi.ratemycourse.database.UserDataModel;
import com.example.happi.ratemycourse.util.TextEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginPageFragment extends Fragment
{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private final String LOG_TAG = "LoginPageFragment";
	private final String ENCRYPTION_ALGORITHM = "MD5";
	private static final String EMAIL_INPUT_DIGITS = "abcdefghijklmnopqrstuvwxyz0123456789@._";
	private static final String PASSWORD_INPUT_DIGITS = "0123456789-._abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private TextEncoder _textEncoder;
	private View _view;
	private EditText _email;
	private EditText _password;
	private Button _signupButton;
	private Button _loginButton;
	private UserDataHandler _dataHandler;

	public LoginPageFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment LoginPageFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LoginPageFragment newInstance( String param1, String param2 )
	{
		LoginPageFragment fragment = new LoginPageFragment();
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
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		return inflater.inflate( R.layout.fragment_login_page, container, false );
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

	@Override
	public void onResume()
	{
		super.onResume();
		init();
	}

	private void init()
	{
		_view = getView();
		_textEncoder = new TextEncoder( ENCRYPTION_ALGORITHM );
		_dataHandler = new UserDataHandler( getContext() );

		testDB();

		_email = _view.findViewById( R.id.text_email );
		_password = _view.findViewById( R.id.text_password );;
		_signupButton = _view.findViewById( R.id.button_signup );
		_loginButton = _view.findViewById( R.id.button_login );

		// setup the virtual keyboard next button action
		_email.setImeOptions( EditorInfo.IME_ACTION_DONE );
		_password.setImeOptions( EditorInfo.IME_ACTION_DONE );

		// input type
		//_email.setInputType( InputType.TYPE_CLASS_TEXT );
		//_email.setKeyListener( DigitsKeyListener.getInstance( EMAIL_INPUT_DIGITS ) );
		//_password.setInputType( InputType.TYPE_TEXT_VARIATION_PASSWORD );
		//_password.setKeyListener( DigitsKeyListener.getInstance( PASSWORD_INPUT_DIGITS ) );

		_loginButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v )
			{
				onLoginButtonClick( v );
			}
		} );
		_signupButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v )
			{
				onSignUpButtonClick( v );
			}
		} );
	}

	private void onLoginButtonClick( View v )
	{
		Log.d( LOG_TAG, "onLoginButtonClick" );
		String userName = _email.getText().toString();
		String password = _password.getText().toString();

		validateLogin( userName, password );
	}

	private void onSignUpButtonClick( View v )
	{
		Log.d( LOG_TAG, "onSignUpButtonClick" );
		String userName = _email.getText().toString();
		String password = _password.getText().toString();
	}

	private void validateLogin( String uname, String password )
	{
		UserDataModel userData = _dataHandler.getUser( uname );
		if ( userData != null && java.util.Arrays.equals( userData.getPassword(), _textEncoder.getEncodedText( password ) ) )
		{
			alertUser( R.string.login_success_message, R.string.login_message_title );
		}
		else
		{
			alertUser( R.string.login_fail_message, R.string.login_message_title );
		}
	}

//	private string storeUserId(UserDataModel userData){
//		String userID = UserDataModel.getUserId();
//		return userID;
//	}

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

	private void testDB()
	{
		UserDataModel userDataSent = new UserDataModel(
		        "1",
                "John123",
                "JohnD25@my.bcit.ca",
                new byte[]{1,2,3,4},
                "John",
                "Doe",
                "BC-BCIT");

		_dataHandler.updateUser( userDataSent );

		UserDataModel userDataReceived = _dataHandler.getUser( "johndoe@blah.com" );
		if ( userDataReceived != null )
		{
			Log.d( LOG_TAG + " data received", userDataReceived.toString() );
		}
		else
		{
			Log.d( LOG_TAG, " data received is null" );
		}
	}
}
