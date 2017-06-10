package clinic;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Doctor extends Person {
	private int docID;
	private String specialization;
	private List<Person> patients = new LinkedList<>();
	
	public Doctor(String first, String last, String ssn, int docID, String specialization) {
		super(first, last, ssn);
		this.docID=docID;
		this.specialization=specialization;
	}

	public int getId(){
		return docID;
	}
	
	public String getSpecialization(){
		return specialization;
	}
	
	public void addPatients(Person patient){
		patients.add(patient);
		patient.setDoctor(this);
	}
	
	public Collection<Person> getPatients() {
		return patients.stream()
				.sorted(Comparator.comparing(Person::getLast)
						.thenComparing(Person::getFirst))
				.collect(Collectors.toList());
	}

}
