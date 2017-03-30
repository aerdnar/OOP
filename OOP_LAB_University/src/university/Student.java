package university;

import java.util.Arrays;

class Student extends Person {
	private int newbie;
	private final int MAX_COURSE = 25;
	private int[] coursesList = new int[MAX_COURSE];
	private int courseIndex;

	public Student() {
		super();
	}

	public Student(String name, String surname) {
		super(name, surname);
	}

	public Student(String name, String surname, int newbie) {
		super(name, surname);
		setNewbie(newbie);
	}

	public void setNewbie(int newbie) {
		this.newbie = newbie;
	}

	public int addCourse(int code) {
		if (canAddCourse() == true) {
			int i;
			for (i = 0; i < courseIndex && coursesList[i] != code; i++)
				;
			if (courseIndex == i) {
				coursesList[courseIndex++] = code;
				return 0;
			} else
				return -2;
		} else
			return -1;
	}

	public boolean canAddCourse() {
		return courseIndex < MAX_COURSE;
	}

	public int[] getCourses() {
		return Arrays.copyOf(coursesList, courseIndex);
	}

	public int getNewbie() {
		return newbie;
	}
}
