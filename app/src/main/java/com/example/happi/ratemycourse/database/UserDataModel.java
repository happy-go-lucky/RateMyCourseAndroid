package com.example.happi.ratemycourse.database;

import android.util.Log;

import com.example.happi.ratemycourse.util.TextEncoder;

import org.w3c.dom.Text;


public class UserDataModel
{
    private static int numberOfUsers = 0;

    private final String LOG_TAG = "UserDataModel";
	private String _userEmail;
	private String _firstName;
	private String _lastName;
	private String _schoolName;
	private byte[] _encodedPassword;

	public UserDataModel( String userEmail, byte[] password, String firstName, String lastName, String schoolName )
	{
		_userEmail = userEmail;
        _encodedPassword = password;
        _firstName = firstName;
        _lastName = lastName;
        _schoolName = schoolName;
	}

	public String getUserEmail()
	{
		return _userEmail;
	}

	public String getFirstName()
	{
		return _firstName;
	}

	public String getLastName()
	{
		return _lastName;
	}

	public String getSchoolName()
	{
		return _schoolName;
	}

	public byte[] getPassword()
	{
		return _encodedPassword;
	}

	public void setUserEmail( String value )
	{
		_userEmail = value;
	}

	public void setFirstName( String value )
	{
		_firstName = value;
	}

	public void setLastName( String value )
	{
		_lastName = value;
	}

	public void setSchoolName( String value )
	{
		_schoolName = value;
	}

	public void setPassword( byte[] value )
	{
		_encodedPassword = value;
	}

}
