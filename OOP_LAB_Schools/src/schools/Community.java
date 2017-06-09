package schools;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Community {
	private String name;
	private Type type;
	private List<Municipality> municipalities = new LinkedList<>();
	
	public Community(String name, Type type){
		this.name=name;
		this.type=type;
	}
	
	public enum Type { MONTANA, COLLINARE };
	
	public String getName() {
		return name;
	}
	
	public Type getType(){
		return type;
	}
	
	public void addMunicipality(Municipality municipality){
		municipalities.add(municipality);
	}

	public Collection<Municipality> getMunicipalities() {
		return municipalities;
	}
	
}
