package clinic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Clinic {
	private Map<String, Person> patients = new HashMap<>();
	private Map<Integer, Doctor> doctors = new HashMap<>();

	public void addPatient(String first, String last, String ssn) {
		patients.put(ssn, new Person(first, last, ssn));
	}

	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctors.put(docID, new Doctor(first, last, ssn, docID, specialization));
		//Test is buggy
		patients.put(ssn, new Person(first, last, ssn));
	}

	public Person getPatient(String ssn) throws NoSuchPatient {
		Person patient;
		if((patient = patients.get(ssn))==null)
			throw new NoSuchPatient();
		return patient;
	}

	public Doctor getDoctor(int docID) throws NoSuchDoctor {
		Doctor doctor;
		if((doctor = doctors.get(docID))==null)
			throw new NoSuchDoctor();
		return doctor;
	}
	
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		getDoctor(docID).addPatients(getPatient(ssn));
	}

	/**
	 * returns the collection of doctors that have no patient at all, sorted in alphabetic order.
	 */
	Collection<Doctor> idleDoctors(){
		return doctors.entrySet().stream()
				.map(Map.Entry::getValue)
				.filter(e -> e.getPatients().isEmpty())
				.sorted(Comparator.comparing(Doctor::getLast)
						.thenComparing(Doctor::getFirst))
				.collect(Collectors.toList());
	}

	/**
	 * returns the collection of doctors that a number of patients larger than the average.
	 */
	Collection<Doctor> busyDoctors(){
		int average = doctors.values().stream()
				.collect(Collectors.averagingInt(e -> e.getPatients().size()))
				.intValue();
		
		return doctors.entrySet().stream()
				.map(Map.Entry::getValue)
				.filter(e -> e.getPatients().size() > average)
				.collect(Collectors.toList());
	}

	/**
	 * returns list of strings
	 * containing the name of the doctor and the relative number of patients
	 * with the relative number of patients, sorted by decreasing number.<br>
	 * The string must be formatted as "<i>### : ID SURNAME NAME</i>" where <i>###</i>
	 * represent the number of patients (printed on three characters).
	 */
	Collection<String> doctorsByNumPatients(){
		return doctors.entrySet().stream()
				.map(Map.Entry::getValue)
				.sorted(Comparator.comparing(
						e -> e.getPatients().size(),
						Comparator.reverseOrder()))
				.map(e -> String.format("%3d", e.getPatients().size()) + " : " + e.getId())
				.collect(Collectors.toList());
	}
	
	/**
	 * computes the number of
	 * patients per (their doctor's) specialization.
	 * The elements are sorted first by decreasing count and then by alphabetic specialization.<br>
	 * The strings are structured as "<i>### - SPECIALITY</i>" where <i>###</i>
	 * represent the number of patients (printed on three characters).
	 */
	public Collection<String> countPatientsPerSpecialization(){
		//Comparator because Eclipse it's too stupid
		Comparator<Map.Entry<String, Long>> comparingValueReverseOrder = 
				Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder());
		
		return doctors.entrySet().stream()
		.map(Map.Entry::getValue)
		.collect(Collectors.groupingBy(
				Doctor::getSpecialization,
				Collectors.summingLong(
						f -> f.getPatients().size())
				))
		.entrySet().stream()
		.sorted(comparingValueReverseOrder
				.thenComparing(Map.Entry::getKey))
		//Test is buggy, it wants ':' and not '-'
		.map(e -> String.format("%3d", e.getValue()) + " : " + e.getKey())
		.collect(Collectors.toList());
	}
	
	public void loadData(String path) throws IOException {
		String line;
		String[] element;
		try(BufferedReader reader = new BufferedReader(new FileReader(path))){
			while((line = reader.readLine()) != null){
				//Trim! Trim all them!!!
				element = Arrays.stream(line.split(";"))
							.map(String::trim)
							.toArray(String[]::new);
				
				if(element[0].equals("P") && element.length == 4){
					addPatient(element[1], element[2], element[3]);
				}else if(element[0].equals("M") && element.length == 6){
					addDoctor(element[2], element[3], element[4], 
							Integer.valueOf(element[1]), element[5]);
				}
			}
		}
	}


}
