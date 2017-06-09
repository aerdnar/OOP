package schools;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class Municipality {
	private String name;
	private String province;
	private Community community;
	private List<Branch> branches = new LinkedList<>();
	
	public Municipality(String name, String province){
		this.name=name;
		this.province=province;
	}
	
	public Municipality(String name, String province, Community community){
		this(name,province);
		this.community=community;
		community.addMunicipality(this);
	}
	public String getName() {
		return name;
}
	public String getProvince() {
		return province;
	}
	
	public void addBranch(Branch branch){
		branches.add(branch);
	}

	public Collection<Branch> getBranches() {
		return branches;
	}

	public Optional<Community> getCommunity() {
		return Optional.ofNullable(community);
	}	
	
}
