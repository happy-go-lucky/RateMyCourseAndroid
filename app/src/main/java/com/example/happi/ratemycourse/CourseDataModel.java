package com.example.happi.ratemycourse;

public class CourseDataModel {
	public enum CourseMode {FT, PT};

	public enum CourseSemester {Winter, Summer, Fall};

	public enum CourseCode {COMP, MATH, LIBS};

    private int _courseNumber;
    private CourseCode _courseCode;
    private CourseMode _courseMode;
    private CourseSemester _semesterOffered;
    private int _yearOffered;
    private String _instructorLastName;
    private String _instructorFirstName;


	public CourseDataModel(CourseMode mode,
                           CourseSemester semester,
                           int course_number,
                           CourseCode courseCode,
                           int year_offered,
                           String instructor_lastname,
                           String instructor_firstname) {
	    _semesterOffered = semester;
		_courseMode = mode;
		_courseNumber = course_number;
		_courseCode = courseCode;
		_yearOffered = year_offered;
		_instructorLastName = instructor_lastname;
		_instructorFirstName = instructor_firstname;
	}

	public CourseMode getCourseMode() {
		return _courseMode;
	}

	public void setCourseMode(CourseMode mode) {
		_courseMode = mode;
	}

	public int getCourseNumber() {
		return _courseNumber;
	}

	public void setCourseNumber(int course_number) {
		_courseNumber = course_number;
	}

	public CourseCode getCourseCode() {
		return _courseCode;
	}

	public void setCourseCode(CourseCode course_name) {
		_courseCode = course_name;
	}

	public int getYearOffered() {
		return _yearOffered;
	}

	public void setYearOffered(int yearOffered) {
		_yearOffered = yearOffered;
	}

    public CourseSemester getSemesterOffered() {
        return _semesterOffered;
    }

    public void setSemesterOffered(CourseSemester value) {
        _semesterOffered = value;
    }

	public String getInstructorLastName() {
		return _instructorLastName;
	}

	public void setInstructorLastName(String instructorLastName) {
		_instructorLastName = instructorLastName;
	}

	public String getInstructorFirstName() {
		return _instructorFirstName;
	}

	public void setInstructorFirstName(String instructorFirstName) {
		_instructorFirstName = instructorFirstName;
	}

	@Override
	public String toString() {
		return String.format("Course Mode: %s, Course Number: %s, Course Name: %s, Year: %s, Instructor Last Name: %s, Instructor First Name: %s\n",
				_courseMode, _courseNumber, _courseCode, _yearOffered, _instructorLastName, _instructorFirstName);
	}
}