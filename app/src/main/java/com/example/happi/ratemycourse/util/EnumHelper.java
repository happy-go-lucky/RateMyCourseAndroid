package com.example.happi.ratemycourse.util;

import java.util.EnumMap;

public class EnumHelper
{
	private String _name;
	private int _index;

	public EnumHelper( String name, int index )
	{
		_name = name;
		_index = index;
	}

	public String name()
	{
		return _name;
	}

	public int index()
	{
		return _index;
	}

	@Override
	public String toString()
	{
		return String.format( "name: %s, index: %d", _name, _index );
	}
}
