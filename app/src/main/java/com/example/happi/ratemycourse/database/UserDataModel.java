package com.example.happi.ratemycourse.database;

import android.util.Log;


public class UserDataModel
{
	private final String LOG_TAG = "UserDataModel";
	private String _userId;
	private String _userName;
	private String _userEmail;
	private String _firstName;
	private String _lastName;
	private String _schoolName;
	private byte[] _encodedPassword;

	public UserDataModel( String userId, String userName, String userEmail, byte[] password, String firstName, String lastName, String schoolName )
	{
		_userId = userId;//
		_userName = userName;
		_userEmail = userEmail;
		_firstName = firstName;
		_lastName = lastName;
		_schoolName = schoolName;
		_encodedPassword = password;
	}
	public String getUserId()
	{
		return _userId;
	}

	public String getUserName()
	{
		return _userName;
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

	public void setUserId( String value )
	{
		_userId = value;
	}

	public void setUserName( String value )
	{
		_userName = value;
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
