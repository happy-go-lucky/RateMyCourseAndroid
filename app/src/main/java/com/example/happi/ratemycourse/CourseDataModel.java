package com.example.happi.ratemycourse;

public class CourseDataModel {
	public enum CourseMode {FT, PT};

	public enum CourseSemester {Winter, Summer, Fall};

	public enum CourseCode {COMP, MATH, LIBS};

	private int _courseid;
	private CourseMode _courseMode;
	private int _courseNumber;
	private CourseCode _courseCode;
	private int _yearOffered;
	private String _instructorLastName;
	private String _instructorFirstName;
	private CourseSemester _semesterOffered;


	public CourseDataModel(int course_id, CourseMode mode, int course_number, CourseCode course_name, int year_offered, String instructor_lastname, String instructor_firstname) {
		_courseid = course_id;
		_courseMode = mode;
		_courseNumber = course_number;
		_courseCode = course_name;
		_yearOffered = year_offered;
		_instructorLastName = instructor_lastname;
		_instructorFirstName = instructor_firstname;
	}

	public int get_Courseid() {
		return _courseid;
	}

	public void set_Courseid(int course_id) {
		_courseNumber = course_id;
	}

	public CourseMode get_CourseMode() {
		return _courseMode;
	}

	public void set_CourseMode(CourseMode mode) {
		_courseMode = mode;
	}

	public int get_CourseNumber() {
		return _courseNumber;
	}

	public void set_CourseNumber(int course_number) {
		_courseNumber = course_number;
	}

	public CourseCode get_CourseCode() {
		return _courseCode;
	}

	public void set_CourseCode(CourseCode course_name) {
		_courseCode = course_name;
	}

	public int get_YearOffered() {
		return _yearOffered;
	}

	public void set_YearOffered(int yearOffered) {
		_yearOffered = yearOffered;
	}

//    public CourseSemester getSemesterOffered() {
//        return _semesterOffered;
//    }
//
//    public void setSemesterOffered(CourseSemester value) {
//        _semesterOffered = value;
//    }

	public String get_InstructorLastName() {
		return _instructorLastName;
	}

	public void set_InstructorLastName(String instructorLastName) {
		_instructorLastName = instructorLastName;
	}


	public String get_InstructorFirstName() {
		return _instructorFirstName;
	}

	public void set_InstructorFirstName(String instructorFirstName) {
		_instructorFirstName = instructorFirstName;
	}


	@Override
	public String toString() {
		return String.format("Id: %s, Course Mode: %s, Course Number: %s, Course Name: %s, Year: %s, Instructor Last Name: %s, Instructor First Name: %s\n",
				_courseid, _courseMode, _courseNumber, _courseCode, _yearOffered, _instructorLastName, _instructorFirstName);
	}
}
