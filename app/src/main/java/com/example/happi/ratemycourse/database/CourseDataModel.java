package com.example.happi.ratemycourse.database;

public class CourseDataModel {
	public enum CourseMode {FT, PT};

	public enum CourseSemester {Winter, Summer, Fall};

	public enum CourseCode {COMP, MATH, LIBS};

    private CourseCode _courseCode;
    private int _courseNumber;
    private String _courseName;
    private CourseMode _courseMode;
    private CourseSemester _semesterOffered;
    private int _yearOffered;
    private String _instructorLastName;
    private String _instructorFirstName;


	public CourseDataModel(CourseCode courseCode,
                           int course_number,
                           String courseName,
                           CourseMode mode,
                           CourseSemester semester,
                           int year_offered,
                           String instructor_lastname,
                           String instructor_firstname) {
        _courseCode = courseCode;
        _courseNumber = course_number;
        _courseName = courseName;
        _courseMode = mode;
        _semesterOffered = semester;
		_yearOffered = year_offered;
		_instructorLastName = instructor_lastname;
		_instructorFirstName = instructor_firstname;
	}

    public CourseCode getCourseCode() {
        return _courseCode;
    }

    public void setCourseCode(CourseCode code) {
        _courseCode = code;
    }

    public int getCourseNumber() {
        return _courseNumber;
    }

    public void setCourseNumber(int number) {
        _courseNumber = number;
    }

    public String getCourseName() {
        return _courseName;
    }

    public void setCourseName(String name) {
        _courseName = name;
    }

	public CourseMode getCourseMode() {
		return _courseMode;
	}

	public void setCourseMode(CourseMode mode) {
		_courseMode = mode;
	}

    public CourseSemester getSemesterOffered() {
        return _semesterOffered;
    }

    public void setSemesterOffered(CourseSemester semester) {
        _semesterOffered = semester;
    }

	public int getYearOffered() {
		return _yearOffered;
	}

	public void setYearOffered(int year) {
		_yearOffered = year;
	}

	public String getInstructorLastName() {
		return _instructorLastName;
	}

	public void setInstructorLastName(String lastName) {
		_instructorLastName = lastName;
	}

	public String getInstructorFirstName() {
		return _instructorFirstName;
	}

	public void setInstructorFirstName(String firstName) {
		_instructorFirstName = firstName;
	}

	@Override
	public String toString() {
		return String.format("Course: %s%s %s, PT/FT: %s, Semester: %s, " +
                        "Year: %s, Instructor: %s, %s\n",
				_courseCode, _courseNumber, _courseName, _courseMode,
                _semesterOffered, _yearOffered, _instructorLastName,
                _instructorFirstName);
	}
}