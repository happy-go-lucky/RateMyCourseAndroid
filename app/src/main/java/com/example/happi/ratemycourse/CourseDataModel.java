package com.example.happi.ratemycourse;

public class CourseDataModel
{
	public enum CourseMode { FT, PT };
	public enum CourseSemester { Winter, Summer, Fall };
	public enum CourseCode { COMP, MATH, LIBS };

	private int _courseNumber;
	private int _yearOffered;
	private CourseSemester _semesterOffered;
	private CourseCode _courseCode;
	private String _instructorName;
	private CourseMode _courseMode;

	public CourseDataModel( int courseNumber, int yearOffered, CourseSemester semester, CourseCode courseCode, String instructor, CourseMode mode )
	{
		_courseNumber = courseNumber;
		_yearOffered = yearOffered;
		_semesterOffered = semester;
		_courseCode = courseCode;
		_instructorName = instructor;
		_courseMode = mode;
	}

	public int getCourseNumber()
	{
		return _courseNumber;
	}

	public void setCourseNumber( int value )
	{
		_courseNumber = value;
	}

	public CourseCode getCourseCode()
	{
		return _courseCode;
	}

	public void setCourseCode( CourseCode value )
	{
		_courseCode = value;
	}

	public int getYearOffered()
	{
		return _yearOffered;
	}

	public void setYearOffered( int value )
	{
		_yearOffered = value;
	}

	public CourseSemester getSemesterOffered()
	{
		return _semesterOffered;
	}

	public void setSemesterOffered( CourseSemester value )
	{
		_semesterOffered = value;
	}

	public String getInstructor()
	{
		return _instructorName;
	}

	public void setInstructor( String value )
	{
		_instructorName = value;
	}

	public CourseMode getCourseMode()
	{
		return _courseMode;
	}

	public void setCourseMode( CourseMode value )
	{
		_courseMode = value;
	}

	@Override
	public String toString()
	{
		return String.format( "code: %s, number: %s, Year: %s, semester: %s, instructor: %s, mode: %s\n",
				_courseCode, _courseNumber, _yearOffered, _semesterOffered, _instructorName, _courseMode );
	}
}
