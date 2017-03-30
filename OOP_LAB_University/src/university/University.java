package university;

public class University {
	private final int MAX_NEWBIE = 1000;
	private final int START_NEWBIE = 10000;
	private final int MAX_COURSE = 50;
	private final int START_COURSE = 10;
	private String name;
	private Person rector;
	private Student[] studentsList = new Student[MAX_NEWBIE];
	private Course[] coursesList = new Course[MAX_COURSE];
	private int newbieIndex;
	private int courseIndex;

	public University() {
	}

	public University(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRector(String first, String last) {
		rector = new Person(first, last);
	}

	public String getRector() {
		if (rector != null)
			return rector.getName() + " " + rector.getSurname();
		else
			return null;
	}

	public int enroll(String first, String last) {
		if (newbieIndex < MAX_NEWBIE) {
			studentsList[newbieIndex] = new Student(first, last, newbieIndex + START_NEWBIE);
			return studentsList[newbieIndex++].getNewbie();
		} else
			return -1;
	}

	public String student(int id) {
		if (id - START_NEWBIE < newbieIndex) {
			return studentsList[id - START_NEWBIE].getNewbie() + " " + studentsList[id - START_NEWBIE].getName() + " "
					+ studentsList[id - START_NEWBIE].getSurname();
		} else
			return null;
	}

	public int activate(String title, String teacher) {
		if (courseIndex < MAX_COURSE) {
			coursesList[courseIndex] = new Course(title, teacher, courseIndex + START_COURSE);
			return coursesList[courseIndex++].getCode();
		} else
			return -1;
	}

	public String course(int code) {
		if (code - START_COURSE < courseIndex) {
			return coursesList[code - START_COURSE].getCode() + " " + coursesList[code - START_COURSE].getTitle() + " "
					+ coursesList[code - START_COURSE].getTeacher();
		} else
			return null;
	}

	public void register(int studentID, int courseCode) {
		if (studentID - START_NEWBIE < newbieIndex && courseCode - START_COURSE < courseIndex
				&& studentsList[studentID - START_NEWBIE].canAddCourse() == true
				&& coursesList[courseCode - START_COURSE].canAddStudent() == true) {
			studentsList[studentID - START_NEWBIE].addCourse(courseCode);
			coursesList[courseCode-START_COURSE].addStudent(studentID);
		}
	}

	public String listAttendees(int courseCode) {
		if(courseCode - START_COURSE < courseIndex){
			int[] tmp = coursesList[courseCode - START_COURSE].getStudents();
			int i,len;
			StringBuffer sb = new StringBuffer();
			for(i=0,len=tmp.length;i<len;i++)
				sb.append(student(tmp[i])).append("\n");
			return sb.toString();
		} return null;
	}

	public String studyPlan(int studentID) {
		if(studentID - START_NEWBIE < newbieIndex){
			int[] tmp = studentsList[studentID - START_NEWBIE].getCourses();
			int i,len;
			StringBuffer sb = new StringBuffer();
			for(i=0,len=tmp.length;i<len;i++)
				sb.append(course(tmp[i])).append("\n");
			return sb.toString();
		}
		return null;
	}
}
