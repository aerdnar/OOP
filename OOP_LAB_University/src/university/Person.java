package university;

class Person {
	private String name;
	private String surname;

	public Person() {
	}

	public Person(String name, String surname) {
		setName(name);
		setSurname(surname);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
}
