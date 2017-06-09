package schools;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class School {
	private String name;
	private String code;
	private int grade;
	private String description;
	private List<Branch> branches = new LinkedList<>();
	
	public School(String name, String code, int grade, String description) {
		this.name=name;
		this.code=code;
		this.grade=grade;
		this.description=description;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public int getGrade() {
		return grade;
	}

	public String getDescription() {
		return description;
	}

	public Collection<Branch> getBranches() {
		return branches;
	}

	public void addBranch(Branch branch) {
		branches.add(branch);
	}

}
