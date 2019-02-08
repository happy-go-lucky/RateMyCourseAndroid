package com.example.happi.ratemycourse;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TextEncoder
{
	private final String LOG_TAG = "TextEncoder";

	private MessageDigest _mdCode;

	public TextEncoder( String algorithm )
	{
		try
		{
			_mdCode = MessageDigest.getInstance( algorithm );
		}
		catch( NoSuchAlgorithmException e )
		{
			Log.d( LOG_TAG, "please enter a valid algorithm to encrypt" );
		}
	}

	public byte[] getEncodedText( String text )
	{
		_mdCode.update( text.getBytes() );
		return _mdCode.digest();
	}
}
