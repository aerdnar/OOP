package university;

import java.util.Arrays;

class Course {
	private String title;
	private String teacher;
	private final int MAX_STUDENT = 100;
	private int[] studentsList = new int[100];
	private int studentIndex;
	private int code;

	public Course() {
		code = -1;
	}

	public Course(String title, String teacher, int code) {
		setTitle(title);
		setTeacher(teacher);
		setCode(code);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int addStudent(int code) {
		if (canAddStudent() == true) {
			int i;
			for (i = 0; i < studentIndex && studentsList[i] != code; i++)
				;
			if (studentIndex == i) {
				studentsList[studentIndex++] = code;
				return 0;
			} else
				return -2;
		} else
			return -1;
	}

	public boolean canAddStudent() {
		return studentIndex < MAX_STUDENT;
	}

	public int[] getStudents() {
		return Arrays.copyOf(studentsList, studentIndex);
	}

	public String getTitle() {
		return title;
	}

	public String getTeacher() {
		return teacher;
	}

	public int getCode() {
		return code;
	}
}
