package clinic;

import clinic.Doctor;

public class Person {
	private String first;
	private String last;
	private String ssn;
	private Doctor doctor;
	
	public Person(String first, String last, String ssn) {
		this.first = first;
		this.last = last;
		this.ssn = ssn;
		doctor = null;
	}

	public void setDoctor(Doctor doctor){
		this.doctor = doctor;
	}
	
	public String getSSN(){
		return ssn;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public Doctor getDoctor() {
		return doctor;
	}

}
